import unittest
from unittest.mock import MagicMock, patch
from datetime import date
import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)

from Student import Student
from DepartmentHead import DepartmentHead, CourseSection, MandatoryCourse, NotificationSystem
 
class TestDepartmentHead(unittest.TestCase):

    def setUp(self):
        # Create a DepartmentHead instance with the correct constructor parameters
        self.department_head = DepartmentHead(
            name="John",  
            surname="Doe", 
            birthdate=date(1980, 5, 10), 
            gender="Male", 
            ssn="D12345"  # Correct usage of ssn
        )
        
        # Create a mock NotificationSystem instance
        self.notification_system_mock = MagicMock(NotificationSystem)

    @patch('DepartmentHead.CourseSection')  # Mocking CourseSection class
    def test_create_course(self, MockCourseSection):
        # Test creating a course
        course_name = "Introduction to Programming"
        course_id = "CS101"
        course_type = "m"  # Mandatory course
        credits = 3
        number_of_sections = 2
        capacity = 30
        
        # Mock the CourseSection to prevent constructor errors
        MockCourseSection.return_value = MagicMock(CourseSection)
        
        # Call create_course
        course = self.department_head.create_course(course_name, course_id, course_type, credits, number_of_sections, capacity)
        
        # Ensure the course sections are created correctly
        self.assertIsNotNone(course.get_course_sections())  # Ensure course sections are created
        self.assertEqual(len(course.get_course_sections()), number_of_sections)

    def test_create_course_section(self):
        # Testing create_course_section method
        course = MandatoryCourse("CS101", "Introduction to Programming", 3)
        number_of_sections = 3
        capacity = 30  # Define a capacity for the sections
        
        # Mocking CourseSection initialization
        course_section_mock = MagicMock(CourseSection)
        course_section_mock.set_parent_course = MagicMock()
        course_section_mock.set_section_id = MagicMock()
        
        # Patching the constructor to return the mocked course section
        with patch('DepartmentHead.CourseSection', return_value=course_section_mock):
            sections = self.department_head.create_course_section(number_of_sections, course, capacity)  # Pass capacity here
        
        # Checking if the correct number of sections are created
        self.assertEqual(len(sections), number_of_sections)
        for section in sections:
            self.assertIsInstance(section, CourseSection)

    @patch('builtins.input', return_value='30')  # Mocking input to avoid waiting for user input
    def test_set_capacity(self, mock_input):
        # Testing set_capacity method
        course_section = MagicMock(CourseSection)  # Mock CourseSection instance
        course_section.set_capacity = MagicMock()  # Mocking set_capacity method
        course_section.capacity = 10  # Set initial capacity
        
        self.department_head.set_capacity(course_section)
        
        # Checking if the set_capacity method was called with the mocked input (30)
        course_section.set_capacity.assert_called_once_with(30)

    def test_manage_capacity(self):
        # Testing manage_capacity method
        course_section = MagicMock(CourseSection)  # Mock CourseSection instance
        course_section.get_capacity = MagicMock(return_value=10)  # Current capacity is set to 10
        course_section.set_capacity = MagicMock()  # Mocking set_capacity method
        course_section.capacity = 10  # Set initial capacity
        
        new_capacity = 20
        self.department_head.manage_capacity(course_section, new_capacity, self.notification_system_mock)
        
        # Checking if the capacity was updated with the new value
        course_section.set_capacity.assert_called_once_with(new_capacity)

if __name__ == "__main__":
    unittest.main()
