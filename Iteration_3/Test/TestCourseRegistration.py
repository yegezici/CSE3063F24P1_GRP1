import unittest
from unittest.mock import MagicMock

import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)

from CourseRegistration import CourseRegistration  
 
class TestCourseRegistration(unittest.TestCase):

    def setUp(self):
        """
        Set up the test environment by creating mock objects and initializing the CourseRegistration object.
        This method runs before each individual test.
        """ 
        # Create a mock object for the manager
        self.manager_mock = MagicMock()
        
        # Create a CourseRegistration instance and assign the mock manager to it
        self.course_registration = CourseRegistration()
        self.course_registration.manager = self.manager_mock

    def test_login_valid_user(self):
        """
        Test if the login function works with a valid user ID and password.
        """
        entered_user_id = "12345"  # Valid user ID
        entered_password = "password"  # Valid password
        mock_person = MagicMock()  # Mock object for a valid user
        self.manager_mock.check_user.return_value = mock_person  # The check_user method returns the mock person
        
        # Test the login function with valid ID and password
        result = self.course_registration.check_id_and_password(entered_user_id, entered_password)
        
        # Check if the check_user method was called with the correct parameters
        self.manager_mock.check_user.assert_called_with(entered_user_id, entered_password)
        self.assertEqual(result, mock_person)  # The result should be the mock person

    def test_check_id_and_password_invalid_user(self):
        """
        Test if the login function handles invalid user ID and password correctly.
        """
        entered_user_id = "invalid_id"  # Invalid user ID
        entered_password = "wrong_password"  # Invalid password
        self.manager_mock.check_user.return_value = None  # check_user returns None for invalid users
        
        # Test the login function with invalid ID and password
        result = self.course_registration.check_id_and_password(entered_user_id, entered_password)
        
        # Check if the check_user method was called with the correct parameters
        self.manager_mock.check_user.assert_called_with(entered_user_id, entered_password)
        self.assertIsNone(result)  # The result should be None for invalid users

if __name__ == "__main__":
    # Run the tests with verbosity level 2 for detailed output
    unittest.main()
