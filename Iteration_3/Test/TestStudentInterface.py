import unittest
from datetime import date
import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)

from Student import Student
from Transcript import Transcript
from Course import Course
from StudentInterface import StudentInterface
from NotificationSystem import NotificationSystem

class TestStudentInterface(unittest.TestCase):

    def setUp(self):
        # Create Transcript and Student objects
        self.transcript = Transcript([], [], [])
        self.student = Student(name="Melisa", surname="Gezer", birthdate=date(2000, 1, 1), gender="F", transcript=self.transcript, student_id="150120101")
        
        # Create courses and add to the list
        self.courses = [
            Course(course_id="IE3107", course_name="Probability", credits=6),
            Course(course_id="IE3108", course_name="Modeling", credits=5)
        ]
        
        # Setting prerequisites
        self.courses[1].set_prerequisite_course(self.courses[0])  # IE3108 requires IE3107
        
        # Create NotificationSystem
        self.notification_system = NotificationSystem()

        # Create StudentInterface object
        self.student_interface = StudentInterface(student=self.student, courses=self.courses, notification_system=self.notification_system)

    def test_show_registerable_courses(self):
        """Test if showRegisterableCourses works as expected"""
        # Initially, the student can only register for IE3107 (because IE3108 has a prerequisite)
        registerable_courses = self.student_interface.show_registrable_courses()
        self.assertEqual(len(registerable_courses), 1, "Registerable courses list should contain one course initially.")
        
        # After completing IE3107, IE3108 should be available
        self.student.get_transcript().get_completed_courses().append(self.courses[0])
        registerable_courses = self.student_interface.show_registrable_courses()
        self.assertEqual(len(registerable_courses), 1, "Registerable courses list should contain one course after prerequisite is met.")
        self.assertEqual(registerable_courses[0].get_course_id(), "IE3108", "The remaining course should be IE3108.")

    def test_check_prerequisite(self):
        """Test if checkPrerequisite works as expected"""
        math_course = self.courses[1]  # IE3108
        programming_course = self.courses[0]  # IE3107
        
        # Initially, the prerequisite for IE3108 is not met
        self.assertFalse(self.student_interface.check_prerequisite(math_course), "Prerequisite for IE3108 should not be met initially.")
        
        # After completing IE3107, the prerequisite for IE3108 should be met
        self.student.get_transcript().get_completed_courses().append(programming_course)
        self.assertTrue(self.student_interface.check_prerequisite(math_course), "Prerequisite for IE3108 should be met after completing IE3107.")

    def test_check_course_exist_in_list(self):
        """Test if checkCourseExistInList works as expected"""
        programming_course = self.courses[0]  # IE3107
        
        # Initially, the course should not exist in the completed courses list
        self.assertFalse(self.student_interface.check_course_exist_in_list(programming_course, self.student.get_transcript().get_completed_courses()), "Course should not exist in the list initially.")
        
        # After adding the course, it should exist in the list
        self.student.get_transcript().get_completed_courses().append(programming_course)
        self.assertTrue(self.student_interface.check_course_exist_in_list(programming_course, self.student.get_transcript().get_completed_courses()), "Course should exist in the list after adding.")
         
if __name__ == "__main__":
    unittest.main()
 
