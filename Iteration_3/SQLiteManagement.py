import sqlite3
from Student import Student
from CourseSection import CourseSection
from Course import Course
from TimeSlot import TimeSlot
from Transcript import Transcript
from MandatoryCourse import MandatoryCourse
from NonTechnicalElectiveCourse import NonTechnicalElectiveCourse
from TechnicalElectiveCourse import TechnicalElectiveCourse

class SqliteManager:

    def __init__(self):
        self.conn = sqlite3.connect('Iteration_3/database/CourseRegistration.db')
        self.cursor = self.conn.cursor()
        self.courseSections = self.initialize_courseSections()
        self.courses = self.initialize_courses()
    def print_table(self, table_name: str) ->None:        
        try:
            self.cursor.execute(f"SELECT * FROM {table_name}")
            rows = self.cursor.fetchall()
            for row in rows:
              print(row)
        except sqlite3.Error as e:
            print("SQLite error:", e)

    def initialize_courses(self) -> list:
        courses = []
        self.cursor.execute("SELECT * FROM Course")
        rows = self.cursor.fetchall()
        for row in rows:
            if row[4] == 'm':
                course =  MandatoryCourse(row[0], row[1], row[2], None ,row[4])
            elif row[4] == 'te':
                course = NonTechnicalElectiveCourse(row[0], row[1], row[2], row[4])
            elif row[4] == 'nte':
                course = TechnicalElectiveCourse(row[0], row[1], row[2], row[3])
                    
                
            courses.append(Course(row[0], row[1], row[2], row[3], row[4]))
        return courses

    def set_prerequisites(self) -> None:
        for course in self.courses:
            self.cursor.execute(f"SELECT prerequisiteID FROM Course WHERE courseID = '{course.get_course_id}'")
            rows = self.cursor.fetchall()
            for row in rows:
                course.set_prerequisite(row[0])
    def find_user(self, username: str, password: str) -> bool:
        try:
            self.cursor.execute(f"SELECT * FROM User u WHERE u.UserID = '{username}' AND u.password = '{password}'")
            rows = self.cursor.fetchall()
            return len(rows) > 0
        except sqlite3.Error as e:
            print("SQLite error:", e)
            return False
        
    
    def save_student(self, student: Student) -> None:
        try:
            sql = '''
            INSERT INTO Student (studentID, name, surname, gender, birthDate, advisorID)
            VALUES (?, ?, ?, ?, ?, ?)
            '''
            #ADVISOR ID EKSIK
            self.cursor.execute(sql, (student.get_id, student.get_name, student.get_surname, student.get_gender, student.get_birthdate,  ))
            self.conn.commit()        
       
        except sqlite3.IntegrityError as e:
            print(f"Error: {e}")
    
    
    def save_courseSection(self, courseSection: CourseSection) -> None:
        try:
            sql = '''
            INSERT INTO CourseSection (sectionID, capacity, courseID, lecturerSSN)
            VALUES (?, ?, ?, ?)
            '''
            self.cursor.execute(sql, (courseSection.get_section_id, courseSection.get_capacity, courseSection.parent_course.course_id, courseSection.get_lecturer_ssn))
            self.conn.commit()        
       
        except sqlite3.IntegrityError as e:
            print(f"Error: {e}")
            
    def save_course(self, course: Course) -> None:
        try:
            sql = '''
            INSERT INTO Course (courseID, name, credit, prerequisiteID, courseType)
            VALUES (?, ?, ?, ?, ?)
            '''
            self.cursor.execute(sql, (course.get_course_id, course.get_name, course.get_credit, course.get_prerequisite_id, course.get_course_type))
            self.conn.commit()
        except sqlite3.IntegrityError as e:
            print(f"Error: {e}")
            
    def get_student(self, student_id: str) -> Student:
        try:
            self.cursor.execute(f"SELECT * FROM Student s WHERE s.studentID = '{student_id}'")
            row = self.cursor.fetchone()
            if row:
                return Student(row[0], row[1], row[2], row[3], row[4], row[5])
            else:
                return None
        except sqlite3.Error as e:
            print("SQLite error:", e)
            return None
    
    def get_transcript(self, student_id: str)-> Transcript:
        try:
            self.cursor.execute(f"SELECT * FROM Transcript t WHERE t.studentID = '{student_id}'")
            rows = self.cursor.fetchall()
            return rows
        except sqlite3.Error as e:
            print("SQLite error:", e)
            return None
    
    def get_courses_of_transcript(self, student_id: str, courseList_type: str)-> list:
        courses = []
        try:
            self.cursor.execute(f"SELECT * FROM {courseList_type} t WHERE t.studentID = '{student_id}'")
            rows = self.cursor.fetchall()
            for row in rows:
                self.cursor.execute(f"SELECT * FROM Course WHERE courseID = '{row[0]}'") 
                row = self.cursor.fetchone()
                course_type = row[4]
                course
                if course_type == 'm':
                    course = new MandatoryCourse(row[0], row[1], row[2], )
                elif course_type == 'te':
                elif course_type == 'nte':    
                
                
                courses.append(row)
                
            return courses
        except sqlite3.Error as e:
            print("SQLite error:", e)
        
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

        




manager = SqliteManager()
for courseSection in manager.courseSections:
    print(courseSection.get_section_id())
    print(courseSection.get_parent_course().get_course_id())
   