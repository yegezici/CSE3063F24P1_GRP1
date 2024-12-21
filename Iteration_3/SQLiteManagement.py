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
from DepartmentHead import DepartmentHead
import copy
from typing import Optional
from Person import Person   
from Logging_Config import logger
from Lecturer import Lecturer
from Notification import Notification
from NotificationSystem import NotificationSystem

class SQLiteManagement:


    def __init__(self):
        self.conn = sqlite3.connect('Iteration_3/database/CourseRegSys.db')
        self.cursor = self.conn.cursor()
        self.courses = self.initialize_courses()            
        self.courseSections = self.initialize_courseSections()
        self.set_prerequisites()
        self.students = []
        self.advisors = []
        
    def get_students(self) -> list[Student]:
        return self.students
    def get_course_sections(self) -> list[CourseSection]:
        return self.courseSections
    def get_courses(self) -> list[Course]:
        return self.courses
    def get_advisors(self) -> list[Advisor]:
        return self.advisors
    
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

    
    def init_time_intervals():
        all_time_intervals = [
            "8:30-9:20",
            "9:30-10:20",
            "10:30-11:20",
            "11:30-12:20",
            "12:30-13:20",
            "13:30-14:20",
            "14:30-15:20",
            "15:30-16:20"
        ]
        return all_time_intervals

    
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
            self.cursor.execute(sql, (courseSection.get_section_id, courseSection.get_capacity, courseSection.parent_course.course_id, courseSection.get_lecturer_ssn()))
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

    def save_time_slots(self) -> None:
        try:
            # Get existing data for time slots.
            self.cursor.execute("SELECT id, timeInterval, day, classroom, sectionID FROM TimeSlot")
            existing_time_slots = {
                (row[1], row[2], row[3], row[4]): row[0]  # (timeInterval, day, classroom, sectionID): id
                for row in self.cursor.fetchall()
            }

            # Create set for existing time slots in code.
            current_time_slots = set()

            for section in self.courseSections:
                for time_slot in section.get_time_slots():
                    time_interval = time_slot.get_time_interval()
                    day = time_slot.get_day()
                    classroom = time_slot.get_classroom()
                    section_id = section.get_section_id()

                    current_time_slots.add((time_interval, day, classroom, section_id))

                    # If time slot exist already, update it.
                    if (time_interval, day, classroom, section_id) in existing_time_slots:
                        sql = '''
                        UPDATE TimeSlot
                        SET timeInterval = ?, day = ?, classroom = ?, sectionID = ?
                        WHERE id = ?
                        '''
                        self.cursor.execute(sql, (time_interval, day, classroom, section_id, existing_time_slots[(time_interval, day, classroom, section_id)]))
                    else:
                        # If time slot doesn't exist, create a new one.
                        sql = '''
                        INSERT INTO TimeSlot (timeInterval, day, classroom, sectionID)
                        VALUES (?, ?, ?, ?)
                        '''
                        self.cursor.execute(sql, (time_interval, day, classroom, section_id))

            # Delete time slot record if it doesn't exist anymore.
            for key, id in existing_time_slots.items():
                if key not in current_time_slots:
                    self.cursor.execute("DELETE FROM TimeSlot WHERE id = ?", (id,))

            self.conn.commit()

        except sqlite3.IntegrityError as e:
            print(f"Integrity error while saving TimeSlot: {e}")
        except sqlite3.Error as e:
            print(f"SQLite error while saving TimeSlot: {e}")


    
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
                for course in self.courses:
                    if course.get_course_id() == course_id:
                        parent_course = course
                        break
                        
                # Step 3: Fetch TimeSlots for the Section
                self.cursor.execute("SELECT id, day, timeInterval, classroom FROM TimeSlot WHERE sectionID = ?;", (section_id,))
                time_slots_data = self.cursor.fetchall()
                time_slots = []
                for row in time_slots_data:
                    time_slot = TimeSlot(
                        day=row[1], 
                        time_interval=row[2], 
                        classroom=row[3], 
                    ) 
                    time_slots.append(time_slot)
                
                # Step 4: Create CourseSection Object
                course_section = CourseSection(
                    section_id=section_id,
                    capacity=capacity,
                    parent_course=parent_course,
                    lecturer=None  # Lecturer object can be populated later if needed
                )
                course_section.set_time_slots(time_slots)
                courseSections.append(course_section)
                parent_course.get_course_sections().append(course_section)
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
    
    def get_department_head(self, headID: str) -> DepartmentHead:
        try:
            self.cursor.execute(f"SELECT * FROM DeparmentHead d WHERE d.headID = '{headID}'")
            row = self.cursor.fetchone()
            if row:
                dephead = DepartmentHead(name=row[1], surname=row[2], birthdate=row[3], gender=row[4], id=headID,)
                return dephead
            else:
                return None
        except sqlite3.Error as e:
            print("SQLite error:", e)
            return None 

    def get_deparment_scheduler(self, schedulerID: str) -> DepartmentScheduler:
        try:
            self.cursor.execute(f"SELECT * FROM DeparmentScheduler d WHERE d.scheduler = '{schedulerID}'")
            row = self.cursor.fetchone()
            if row:
                depsch = DepartmentScheduler(name=row[1], surname=row[2], birthdate=row[3], gender=row[4],
                                             id=schedulerID,
                                             courses=self.courses,
                                             course_sections=self.courseSections,
                                             all_time_intervals= self.init_time_intervals())
                return depsch
            else:
                return None
        except sqlite3.Error as e:
            print("SQLite error:", e)
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

    #Add new student to Student table
    def add_student(self, student: Student, password :str) -> None:
        try:
            self.cursor.execute(f"INSERT INTO User (UserID, password, userType) VALUES (?, ?, ?);", 
                                (student.get_id(), password, 'S')),
            self.cursor.execute(f"INSERT INTO Student (studentID, name, surname, birthdate, gender, transcriptID) VALUES (?, ?, ?, ?, ?);", 
                                (student.get_id(), student.get_name(), student.get_surname(), student.get_birthdate(), student.get_gender()))
            self.connection.commit()
        except sqlite3.Error as e:
            print("SQLite error:", e)
            
        #Delete that student from Student table
    def delete_student(self, student: Student) -> None:
        try:
            self.cursor.execute(f"DELETE FROM Student WHERE studentID = '{student.get_id()}'")
            self.cursor.execute(f"DELETE FROM User WHERE UserID = '{student.get_id()}'")
            self.connection.commit()
        except sqlite3.Error as e:
            print("SQLite error:", e)
       
        #Add new advisor to Advisor table
    def add_advisor(self, advisor: Advisor, password: str) -> None:
        try: 
            self.cursor.execute(f"INSERT INTO User (UserID, password, userType) VALUES (?, ?, ?);", 
                                (advisor.get_ssn(), password, 'A'))
            self.cursor.execute(f"INSERT INTO Lecturer (ssn, name, surname, birthdate, gender) VALUES (?, ?, ?, ?, ?);",
                                advisor.get_ssn(), advisor.get_name(), advisor.get_surname(), advisor.get_birthdate(), advisor.get_gender())
            for student in advisor.get_students():
                self.cursor.execute(f"INSERT INTO StudentOfAdvisor (studentID, advisorID) VALUES (?, ?);",
                                    (student.get_id(), advisor.get_ssn()))
            self.connection.commit()
        except sqlite3.Error as e:
            print("SQLite error:", e)
            
    #Delete that advisor from Advisor table
    def delete_advisor(self, advisor: Advisor) -> None:   
        try:
            self.cursor.execute(f"DELETE FROM Lecturer WHERE ssn = '{advisor.get_ssn()}'")
            self.cursor.execute(f"DELETE FROM User WHERE UserID = '{advisor.get_ssn()}'")
            for student in advisor.get_students():
                self.cursor.execute(f"DELETE FROM StudentOfAdvisor WHERE advisorID = '{advisor.get_ssn()}'")         
            self.connection.commit()
        except sqlite3.Error as e:
            print("There is an error in delete_advisor function in SQLiteManagement.py\nAdvisor is not deleted.\nSQLite error:", e)
    
    #Add new lecturer to Lecturer table
    def add_lecturer(self, lecturer: Lecturer) -> None:
        try:
            self.cursor.execute(f"INSERT INTO Lecturer (ssn, name, surname, birthdate, gender) VALUES (?, ?, ?, ?, ?);",
                                (lecturer.get_ssn(), lecturer.get_name(), lecturer.get_surname(), lecturer.get_birthdate(), lecturer.get_gender()))
            self.connection.commit()
        except sqlite3.Error as e:
            print("There is an error in add_lecturer function.\nLecturer is not added.\nSQLite error:", e)
            
    #Delete that lecturer from Lecturer table
    def delete_lecturer(self, lecturer: Lecturer) -> None:
        try:
            self.cursor.execute(f"DELETE FROM Lecturer WHERE ssn = '{lecturer.get_ssn()}'")
            self.connection.commit()
        except sqlite3.Error as e:
            print("There is an error in delete_lecturer function in SQLiteManagement.py\nLecturer is not deleted.\nSQLite error:", e)
    
    #Add new department scheduler to DepartmentScheduler table
    def add_department_scheduler(self, department_scheduler: DepartmentScheduler) -> None:
        try:
            self.cursor.execute(f"INSERT INTO DepartmentScheduler (ssn, name, surname, birthdate, gender) VALUES (?, ?, ?, ?, ?);",
                                (department_scheduler.get_ssn(), department_scheduler.get_name(), department_scheduler.get_surname(), department_scheduler.get_birthdate(), department_scheduler.get_gender()))
            self.connection.commit()
        except sqlite3.Error as e:
            print("There is an error in add_department_scheduler function.\nDepartment Scheduler is not added.\nSQLite error:", e)
    
    #Delete that department scheduler from DepartmentScheduler table
    def delete_department_scheduler(self, department_scheduler: DepartmentScheduler) -> None:     
        try:
            self.cursor.execute(f"DELETE FROM DepartmentScheduler WHERE ssn = '{department_scheduler.get_ssn()}'")
            self.connection.commit()        
        except sqlite3.Error as e:
            print("There is an error in delete_department_scheduler function in SQLiteManagement.py\nDepartment Scheduler is not deleted.\nSQLite error:", e)

    def get_user(self, userID : str)-> Person:
        try:
            self.cursor.execute(f"SELECT userType FROM User WHERE UserID = '{userID}'")
            row = self.cursor.fetchone()
            if row:
                if row[2] == 'S':
                    return self.get_student(row[0])
                if row[2] == 'A':
                    return self.get_advisor(row[0])
        except sqlite3.Error as e:
            print("SQLite error:", e)
            return None
        
    def initialize_notification_system(self)-> NotificationSystem:
        try:
            self.cursor.execute(f"SELECT (receiverID, senderID, message) FROM Notification")
            rows = self.cursor.fetchall()
            notifications = []
            for row in rows:
                receiver = self.get_user(row[0])
                sender = self.get_user(row[1])
                notification = Notification(sender, receiver, row[2])
                notifications.append(notification)
            return NotificationSystem(notifications)
        except sqlite3.Error as e:
            print("SQLite error:", e)
            
    def save_notification(self, receiver : Person, sender : Person, message : str)-> None:
        try:
            self.cursor.execute(f"INSERT INTO Notification (receiverID, senderID, message) VALUES (?, ?, ?);",
                                (receiver.get_ssn(), sender.get_ssn(), message))
            self.connection.commit()
        except sqlite3.Error as e:
            print("SQLite error:", e)
            
    def delete_notification(self, notification : Notification)-> None:
        try:
            self.cursor.execute(f"DELETE FROM Notification WHERE receiverID = '{notification.get_receiver().get_ssn()}' AND senderID = '{notification.get_sender().get_ssn()}' AND message = '{notification.get_message()}'")
            self.connection.commit()
        except sqlite3.Error as e:
            print("SQLite error:", e)
            
    

