import unittest
from datetime import date
import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)
 
from Student import Student
from Transcript import Transcript
from CourseSection import CourseSection
from Course import Course
from Lecturer import Lecturer  
 
class TestStudentRegisterCourse(unittest.TestCase):

    def setUp(self):
        # Create Transcript and Student objects
        self.transcript = Transcript([], [], [])
        self.student = Student(name="Melisa", surname="Gezer", birthdate=date(2000, 1, 1), gender="F", transcript=self.transcript, student_id="150120101")
        
        # Create Course and Lecturer objects
        self.test_course = Course(course_id="CSE2225", course_name="Data Structures", credits=7)
        self.test_lecturer = Lecturer(name="Maraz", surname="Ali")  # Create a lecturer
        
        # Create CourseSection object and set the parent course and lecturer
        self.test_course_section = CourseSection(section_id="CSE2225.1", capacity=8, lecturer=self.test_lecturer, parent_course=self.test_course)

    def test_register_course_adds_course_to_waited_courses(self):
        """Test if the course is added to the student's waited_courses list"""
        self.student.register_course(self.test_course_section)
        
        # Assert: Check if the course is added to the waited_courses list
        self.assertIn(self.test_course, self.transcript.get_waited_courses(), "Course should be added to the student's waited courses list in the transcript.")

    def test_register_course_adds_section_to_waited_sections(self):
        """Test if the course section is added to the student's waited_sections list"""
        self.student.register_course(self.test_course_section)
        
        # Assert: Check if the course section is added to the waited_sections list
        self.assertIn(self.test_course_section, self.transcript.get_waited_sections(), "Course section should be added to the student's waited sections list in the transcript.")

if __name__ == "__main__":
    unittest.main()
