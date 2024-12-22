import unittest
from unittest.mock import MagicMock
from AdminInterface import AdminInterface
from Admin import Admin
from CourseSection import CourseSection
from Admin import Admin
from Iteration_3.Staff import Staff

class TestAdminInterface(unittest.TestCase):
    
    def setUp(self):
        # Create mock Admin and CourseSections
        self.mock_admin = MagicMock(spec=Admin)
        self.mock_course_sections = [MagicMock(spec=CourseSection) for _ in range(3)]
        
        # Create AdminInterface instance
        self.interface = AdminInterface(self.mock_admin, self.mock_course_sections)

    def test_get_choice_valid_input(self):
        """
        Test if get_choice() correctly returns the user's valid choice.
        """
        user_input = "1\n"
        with unittest.mock.patch("builtins.input", return_value=user_input):
            choice = self.interface.get_choice()
        self.assertEqual(choice, 1)

    def test_get_choice_invalid_input(self):
        """
        Test if get_choice() handles invalid inputs and returns 0.
        """
        user_input = "invalid\n"
        with unittest.mock.patch("builtins.input", return_value=user_input):
            choice = self.interface.get_choice()
        self.assertEqual(choice, 0)

    def test_student_to_add(self):
        """
        Test the creation of a student object in student_to_add().
        """
        inputs = iter([
            "S123", "John", "Doe", "Male", "2000-05-15", "A456"
        ])
        self.mock_admin.get_advisors.return_value = [MagicMock(get_id=lambda: "A456")]

        with unittest.mock.patch("builtins.input", lambda _: next(inputs)):
            student = self.interface.student_to_add()

        self.assertIsNotNone(student)
        self.assertEqual(student.get_id(), "S123")

    def test_advisor_to_add_with_valid_lecturer(self):
        """
        Test the creation of an advisor from a valid lecturer in advisor_to_add().
        """
        mock_lecturer = MagicMock()
        mock_lecturer.get_id.return_value = "L789"
        self.mock_admin.get_lecturers.return_value = [mock_lecturer]

        with unittest.mock.patch("builtins.input", return_value="L789"):
            advisor = self.interface.advisor_to_add()

        self.assertIsNotNone(advisor)
        self.assertEqual(advisor.get_id(), "L789")

    def test_advisor_to_add_with_invalid_lecturer(self):
        """
        Test advisor_to_add() when the provided lecturer ID is invalid.
        """
        self.mock_admin.get_lecturers.return_value = []
        with unittest.mock.patch("builtins.input", return_value="L999"):
            advisor = self.interface.advisor_to_add()

        self.assertIsNone(advisor)

    def test_lecturer_to_add(self):
        """
        Test the creation of a lecturer object in lecturer_to_add().
        """
        inputs = iter(["L123", "Jane", "Doe", "Female", "1985-10-10"])

        with unittest.mock.patch("builtins.input", lambda _: next(inputs)):
            lecturer = self.interface.lecturer_to_add()

        self.assertIsNotNone(lecturer)
        self.assertEqual(lecturer.get_id(), "L123")

    def test_scheduler_to_add_with_valid_lecturer(self):
        """
        Test the creation of a department scheduler from a valid lecturer in scheduler_to_add().
        """
        mock_lecturer = MagicMock()
        mock_lecturer.get_id.return_value = "L999"
        self.mock_admin.get_lecturers.return_value = [mock_lecturer]

        with unittest.mock.patch("builtins.input", return_value="L999"):
            scheduler = self.interface.scheduler_to_add()

        self.assertIsNotNone(scheduler)
        self.assertEqual(scheduler.get_id(), "L999")

    def test_scheduler_to_add_with_invalid_lecturer(self):
        """
        Test scheduler_to_add() when the provided lecturer ID is invalid.
        """
        self.mock_admin.get_lecturers.return_value = []
        with unittest.mock.patch("builtins.input", return_value="L888"):
            scheduler = self.interface.scheduler_to_add()

        self.assertIsNone(scheduler)


if __name__ == "__main__":
    unittest.main(verbosity=2)
