from Admin import Admin
from Student import Student
from typing import List
from SQLiteManagement import SQLiteManagement
from Logging_Config import logger

class AdminInterface:
    __init__(self, admin: Admin, advisors: List[Advisor]):
        self.admin = admin
        self.advisors = advisors
    
    def show_menu(self):
        log_out = False
        choice = self.get_choice()
        if choice == 1:
            logger.info(f"Choice 1:  Add Student is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            student_id, student_name, student_surname, student_birthdate, student_AdvisorID = self.student_to_add()
            for advisor in self.advisors:
                if advisor.get_id() == student_AdvisorID:
                    student = Student(student_id, student_name, student_surname, student_birthdate)
                    advisor.add_student(student)
                    student.set_advisor(advisor)
                    break
            student = Student(student_id, student_name, student_surname, student_birthdate)
            self.admin.add_student(student)
            SQLiteManagement.add_student(student)
        elif choice == 2:
            logger.info(f"Choice 2:  Delete Student is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            student_id_to_be_deleted = input("Enter student ID to be deleted: ")
            self.admin.delete_student(student_id_to_be_deleted)
            SQLiteManagement.delete_student(student_id_to_be_deleted)
        elif choice == 3:
            logger.info(f"Choice 3:  Add Advisor is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            self.admin.add_advisor()
        elif choice == 4:
            logger.info(f"Choice 4:  Delete Advisor is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            self.admin.delete_advisor()
        elif choice == 5:
            logger.info(f"Choice 5:  Add Lecturer is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            self.admin.add_lecturer()
        elif choice == 6:
            logger.info(f"Choice 6:  Delete Lecturer is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            self.admin.delete_lecturer()
        elif choice == 7:
            logger.info(f"{self.admin.get_name()} {self.admin.get_surname()} succesfully logged out.")
            log_out = True
        else:
            logger.warning("Invalid choice")
            print("Enter 1, 2, 3, 4, 5, 6, or 7.")
        return log_out

    
    def get_choice(self):
        print("Select an operation:\n1. Add Student\n2. Delete Student\n3. Add Advisor\n4. Delete Advisor\n5. Add Lecturer\n6. Delete Lecturer\n7. Log out")
        try:
            choice = int(input("Enter your choice: "))
            return choice
        except ValueError:
            logger.warning(f"Invalid choice    - {self.admin.get_name()} {self.admin.get_surname()}")
            print("Enter an integer value.")
            return 0

    def student_to_add(self):
        student_id = input("Enter student ID: ")
        student_name = input("Enter student name: ")
        student_surname = input("Enter student surname: ")
        student_birthdate = input("Enter student birthdate: ")
        student_AdvisorID = input("Enter student advisorID: ")
        return student_id, student_name, student_surname, student_birthdate, student_AdvisorID