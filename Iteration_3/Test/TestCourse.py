import unittest
from datetime import date
import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)

# Class representing a mandatory course
class MandatoryCourse:
    def __init__(self, course_id, course_name, credits):
        """
        Initialize a mandatory course with a course ID, name, credits, and an empty list of students.
        """
        self.course_id = course_id
        self.course_name = course_name
        self.credits = credits
        self.students = []  # List to store enrolled students
        self.prerequisite_course = None  # No prerequisite course by default

    def add_student(self, student):
        """
        Add a student to the course.
        """
        self.students.append(student)

    def get_students(self):
        """
        Return the list of students enrolled in the course.
        """
        return self.students

    def set_prerequisite_course(self, prerequisite_course):
        """
        Set the prerequisite course for this course.
        """
        self.prerequisite_course = prerequisite_course

    def get_prerequisite_course(self):
        """
        Return the prerequisite course for this course.
        """
        return self.prerequisite_course

    def get_course_id(self):
        """
        Return the course ID.
        """
        return self.course_id

    def get_credits(self):
        """
        Return the number of credits for the course.
        """
        return self.credits

# Class representing a student
class Student:
    def __init__(self, first_name, last_name, birthdate, gender, transcript, student_id):
        """
        Initialize a student with personal information, a transcript, and a student ID.
        """
        self.first_name = first_name
        self.last_name = last_name
        self.birthdate = birthdate
        self.gender = gender
        self.transcript = transcript  # Transcript object associated with the student
        self.student_id = student_id

# Class representing a student's transcript
class Transcript:
    def __init__(self):
        """
        Initialize a transcript with lists for current and waited courses.
        """
        self.current_courses = []  # List of current courses the student is enrolled in
        self.waited_courses = []  # List of courses the student is waiting for

# Test class for the MandatoryCourse class
class TestCourse(unittest.TestCase):

    def setUp(self):
        """
        Set up the test environment by initializing the course, student, and prerequisite course objects.
        This function runs before each individual test.
        """
        # Initialize the Course object
        self.course = MandatoryCourse("CSE2225", "Data Structures", 7)

        # Initialize the Student object
        self.transcript = Transcript()
        self.student = Student("Melisa", "Gezer", date.today(), 'F', self.transcript, "150120101")

        # Initialize the prerequisite course
        self.prerequisite_course = MandatoryCourse("MATH1002", "Calculus I", 5)

    def test_add_student(self):
        """
        Test if a student can be successfully added to the course.
        """
        self.course.add_student(self.student)
        
        # Assert that the student has been added to the course's student list.
        self.assertTrue(self.student in self.course.get_students(), "Student should be added to the course.")

    def test_get_course_id(self):
        """
        Test if the correct course ID is returned by the method.
        """
        self.assertEqual(self.course.get_course_id(), "CSE2225", "Course ID should be CSE2225")

    def test_set_and_get_prerequisite_course(self):
        """
        Test if the prerequisite course can be set and retrieved correctly.
        """
        self.course.set_prerequisite_course(self.prerequisite_course)
        
        # Assert that the prerequisite course is correctly set.
        self.assertEqual(self.course.get_prerequisite_course(), self.prerequisite_course, "Prerequisite course should be MATH1002")

    def test_get_credits(self):
        """
        Test if the correct number of credits is returned for the course.
        """
        self.assertEqual(self.course.get_credits(), 7, "The course should have 7 credits.")
 
if __name__ == "__main__":
    # Run the tests
    unittest.main() 
