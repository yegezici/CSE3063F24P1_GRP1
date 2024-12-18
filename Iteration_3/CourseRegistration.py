import json
from typing import List, Optional, Union
from Iteration_3.AdvisorInterface import AdvisorInterface
from Iteration_3.DepartmentSchedulerInterface import DepartmentSchedulerInterface
from Iteration_3.StudentInterface import StudentInterface
from Student import Student
from Advisor import Advisor
from Lecturer import Lecturer
from StudentAffairStaff import StudentAffairsStaff
from DepartmentScheduler import DepartmentScheduler
from CourseSection import CourseSection
from Course import Course
from StudentAffairsStaffInterface import StudentAffairsStaffInterface
from JsonManagement import JsonManagement
from Logging_Config import logger

class CourseRegistration:

    def __init__(self,students,courses,course_sections,classrooms):
        # JSON dosyalarından verileri yükle
        self.students = students
        self.courses = courses
        self.course_sections = course_sections
        self.classrooms: List[str] = classrooms

    def init(self):
        logger.info("Course Registration system is started.")
        while True:
            current_user = self.login()
            if current_user is None:
                logger.warning("Invalid login attempt.")
                break
            if isinstance(current_user, Lecturer) and not isinstance(current_user, Advisor):
                break

            while True:
                if isinstance(current_user, Student):
                    logger.info(f"{current_user.get_name()} {current_user.get_surname()} with ID {current_user.get_id()} has succesfully logged in")
                    user_interface = StudentInterface(current_user, self.courses)
                elif isinstance(current_user, Advisor):
                    logger.info(
                        f"{current_user.get_name()} {current_user.get_surname()} with ID {current_user.get_id()} has succesfully logged in.")
                    user_interface = AdvisorInterface(current_user)
                elif isinstance(current_user, StudentAffairsStaff):
                    user_interface = StudentAffairsStaffInterface(current_user, self.courses, self.course_sections)
                elif isinstance(current_user, DepartmentScheduler):
                    user_interface = DepartmentSchedulerInterface(current_user, self.course_sections)

                if user_interface.show_menu():
                    self.save_students()
                    break

    def save_students(self):
        for student in self.students:
            JsonManagement.get_instance().save_student(student)

    def login(self) -> Optional[Union[Student, Advisor, StudentAffairsStaff, DepartmentScheduler]]:

        print("Welcome!\n1- Login\nPress any other key to exit")
        if input() == "1":
            entered_user_id = input("User ID: ")
            entered_password = input("Password: ")
            return self.check_id_and_password(entered_user_id, entered_password)
        else:
            print("Program has been terminated successfully.")
            return None

    def check_id_and_password(self, entered_user_id: str, entered_password: str) -> Optional[Union[Student]]:
        if not entered_user_id or not entered_password:
            print("Please enter user id and password.")
            logger.info("Invalid login attempt")
            return None

        # Öğrenci ID'sini kontrol et
        for student in self.students:
            if student.get_id() == entered_user_id[1:] and entered_password == "abc123":
                logger.info("Succesfully logged in")
                return student
            elif student.get_id() == entered_user_id:
                logger.warning("Wrong password")
                print("Wrong password.")
                return None

        print("Wrong User ID or Password")
        logger.warning("Wrong user id or password")
        return None

    def check_id_and_password_of_person(self, user_id: str, password: str, file_path: str, person_type: str) -> bool:
        try:
            with open(file_path, "r") as file:
                data = json.load(file)
                for person in data.get(person_type, []):
                    if person["userID"] == "150121031" and person["password"] == "abc123":
                        return True
                    elif person["userID"] == user_id:
                        print("Wrong password")
                        return False
        except Exception as e:
            print("An error occurred while checking credentials:", e)
        return False
