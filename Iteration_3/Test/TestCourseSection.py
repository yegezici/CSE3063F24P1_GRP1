import unittest
from datetime import date

import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)

# Student class represents a student with basic details and a transcript
class Student:
    def __init__(self, first_name, last_name, birthdate, gender, transcript, student_id):
        self.first_name = first_name
        self.last_name = last_name
        self.birthdate = birthdate
        self.gender = gender
        self.transcript = transcript  # A student's transcript object
        self.student_id = student_id  # Unique ID for the student

# Transcript class represents the transcript of a student, including current and waited courses
class Transcript:
    def __init__(self):
        self.current_courses = []  # List to store currently enrolled courses
        self.waited_courses = []   # List to store courses the student is waiting for

# CourseSection class represents a section of a course with a defined capacity and enrolled students
class CourseSection:
    def __init__(self, course_id, capacity):
        self.course_id = course_id  # The course ID associated with this section
        self.capacity = capacity  # The maximum number of students that can enroll
        self.current_students = []  # List to store students currently enrolled in this section

    def add_student_to_section(self, student):
        """
        Adds a student to the current section if space is available.
        """
        self.current_students.append(student)

    def get_current_students(self):
        """
        Returns the list of students currently enrolled in this course section.
        """
        return self.current_students

# Test case class for testing the CourseSection functionality
class TestCourseSection(unittest.TestCase):

    def setUp(self):
        """
        Set up the test environment by creating a Student and a CourseSection object.
        This method runs before each individual test.
        """
        self.student = Student("Melisa", "Gezer", date.today(), 'F', Transcript(), "150120101")
        self.course_section = CourseSection("CSE101", 15)

    def test_add_student_to_section(self):
        """
        Test the functionality of adding a student to a course section.
        It checks if the student is successfully added to the section's student list.
        """
        # Before adding the student, the list of current students should be empty
        self.assertTrue(len(self.course_section.get_current_students()) == 0, "Current students list should be empty before adding a student")

        # Add the student to the section
        self.course_section.add_student_to_section(self.student)

        # After adding the student, the list of current students should contain the student
        self.assertTrue(self.student in self.course_section.get_current_students(), "Current students list should contain the added student")
 
# Run the tests if this script is executed directly
if __name__ == "__main__":
    unittest.main() 
