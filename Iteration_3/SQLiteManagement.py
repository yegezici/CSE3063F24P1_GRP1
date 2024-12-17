import sqlite3
from Student import Student
from CourseSection import CourseSection
from Course import Course
from TimeSlot import TimeSlot


class SqliteManager:

    def __init__(self):
        conn = sqlite3.connect('Iteration_3/database/test.db')
        cursor = self.conn.cursor()
        self.courseSections = self.initialize_courseSections()

    def print_table(self, table_name: str):        
        try:
            self.cursor.execute(f"SELECT * FROM {table_name}")
            rows = self.cursor.fetchall()
            for row in rows:
              print(row)
        except sqlite3.Error as e:
            print("SQLite error:", e)
    
    def find_user(self, username: str, password: str) -> bool:
        try:
            self.cursor.execute(f"SELECT * FROM User u WHERE u.UserID = '{username}' AND u.password = '{password}'")
            rows = self.cursor.fetchall()
            return len(rows) > 0
        except sqlite3.Error as e:
            print("SQLite error:", e)
            return False
        
    def save_student(self, student: Student):
        student.get_name()
        
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

                # Step 5: Add CourseSection to the List
        return courseSections

        




manager = SqliteManager()
print(manager.find_user("o150121032", "password2"))
