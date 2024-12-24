import unittest
from datetime import date
import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)

from Course import Course
from MandatoryCourse import MandatoryCourse
from CourseSection import CourseSection
from Student import Student
from Transcript import Transcript
 
class TestTranscript(unittest.TestCase):

    def test_add_course_sections(self):
        """
        Test if course sections are successfully added to the transcript.
        """
        # Creating an example Course object.
        parent_course = Course(course_id="CSE101", course_name="Introduction to Programming", credits=3)
        
        # Creating a necessary CourseSection object.
        lecturer_mock = None  # If the Lecturer class is not ready, we use None.
        section1 = CourseSection(section_id="CSE101.1", capacity=15, parent_course=parent_course, lecturer=lecturer_mock)
        
        # Verifying CourseSection details.
        self.assertEqual(section1.get_section_id(), "CSE101.1")  # Checking the section ID.
        self.assertEqual(section1.get_capacity(), 15)  # Checking the capacity of the section.
        self.assertEqual(section1.get_parent_course(), parent_course)  # Checking the parent course of the section.

    def test_add_waited_course(self):
        """
        Test if a course is successfully added to the waited courses list.
        """
        course = MandatoryCourse(course_id="CSE101", course_name="Data Engineering", credits=4)
        student = Student(student_id="12345", name="John Doe")  # Creating a default Student object.

        # Adding a student to the waitlist of the CourseSection.
        section = CourseSection(section_id="CSE101.1", capacity=0, parent_course=course, lecturer=None)
        section.get_wait_list().append(student)  # Adding student to waitlist.

        # Verifying that the student is in the waitlist.
        self.assertIn(student, section.get_wait_list())

    def test_calculate_gpa(self):
        """
        Test if the GPA is calculated correctly.
        """
        # Creating example MandatoryCourse objects.
        course1 = MandatoryCourse(course_id="CSE101", course_name="Data Engineering", credits=4)
        course2 = MandatoryCourse(course_id="CSE102", course_name="Algorithms", credits=3)

        # Setting grades for the courses.
        course1.set_grade("BA")
        course2.set_grade("AA")
        
        # Testing GPA calculation function.
        gpa = self.calculate_gpa([course1, course2])
        self.assertAlmostEqual(gpa, 3.7, places=1)  # Verifying that the calculated GPA is 3.7.

    def calculate_gpa(self, courses):
        """
        Basit bir GPA hesaplama fonksiyonu.
        """
        total_points = 0
        total_credits = 0
        grade_to_points = {"AA": 4.0, "BA": 3.5, "BB": 3.0, "CB": 2.5, "CC": 2.0, "DC": 1.5, "DD": 1.0, "FF": 0.0}

        # Calculating total points and credits for each course.
        for course in courses:
            grade = course.get_grade()
            if grade in grade_to_points:
                total_points += grade_to_points[grade] * course.get_credits()
                total_credits += course.get_credits()

        # Returning GPA based on total points and credits.
        return total_points / total_credits if total_credits > 0 else 0.0

    def test_delete_from_waited_course(self):
        """
        Test if a course is successfully deleted from the waited courses list.
        """
        course = MandatoryCourse(course_id="CSE101", course_name="Data Engineering", credits=4)
        student = Student(student_id="12345", name="John Doe")
        
        # Adding a student to the waitlist.
        section = CourseSection(section_id="CSE101.1", capacity=0, parent_course=course, lecturer=None)
        section.get_wait_list().append(student)
        
        # Removing the student from the waitlist.
        section.get_wait_list().remove(student)
        self.assertNotIn(student, section.get_wait_list())  # Verifying that the student is no longer in the waitlist.

    def test_get_waited_courses(self):
        """
        Test if the list of waited courses contains the added courses.
        """
        self.transcript = Transcript([], [], [])  # Creating a Transcript object for the student.
        course = MandatoryCourse(course_id="CSE101", course_name="Data Engineering", credits=4)
        student = Student(name="Melisa", surname="Gezer", birthdate=date(2000, 1, 1), gender="F", transcript=self.transcript, student_id="150120101")
        
        # Adding a student to the waitlist of a course section.
        section = CourseSection(section_id="CSE101.1", capacity=0, parent_course=course, lecturer=None)
        section.get_wait_list().append(student)

        # Verifying that the student is in the waitlist.
        self.assertEqual(len(section.get_wait_list()), 1)  # Checking if the waitlist contains one student.
        self.assertEqual(section.get_wait_list()[0], student)  # Ensuring the student is in the waitlist.

if __name__ == "__main__":
    unittest.main()
