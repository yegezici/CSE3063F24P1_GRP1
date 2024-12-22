import unittest
from datetime import date
from Admin import Admin
from Iteration_3.Staff import Staff
from Logging_Config import logger
from SQLiteManagement import SqliteManager
from typing import List,Optional

class TestAdmin(unittest.TestCase):
    
    def setUp(self):
        
        self.admin = Admin(
            _name="Jane",
            _surname="Doe",
            _birthdate=date(1990, 5, 15),
            _ssn="123-45-6789",
            _gender="Female"
        )
        self.mock_student = type('Student', (object,), {'get_id': lambda self: "S123"})()
        self.mock_advisor = type('Advisor', (object,), {'get_id': lambda self: "A456"})()
        self.mock_lecturer = type('Lecturer', (object,), {'get_id': lambda self: "L789"})()
        self.mock_scheduler = type('DepartmentScheduler', (object,), {'get_id': lambda self: "D101"})()

    def test_add_student(self):
        """
        Test if a student is successfully added.
        """
        self.admin.add_student(self.mock_student)
        self.assertIn(self.mock_student, self.admin.get_students())

    def test_delete_student(self):
        """
        Test if a student is successfully deleted.
        """
        self.admin.add_student(self.mock_student)
        self.admin.delete_student("S123")
        self.assertNotIn(self.mock_student, self.admin.get_students())

    def test_add_advisor(self):
        """
        Test if an advisor is successfully added.
        """
        self.admin.add_advisor(self.mock_advisor)
        self.assertIn(self.mock_advisor, self.admin.get_advisors())

    def test_delete_advisor(self):
        """
        Test if an advisor is successfully deleted.
        """
        self.admin.add_advisor(self.mock_advisor)
        self.admin.delete_advisor("A456")
        self.assertNotIn(self.mock_advisor, self.admin.get_advisors())

    def test_add_lecturer(self):
        """
        Test if a lecturer is successfully added.
        """
        self.admin.add_lecturer(self.mock_lecturer)
        self.assertIn(self.mock_lecturer, self.admin.get_lecturers())

    def test_delete_lecturer(self):
        """
        Test if a lecturer is successfully deleted.
        """
        self.admin.add_lecturer(self.mock_lecturer)
        self.admin.delete_lecturer(self.mock_lecturer)
        self.assertNotIn(self.mock_lecturer, self.admin.get_lecturers())

    def test_add_department_scheduler(self):
        """
        Test if a department scheduler is successfully added.
        """
        self.admin.add_department_scheduler(self.mock_scheduler)
        self.assertIn(self.mock_scheduler, self.admin.get_department_schedulers())

    def test_delete_department_scheduler(self):
        """
        Test if a department scheduler is successfully deleted.
        """
        self.admin.add_department_scheduler(self.mock_scheduler)
        self.admin.delete_department_scheduler(self.mock_scheduler)
        self.assertNotIn(self.mock_scheduler, self.admin.get_department_schedulers())

    def test_initialize_interface(self):
        """
        Test if the interface is successfully initialized.
        """
        interface_mock = "InterfaceMock"
        self.admin.set_interface(interface_mock)
        self.assertEqual(self.admin.initialize_interface(), interface_mock)


if __name__ == "__main__":
    unittest.main(verbosity=2)
