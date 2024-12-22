from Admin import Admin
from typing import List
from SQLiteManagement import SQLiteManagement
from Logging_Config import logger

class AdminInterface:
    def __init__(self, admin: Admin, course_sections: List['CourseSection']):
        self.admin = admin
        self.course_sections = course_sections
        
    
    def show_menu(self):
        log_out = False
        choice = self.get_choice()
        if choice == 1:
            logger.info(f"Choice 1:  Add Student is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            new_student = self.student_to_add()
            if self.admin.add_student(new_student):
                print(f"Student {new_student.get_name()} {new_student.get_surname()} added.")
            #SQLiteManagement.add_student(student)
        elif choice == 2:
            logger.info(f"Choice 2:  Delete Student is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            student_id_to_be_deleted = input("Enter student ID to be deleted: ")
            self.admin.delete_student(student_id_to_be_deleted)
            #SQLiteManagement.delete_student(student_id_to_be_deleted)
        elif choice == 3:
            logger.info(f"Choice 3:  Add Advisor is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            advisor = self.advisor_to_add()
            self.admin.add_advisor(advisor)
            #SQLiteManagement.add_advisor(advisor)
        elif choice == 4:
            logger.info(f"Choice 4:  Delete Advisor is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            advisor_id_to_be_deleted = input("Enter student ID to be deleted: ")
            self.admin.delete_advisor(advisor_id_to_be_deleted)
            #SQLiteManagement.delete_student(advisor_id_to_be_deleted)
        elif choice == 5:
            logger.info(f"Choice 5:  Add Lecturer is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            lecturer = self.lecturer_to_add()
            self.admin.add_lecturer(lecturer)
        elif choice == 6:
            logger.info(f"Choice 6:  Delete Lecturer is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            lecturer_id_to_be_deleted = input("Enter lecturer ID to be deleted: ")
            self.admin.delete_lecturer(lecturer_id_to_be_deleted)
        elif choice == 7:
            logger.info(f"Choice 7: Add Department Scheduler is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            scheduler = self.scheduler_to_add()
            self.admin.add_department_scheduler(scheduler)
        elif choice == 8:
            logger.info(f"Choice 8: Delete Department Scheduler is selected     - {self.admin.get_name()} {self.admin.get_surname()}")
            scheduler_id_to_be_deleted = input("Enter scheduler ID to be deleted: ")
            self.admin.delete_department_scheduler(scheduler_id_to_be_deleted)
        elif choice == 9:
            logger.info(f"{self.admin.get_name()} {self.admin.get_surname()} succesfully logged out.")
            log_out = True
        else:
            logger.warning("Invalid choice")
            print("Enter 1, 2, 3, 4, 5, 6, or 7.")
        return log_out

    
    def get_choice(self):
        print(f"Students in admin: {self.admin.get_students()}")
        print("Select an operation:\n1. Add Student\n2. Delete Student\n3. Add Advisor\n4. Delete Advisor\n5. Add Lecturer\n6. Delete Lecturer\n7. Add Department Scheduler\n8. Delete Department Scheduler\n9. Log out")
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
        student_gender = input("Enter student gender:")
        student_birthdate = input("Enter student birthdate: ")
        student_advisorID = input("Enter student advisorID: ")
        from Student import Student
        from Transcript import Transcript
        transcript = Transcript()
        student = Student(name=student_name, surname=student_surname, birthdate=student_birthdate, gender=student_gender, transcript=transcript, student_id=student_id)        
        for advisor in self.admin.get_advisors():
                if advisor.get_id() == student_advisorID:
                    student.set_advisor(advisor)
                    print(f"Advisor's student list before adding: {[s.get_name() for s in advisor.get_students()]}")
                    advisor.add_student(student)
                    print(f"Advisor's student list after adding: {[s.get_name() for s in advisor.get_students()]}")
                    break
       
        return student

    def advisor_to_add(self):
        lecturer_id = input("Enter lecturer ID to be promoted to Advisor: ")
        lecturer = self.find_lecturer_by_id(lecturer_id)
        if lecturer:
            from Advisor import Advisor
            advisor = Advisor(ssn=lecturer.get_id(),name=lecturer.get_name(), surname=lecturer.get_surname(), birthdate=lecturer.get_birthdate(), gender=lecturer.get_gender(), courses=None, students=None)
            return advisor
        else:
            logger.warning("Lecturer ID not found.")
            return None
    

    def find_lecturer_by_id(self, lecturer_id: str):
        for lecturer in self.admin.get_lecturers():
            if lecturer.get_id() == lecturer_id:
                return lecturer
        return None

    def lecturer_to_add(self):
        lecturer_id = input("Enter lecturer ID: ")
        lecturer_name = input("Enter lecturer name: ")
        lecturer_surname = input("Enter lecturer surname: ")
        lecturer_gender = input("Enter lecturer gender: ")
        lecturer_birthdate = input("Enter lecturer birthdate: ")
        from Lecturer import Lecturer
        lecturer = Lecturer(id=lecturer_id, name=lecturer_name, surname=lecturer_surname,gender=lecturer_gender,courses=None,birthdate=lecturer_birthdate)
        return lecturer


    def scheduler_to_add(self):
        lecturer_id = input("Enter scheduler ID to be promoted to Department Scheduler: ")
        lecturer = self.find_lecturer_by_id(lecturer_id)
        if lecturer:
            from DepartmentScheduler import DepartmentScheduler
            scheduler = DepartmentScheduler(id=lecturer.get_id(), name=lecturer.get_name(), surname=lecturer.get_surname(), birthdate=lecturer.get_birthdate, gender=lecturer.get_gender() ,course_sections=self.course_sections, all_time_intervals=None,)
        return scheduler
