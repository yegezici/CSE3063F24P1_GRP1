import unittest
from datetime import date

class Student:
    def __init__(self, first_name, last_name, birthdate, gender, transcript, student_id):
        self.first_name = first_name
        self.last_name = last_name
        self.birthdate = birthdate
        self.gender = gender
        self.transcript = transcript
        self.student_id = student_id

class Transcript:
    def __init__(self):
        self.current_courses = []
        self.waited_courses = []

class CourseSection:
    def __init__(self, course_id, capacity):
        self.course_id = course_id
        self.capacity = capacity
        self.current_students = []

    def add_student_to_section(self, student):
        self.current_students.append(student)

    def get_current_students(self):
        return self.current_students

class TestCourseSection(unittest.TestCase):

    def setUp(self):
        self.student = Student("Melisa", "Gezer", date.today(), 'F', Transcript(), "150120101")
        self.course_section = CourseSection("CSE101", 15)

    def test_add_student_to_section(self):
        # Before adding the student, currentStudents should be empty
        self.assertTrue(len(self.course_section.get_current_students()) == 0, "Current students list should be empty before adding a student")

        # Add the student to the section
        self.course_section.add_student_to_section(self.student)

        # After adding the student, currentStudents should contain the student
        self.assertTrue(self.student in self.course_section.get_current_students(), "Current students list should contain the added student")

if __name__ == "__main__":
    unittest.main()
