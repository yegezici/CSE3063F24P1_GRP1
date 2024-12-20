import sqlite3
from Student import Student
from CourseSection import CourseSection
from Course import Course
from TimeSlot import TimeSlot
from Transcript import Transcript
from MandatoryCourse import MandatoryCourse
from NonTechnicalElectiveCourse import NonTechnicalElectiveCourse
from TechnicalElectiveCourse import TechnicalElectiveCourse
from DepartmentScheduler import DepartmentScheduler
from Advisor import Advisor
import copy
from typing import Optional
from Person import Person   
from Logging_Config import logger
class SQLiteManagement:


    def __init__(self):
        self.conn = sqlite3.connect('Iteration_3/database/CourseRegSys.db')
        self.cursor = self.conn.cursor()    
        self.courseSections = self.initialize_courseSections()
        self.courses = self.initialize_courses()
        self.set_prerequisites()
        self.students = []
        self.advisors = []
        
    
    def print_table(self, table_name: str) ->None:        
        try:
            self.cursor.execute(f"SELECT * FROM {table_name}")
            rows = self.cursor.fetchall()
            for row in rows:
              print(row)
        except sqlite3.Error as e:
            logger.warning("SQLite error:", e)

    def check_user(self, user_id: str, password: str) -> Person:
        self.cursor.execute(f"SELECT * FROM User WHERE UserID = '{user_id}' AND password = '{password}'")
        row = self.cursor.fetchone()
        if row:
            if row[2] == 'S':
                return self.get_student(row[0])
            if row[2] == 'A':
                return self.get_advisor(row[0])
            
    
    def initialize_courses(self) -> list:
        courses = []
        self.cursor.execute("SELECT * FROM Course")
        rows = self.cursor.fetchall()
        for row in rows:
            if row[4] == 'm':
                course =  MandatoryCourse(course_id=row[0], course_name=row[1], credits=row[2])
            elif row[4] == 'te':
                course = NonTechnicalElectiveCourse(course_id=row[0], course_name=row[1], credits=row[2])
            elif row[4] == 'nte':
                course = TechnicalElectiveCourse(course_id=row[0], course_name=row[1], credits=row[2])
            courses.append(course)
        return courses

    

    
    def set_prerequisites(self) -> None:
        for course in self.courses:
            self.cursor.execute(f"SELECT prerequisiteID FROM Course WHERE courseID = '{course.get_course_id()}'")
            rows = self.cursor.fetchall()
            for row in rows:
                if row[0] == None:
                    course.set_prerequisite_course(None)
                    continue
                prerequisite : Course
                for prerequisite in self.courses:
                    if row[0] == prerequisite.get_course_id():
                        course.set_prerequisite_course(prerequisite)
                
    def find_user(self, username: str, password: str) -> bool:
        try:
            self.cursor.execute(f"SELECT * FROM User u WHERE u.UserID = '{username}' AND u.password = '{password}'")
            rows = self.cursor.fetchall()
            return len(rows) > 0
        except sqlite3.Error as e:
            logger.warning("SQLite error:", e)
            return False
        
    
    def save_student(self, student: Student) -> None:
        try:
            sql = '''
            INSERT INTO Student (studentID, name, surname, gender, birthDate, advisorID, semester)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            '''

            self.cursor.execute(sql, (
                student.get_id(), 
                student.get_name(), 
                student.get_surname(), 
                student.get_gender(), 
                student.get_birthdate(), 
                student.get_advisor().ssn
            ))
            self.conn.commit()        
       
        except sqlite3.IntegrityError as e:
            logger.warning(f"Error: {e}")
    
    #Function to save student current section information into the database.
    def save_current_section_of_student(self, student: Student) -> None:
        try:
            #Check is there any current section record belonging to student.
            check_sql = '''
            SELECT COUNT(*) FROM CurrentSection WHERE studentID = ?
            '''
            self.cursor.execute(check_sql, (student.get_id(),))
            record_count = self.cursor.fetchone()[0]

            #If there is any record, delete it first, insert all later.
            if record_count > 0:
                delete_sql = '''
                DELETE FROM CurrentSection WHERE studentID = ?
                '''
                self.cursor.execute(delete_sql, (student.get_id(),))

            # Get Transcript object of the Student object. 
            transcript = student.get_transcript()
            # Get CurrentSection list from transcript object. 
            current_sections = transcript.get_current_sections()

            # Insert every section in the list into sql
            for section in current_sections:
                section_id = section.get_section_id()  # Get Section ID
                course = section.get_parent_course()  # Get Parent Course object.
                course_id = course.get_course_id()    # Get Course ID of Parent Course object.

                sql = '''
                INSERT INTO CurrentSection (studentID, courseID, courseSectionID)
                VALUES (?, ?, ?)
                '''
                self.cursor.execute(sql, (student.get_id(), course_id, section_id))

            self.conn.commit()

        except sqlite3.IntegrityError as e:
            print(f"Integrity error while saving CurrentSection: {e}")
        except sqlite3.Error as e:
            print(f"SQLite error while saving CurrentSection: {e}")

    def save_waited_section_of_student(self, student: Student) -> None:
        try:
            #Check is there any waited section record belonging to student.
            check_sql = '''
            SELECT COUNT(*) FROM WaitedSection WHERE studentID = ?
            '''
            self.cursor.execute(check_sql, (student.get_id(),))
            record_count = self.cursor.fetchone()[0]
            
            #If there is any record, delete it first, insert all later.
            if record_count > 0:
                delete_sql = '''
                DELETE FROM WaitedSection WHERE studentID = ?
                '''
                self.cursor.execute(delete_sql, (student.get_id(),))  

            # Get Transcript object of the Student object. 
            transcript = student.get_transcript()
            # WaitedSection'lar alınır
            waited_sections = transcript.get_waited_sections()

            # Her bir WaitedSection için SQL'e ekleme yapılır
            for section in waited_sections:
                section_id = section.get_section_id()  # Get Section ID
                course = section.get_parent_course()  # Get Parent Course object.
                course_id = course.get_course_id()    # Get Course ID of Parent Course object.

                sql = '''
                INSERT INTO WaitedSection (studentID, courseID, courseSectionID)
                VALUES (?, ?, ?)
                '''
                self.cursor.execute(sql, (student.get_id(), course_id, section_id))

            self.conn.commit()
            print(f"{student.get_id()} için WaitedSection bilgileri kaydedildi.")
        except sqlite3.IntegrityError as e:
            print(f"Integrity error while saving WaitedSection: {e}")
        except sqlite3.Error as e:
            print(f"SQLite error while saving WaitedSection: {e}")
            

    
    def save_courseSection(self, courseSection: CourseSection) -> None:
        try:
            sql = '''
            INSERT INTO CourseSection (sectionID, capacity, courseID, lecturerSSN)
            VALUES (?, ?, ?, ?)
            '''
            self.cursor.execute(sql, (courseSection.get_section_id, courseSection.get_capacity, courseSection.parent_course.course_id, courseSection.get_lecturer_ssn))
            self.conn.commit()        
       
        except sqlite3.IntegrityError as e:
            logger.warning(f"Error: {e}")
            
    def save_course(self, course: Course) -> None:
        try:
            sql = '''
            INSERT INTO Course (courseID, name, credit, prerequisiteID, courseType)
            VALUES (?, ?, ?, ?, ?)
            '''
            self.cursor.execute(sql, (course.get_course_id(), course.get_name, course.get_credit, course.get_prerequisite_id, course.get_course_type()))
            self.conn.commit()
        except sqlite3.IntegrityError as e:
            logger.warning(f"Error: {e}")
    
    
    def get_courses_of_transcript(self, student_id: str, courseList_type: str)-> list:
        courses = []
        try:
            self.cursor.execute(f"SELECT * FROM {courseList_type} t WHERE t.studentID = '{student_id}'")
            rows = self.cursor.fetchall()
            for row in rows:
                for course in self.courses:
                    if course.get_course_id() == row[1]:
                        new_course = copy.deepcopy(course) 
                        if courseList_type == "CompletedCourse":
                            new_course.set_grade(row[2])
                        courses.append(new_course)

                                    
            return courses
        except sqlite3.Error as e:
            logger.warning("SQLite error:", e)
    
    def get_course_sections_from_course(self, student_id: str, courseSectionList_type: str) -> list:
        courseSectionList = []
        try:
            self.cursor.execute(f"SELECT * FROM {courseSectionList_type} t WHERE t.studentID = '{student_id}'")
            rows = self.cursor.fetchall()
            for row in rows:
                storedSection = row[2]
                for section in self.courseSections:
                    if section.get_section_id() == storedSection:
                        courseSectionList.append(section)

            return courseSectionList
        except sqlite3.Error as e:
            logger.warning("SQLite error:", e)
    
    def get_course_sections_from_course(self, student_id: str, courseSectionList_type: str) -> list:
        courseSectionList = []
        try:
            self.cursor.execute(f"SELECT * FROM {courseSectionList_type} t WHERE t.studentID = '{student_id}'")
            rows = self.cursor.fetchall()
            for row in rows:
                storedSection = row[2]
                for section in self.courseSections:
                    if section.get_section_id() == storedSection:
                        courseSectionList.append(section)

            return courseSectionList
        except sqlite3.Error as e:
            logger.warning("SQLite error:", e)
        
    def initialize_courseSections(self) -> list:
        courseSections = []
         # Step 1: Query all CourseSections
        self.cursor.execute("SELECT sectionID, capacity, courseID, lecturerSSN FROM CourseSection;")
        sections_data = self.cursor.fetchall()
        for section_row in sections_data:
                section_id, capacity, course_id, lecturer_ssn = section_row
                
                # Step 2: Fetch Parent Course Information
                self.cursor.execute("SELECT courseID, name, credit, prerequisiteID, courseType FROM Course WHERE courseID = ?;", (course_id,))
                parent_course_row = self.cursor.fetchone()
                parent_course = None
                if parent_course_row:
                    course_id, name, credit, prerequisite_id, course_type = parent_course_row
                    #BURASI DU
                    #BURASI DU
                    parent_course = Course(course_id, name, credit, prerequisite_id, course_type)
        
                # Step 3: Fetch TimeSlots for the Section
                self.cursor.execute("SELECT day, timeInterval, classroom FROM TimeSlot WHERE sectionID = ?;", (section_id,))
                time_slots_data = self.cursor.fetchall()
                time_slots = [TimeSlot(day=row[0], time_interval=row[1], classroom=row[2]) for row in time_slots_data]

                # Step 4: Create CourseSection Object
                course_section = CourseSection(
                    section_id=section_id,
                    capacity=capacity,
                    parent_course=parent_course,
                    lecturer=None  # Lecturer object can be populated later if needed
                )
               
               
                course_section.set_time_slots(time_slots)
                courseSections.append(course_section)
                # Step 5: Add CourseSection to the List
        return courseSections

    def get_student_without_advisor(self, student_id: str) -> Student:
        exist_student = self.check_student_exists(student_id)
        if exist_student is not None:
            return exist_student
        try:
            self.cursor.execute(f"SELECT * FROM Student s WHERE s.studentID = '{student_id}'")
            row = self.cursor.fetchone()
            if row:
                currentCourses: list[Course] = self.get_courses_of_transcript(student_id, "CurrentCourse")
                waitedCourses: list[Course] = self.get_courses_of_transcript(student_id, "WaitedCourse")
                completedCourses: list[Course] = self.get_courses_of_transcript(student_id, "CompletedCourse")
                currentSections: list[CourseSection] = self.get_course_sections_from_course(student_id, "CurrentSection")
                waitedSections: list[CourseSection] = self.get_course_sections_from_course(student_id, "WaitedSection")
                transcript = Transcript(completedCourses, currentCourses, waitedCourses, currentSections, waitedSections, row[6])
                student = Student(name= row[1], surname=row[2], birthdate=row[4], gender=row[3], transcript=transcript, student_id=row[0])
                self.students.append(student)
                return student
        except sqlite3.Error as e:
            print("SQLite error:", e)
            return None
        return None
    
    def get_student(self, student_id: str) -> Student:
        exist_student = self.check_student_exists(student_id)
        if exist_student is not None:
            return exist_student
        student = self.get_student_without_advisor(student_id)
        self.cursor.execute(f"SELECT * FROM StudentsOfAdvisor s WHERE s.studentID = '{student_id}'")
        row = self.cursor.fetchone()
        if row:
            advisor = self.get_advisor(row[1])
            student.set_advisor(advisor)
        return student

        
    def get_advisor(self, id: str) -> Advisor:
        try:
            self.cursor.execute(f"SELECT * FROM Lecturer a WHERE a.ssn = '{id}'")
            row = self.cursor.fetchone()
            if row:
                advisor = Advisor(name=row[1], surname=row[2], birthdate=row[3], gender=row[4], ssn=row[0])
                self.cursor.execute(f"select * from StudentsOfAdvisor s where s.advisorID = '{id}'")
                rows = self.cursor.fetchall()
                for row in rows:
                    student = self.get_student_without_advisor(row[0])
                    advisor.add_student(student)
                self.advisors.append(advisor)
                return advisor
            else:
                return None
        except sqlite3.Error as e:
            print("SQLite error:", e)
            return None
        return None       
    
    def check_student_exists(self, student_id: str) -> Student:
        try:
            for student in self.students:
                if student.get_id() == student_id:
                    return student
        except Exception as e:
            print(f'There is an error in check_student_exists function: {e}')
            print(f'Exception type: {type(e).__name__}')
            return None
        return None
    
    def get_students(self) -> list[Student]:
        return self.students
    def get_course_sections(self) -> list[CourseSection]:
        return self.courseSections
    def get_courses(self) -> list[Course]:
        return self.courses
    def get_advisors(self) -> list[Advisor]:
        return self.advisors

   
    """
    #Add new student to Student table
    def add_student(self, student: Student) -> None:
    #Delete that student from Student table
    def delete_student(self, student: Student) -> None:
    #Add new advisor to Advisor table
    def add_advisor(self, advisor: Advisor) -> None:
    #Delete that advisor from Advisor table
    def delete_advisor(self, advisor: Advisor) -> None:
    #Add new lecturer to Lecturer table
    def add_lecturer(self, lecturer: Lecturer) -> None:
    #Delete that lecturer from Lecturer table
    def delete_lecturer(self, lecturer: Lecturer) -> None:
    #Add new department scheduler to DepartmentScheduler table
    def add_department_scheduler(self, department_scheduler: DepartmentScheduler) -> None:
    #Delete that department scheduler from DepartmentScheduler table
    def delete_department_scheduler(self, department_scheduler: DepartmentScheduler) -> None:

    """ 

