from Admin import Admin
from typing import List, Optional, Union
from SQLiteManagement import SQLiteManagement
from Logging_Config import logger

class AdminInterface:
    def __init__(self, admin: Admin, course_sections: List['CourseSection']):
        self.admin: Admin = admin
        self.course_sections: List['CourseSection'] = course_sections
        self.manager: SQLiteManagement = SQLiteManagement()
    
    def show_menu(self) -> bool:
        log_out: bool = False
        choice: int = self.get_choice()
        if choice == 1:
            logger.info(f"Choice 1: Add Student is selected - {self.admin.get_name()} {self.admin.get_surname()}")
            password: str = input("Enter new password: ")
            new_student: Optional['Student'] = self.student_to_add()
            if new_student is None:
                print("No matching advisor found.")
            else:
                print(f"Student successfully added. {new_student.get_name()} {new_student.get_surname()} with ID {new_student.get_id()}")
                self.admin.add_student(new_student)
                self.manager.add_student(new_student, password)
        elif choice == 2:
            logger.info(f"Choice 2: Delete Student is selected - {self.admin.get_name()} {self.admin.get_surname()}")
            student_id_to_be_deleted: str = input("Enter student ID to be deleted: ")
            try:
                self.admin.delete_student(student_id_to_be_deleted)
                self.manager.delete_student(student_id_to_be_deleted)
                print(f"Student with ID {student_id_to_be_deleted} successfully deleted.")
            except KeyError:
                logger.error(f"Student ID {student_id_to_be_deleted} not found in records.")
                print("Error: Student ID not found.")
            except Exception as e:
                logger.error(f"An unexpected error occurred while deleting the student: {str(e)}")
                print("An unexpected error occurred. Please try again.")
        elif choice == 3:
            logger.info(f"Choice 3: Add Advisor is selected - {self.admin.get_name()} {self.admin.get_surname()}")
            advisor: Optional['Advisor'] = self.advisor_to_add()
            try:
                if advisor:
                    password = input("Enter new password for the advisor: ")
                    self.admin.add_advisor(advisor)
                    self.manager.add_advisor(advisor, password)
                    print(f"Advisor {advisor.get_name()} {advisor.get_surname()} successfully added.")
                else:
                    print("Error: Could not add advisor. Please check the details.")
            except Exception as e:
                logger.error(f"An unexpected error occurred while adding the advisor: {str(e)}")
                print("An unexpected error occurred. Please try again.")
        elif choice == 4:
            logger.info(f"Choice 4: Delete Advisor is selected - {self.admin.get_name()} {self.admin.get_surname()}")
            advisor_id_to_be_deleted: str = input("Enter advisor ID to be deleted: ")
            try:
                self.admin.delete_advisor(advisor_id_to_be_deleted)
                self.manager.delete_advisor(advisor_id_to_be_deleted)
                print(f"Advisor with ID {advisor_id_to_be_deleted} successfully deleted.")
            except KeyError:
                logger.error(f"Advisor ID {advisor_id_to_be_deleted} not found.")
                print("Error: Advisor ID not found.")
            except Exception as e:
                logger.error(f"An unexpected error occurred while deleting the advisor: {str(e)}")
                print("An unexpected error occurred. Please try again.")
        elif choice == 5:
            logger.info(f"Choice 5: Add Lecturer is selected - {self.admin.get_name()} {self.admin.get_surname()}")
            lecturer: Optional['Lecturer'] = self.lecturer_to_add()
            try:
                if lecturer:
                    password = input("Enter new password for the lecturer: ")
                    self.admin.add_lecturer(lecturer)
                    self.manager.add_lecturer(lecturer, password)
                    print(f"Lecturer {lecturer.get_name()} {lecturer.get_surname()} successfully added.")
                else:
                    print("Error: Could not add lecturer. Please check the details.")
            except Exception as e:
                logger.error(f"An unexpected error occurred while adding the lecturer: {str(e)}")
                print("An unexpected error occurred. Please try again.")
        elif choice == 6:
            logger.info(f"Choice 6: Delete Lecturer is selected - {self.admin.get_name()} {self.admin.get_surname()}")
            lecturer_id_to_be_deleted: str = input("Enter lecturer ID to be deleted: ")
            try:
                self.admin.delete_lecturer(lecturer_id_to_be_deleted)
                self.manager.delete_lecturer(lecturer_id_to_be_deleted)
                print(f"Lecturer with ID {lecturer_id_to_be_deleted} successfully deleted.")
            except KeyError:
                logger.error(f"Lecturer ID {lecturer_id_to_be_deleted} not found.")
                print("Error: Lecturer ID not found.")
            except Exception as e:
                logger.error(f"An unexpected error occurred while deleting the lecturer: {str(e)}")
                print("An unexpected error occurred. Please try again.")
        elif choice == 7:
            logger.info(f"Choice 7: Add Department Scheduler is selected - {self.admin.get_name()} {self.admin.get_surname()}")
            scheduler: Optional['DepartmentScheduler'] = self.scheduler_to_add()
            try:
                if scheduler:
                    password = input("Enter new password for the department scheduler: ")
                    self.admin.add_department_scheduler(scheduler)
                    self.manager.add_department_scheduler(scheduler, password)
                    print(f"Department Scheduler {scheduler.get_name()} {scheduler.get_surname()} successfully added.")
                else:
                    print("Error: Could not add department scheduler. Please check the details.")
            except Exception as e:
                logger.error(f"An unexpected error occurred while adding the department scheduler: {str(e)}")
                print("An unexpected error occurred. Please try again.")
        elif choice == 8:
            logger.info(f"Choice 8: Delete Department Scheduler is selected - {self.admin.get_name()} {self.admin.get_surname()}")
            scheduler_id_to_be_deleted: str = input("Enter scheduler ID to be deleted: ")
            try:
                self.admin.delete_department_scheduler(scheduler_id_to_be_deleted)
                self.manager.delete_department_scheduler(scheduler_id_to_be_deleted)
                print(f"Department Scheduler with ID {scheduler_id_to_be_deleted} successfully deleted.")
            except KeyError:
                logger.error(f"Scheduler ID {scheduler_id_to_be_deleted} not found.")
                print("Error: Scheduler ID not found.")
            except Exception as e:
                logger.error(f"An unexpected error occurred while deleting the department scheduler: {str(e)}")
                print("An unexpected error occurred. Please try again.")
        elif choice == 9:
            logger.info(f"{self.admin.get_name()} {self.admin.get_surname()} successfully logged out.")
            log_out = True
        else:
            logger.warning("Invalid choice")
            print("Enter a valid choice (1, 2, 3, 4, 5, 6, 7, or 8).")
        return log_out
    
    def get_choice(self) -> int:
        print("Select an operation:\n1. Add Student\n2. Delete Student\n3. Add Advisor\n4. Delete Advisor\n5. Add Lecturer\n6. Delete Lecturer\n7. Add Department Scheduler\n8. Delete Department Scheduler\n9. Log out")
        try:
            choice: int = int(input("Enter your choice: "))
            return choice
        except ValueError:
            logger.warning(f"Invalid choice - {self.admin.get_name()} {self.admin.get_surname()}")
            print("Enter an integer value.")
            return 0

    def student_to_add(self) -> Optional['Student']:
        student_id: str = input("Enter student ID: ")
        student_name: str = input("Enter student name: ")
        student_surname: str = input("Enter student surname: ")
        student_gender: str = input("Enter student gender: ")
        student_birthdate: str = input("Enter student birthdate: ")
        student_advisorID: str = input("Enter student advisorID: ")
        from Student import Student
        student: Optional['Student'] = None
        for advisor in self.admin.get_advisors():
            if advisor.get_id() == student_advisorID:
                from Transcript import Transcript
                transcript: 'Transcript' = Transcript()
                student = Student(name=student_name, surname=student_surname, birthdate=student_birthdate, gender=student_gender, transcript=transcript, student_id=student_id)
                advisor.add_student(student)
                student.set_advisor(advisor)
                break
        return student

    def advisor_to_add(self) -> Optional['Advisor']:
        lecturer_id: str = input("Enter lecturer ID to be promoted to Advisor: ")
        lecturer = self.find_lecturer_by_id(lecturer_id)
        if lecturer:
            from Advisor import Advisor
            advisor: 'Advisor' = Advisor(ssn=lecturer.get_id(), name=lecturer.get_name(), surname=lecturer.get_surname(), birthdate=lecturer.get_birthdate(), gender=lecturer.get_gender(), courses=None, students=None)
            return advisor
        logger.warning("Lecturer ID not found.")
        return None

    def find_lecturer_by_id(self, lecturer_id: str) -> Optional['Lecturer']:
        print(len(self.admin.get_lecturers()))
        for lecturer in self.admin.get_lecturers():
            print(lecturer.get_id())
            if lecturer.get_id() == lecturer_id:
                return lecturer
        return None

    def lecturer_to_add(self) -> 'Lecturer':
        lecturer_id: str = input("Enter lecturer ID: ")
        lecturer_name: str = input("Enter lecturer name: ")
        lecturer_surname: str = input("Enter lecturer surname: ")
        lecturer_gender: str = input("Enter lecturer gender: ")
        lecturer_birthdate: str = input("Enter lecturer birthdate: ")
        from Lecturer import Lecturer
        lecturer: 'Lecturer' = Lecturer(ssn=lecturer_id, name=lecturer_name, surname=lecturer_surname, gender=lecturer_gender, courses=None, birthdate=lecturer_birthdate)
        return lecturer

    def scheduler_to_add(self) -> Optional['DepartmentScheduler']:
        lecturer_id: str = input("Enter scheduler ID to be promoted to Department Scheduler: ")
        lecturer: Optional['Lecturer'] = self.find_lecturer_by_id(lecturer_id)
        if lecturer:
            from DepartmentScheduler import DepartmentScheduler
            scheduler: 'DepartmentScheduler' = DepartmentScheduler(id=lecturer.get_id(), name=lecturer.get_name(), surname=lecturer.get_surname(), birthdate=lecturer.get_birthdate(), gender=lecturer.get_gender(), course_sections=self.course_sections, all_time_intervals=None)
            return scheduler
        logger.warning("Lecturer ID not found.")
        return None
