import json
from typing import List, Optional, Union
from AdvisorInterface import AdvisorInterface
from DepartmentSchedulerInterface import DepartmentSchedulerInterface
from StudentInterface import StudentInterface
from Student import Student
from Advisor import Advisor
from Lecturer import Lecturer
from StudentAffairStaff import StudentAffairsStaff
from DepartmentScheduler import DepartmentScheduler
from CourseSection import CourseSection
from Course import Course
from StudentAffairsStaffInterface import StudentAffairsStaffInterface
from NotificationSystem import NotificationSystem

from SQLiteManagement import SQLiteManagement

from Logging_Config import logger


class CourseRegistration:


    def __init__(self):
        manager = SQLiteManagement()
        self.students = manager.get_students()
        self.courses = manager.get_courses()
        self.course_sections = manager.get_course_sections()
        self.advisors = manager.get_advisors()
        


    def init(self):
        logger.info("Course Registration system is started.")

        while True:
            current_user = self.login()
            if current_user is None:
                logger.warning("Invalid login attempt.")
                continue

            if isinstance(current_user, Lecturer) and not isinstance(current_user, Advisor):
                break
            logger.info(f"{current_user.get_name()} {current_user.get_surname()} with ID {current_user.get_id()} has succesfully logged in")
            while True:
                if isinstance(current_user, Student):
                    NotificationSystem.print_user_notifications(user=current_user)
                    user_interface = StudentInterface(current_user, self.courses)
                elif isinstance(current_user, Advisor):
                    NotificationSystem.print_user_notifications(user=current_user)
                    user_interface = AdvisorInterface(current_user)
                elif isinstance(current_user, StudentAffairsStaff):
                    NotificationSystem.print_user_notifications(user=current_user)
                    user_interface = StudentAffairsStaffInterface(current_user, self.courses, self.course_sections)
                elif isinstance(current_user, DepartmentScheduler):
                    NotificationSystem.print_user_notifications(user=current_user)
                    user_interface = DepartmentSchedulerInterface(current_user, self.course_sections)

                if user_interface.show_menu():
                    break

    def login(self) -> Optional[Union[Student, Advisor, StudentAffairsStaff, DepartmentScheduler]]:

        print("Welcome!\n1- Login\nPress any other key to exit")
        if input() == "1":
            entered_user_id = input("User ID: ")
            entered_password = input("Password: ")
            return self.check_id_and_password(entered_user_id, entered_password)
        else:
            logger.info("Program has been terminated succesfully.")
            return None

    def check_id_and_password(self, entered_user_id: str, entered_password: str) -> Optional[Union[Student]]:
        if not entered_user_id or not entered_password:
            print("Please enter user id and password.")
            logger.info("Invalid login attempt")
            return None

        for advisor in self.advisors:

            if advisor.get_ssn() == entered_user_id[1:] and entered_password == "ganiz123":

                logger.info("Advisor logged in")
                return advisor

        # Öğrenci ID'sini kontrol et
        for student in self.students:
            if student.get_id() == entered_user_id[1:] and entered_password == "abc123":
                logger.info("Succesfully logged in")
                return student
            elif student.get_id() == entered_user_id:
                logger.warning("Wrong password")
                print("Wrong password.")
                return None

        logger.warning("Wrong user id or password")
        return None

    def check_id_and_password_of_person(self, user_id: str, password: str, file_path: str, person_type: str) -> bool:
        try:
            with open(file_path, "r") as file:
                data = json.load(file)
                for person in data.get(person_type, []):
                    if person["userID"] == "150121031" and person["password"] == "abc123":
                        logger.info("Succefully logged in.")
                        return True
                    elif person["userID"] == user_id:
                        logger.warning("Wrong Password")
                        return False
        except Exception as e:
            logger.warning("An error occurred while checking credentials:", e)
        return False
