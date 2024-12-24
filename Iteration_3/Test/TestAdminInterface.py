import unittest
from unittest.mock import MagicMock
import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)

# Import necessary classes
from AdminInterface import AdminInterface
from Admin import Admin
from CourseSection import CourseSection
from Admin import Admin
from Staff import Staff

class TestAdminInterface(unittest.TestCase):
    
    def setUp(self):
        """
        Set up the test environment by creating mock Admin and CourseSection instances.
        This function runs before each individual test.
        """
        # Create mock Admin and CourseSections
        self.mock_admin = MagicMock(spec=Admin)
        self.mock_course_sections = [MagicMock(spec=CourseSection) for _ in range(3)]
        
        # Create AdminInterface instance using the mocked Admin and CourseSections
        self.interface = AdminInterface(self.mock_admin, self.mock_course_sections)

    def test_get_choice_valid_input(self):
        """
        Test if the get_choice() function correctly returns the user's valid choice.
        """
        user_input = "1\n"
        # Mock the built-in input function to return predefined user input
        with unittest.mock.patch("builtins.input", return_value=user_input):
            choice = self.interface.get_choice()
        # Assert that the choice returned is 1
        self.assertEqual(choice, 1)

    def test_get_choice_invalid_input(self):
        """
        Test if the get_choice() function correctly handles invalid inputs and returns 0.
        """
        user_input = "invalid\n"
        # Mock the built-in input function to return invalid input
        with unittest.mock.patch("builtins.input", return_value=user_input):
            choice = self.interface.get_choice()
        # Assert that invalid input returns 0
        self.assertEqual(choice, 0)

    def test_student_to_add(self):
        """
        Test the creation of a student object in the student_to_add() function.
        """
        # Mock input values for creating a student
        inputs = iter([
            "S123", "John", "Doe", "Male", "2000-05-15", "A456"
        ])
        # Mock the return value of get_advisors to return a valid advisor ID
        self.mock_admin.get_advisors.return_value = [MagicMock(get_id=lambda: "A456")]

        # Mock input function to return the values defined in inputs
        with unittest.mock.patch("builtins.input", lambda _: next(inputs)):
            student = self.interface.student_to_add()

        # Assert that the student is created and has the correct ID
        self.assertIsNotNone(student)
        self.assertEqual(student.get_id(), "S123")

    def test_advisor_to_add_with_valid_lecturer(self):
        """
        Test the creation of an advisor from a valid lecturer ID in advisor_to_add().
        """
        # Mock a valid lecturer object
        mock_lecturer = MagicMock()
        mock_lecturer.get_id.return_value = "L789"
        # Mock get_lecturers to return the mock lecturer
        self.mock_admin.get_lecturers.return_value = [mock_lecturer]

        # Mock input function to return the valid lecturer ID
        with unittest.mock.patch("builtins.input", return_value="L789"):
            advisor = self.interface.advisor_to_add()

        # Assert that the advisor is created and has the correct ID
        self.assertIsNotNone(advisor)
        self.assertEqual(advisor.get_id(), "L789")

    def test_advisor_to_add_with_invalid_lecturer(self):
        """
        Test the advisor_to_add() function when the provided lecturer ID is invalid.
        """
        # Mock get_lecturers to return an empty list (no valid lecturers)
        self.mock_admin.get_lecturers.return_value = []
        # Mock input function to return an invalid lecturer ID
        with unittest.mock.patch("builtins.input", return_value="L999"):
            advisor = self.interface.advisor_to_add()

        # Assert that no advisor is created when an invalid lecturer ID is provided
        self.assertIsNone(advisor)
 
if __name__ == "__main__":
    # Run the tests with verbosity level 2 for detailed output
    unittest.main() 
