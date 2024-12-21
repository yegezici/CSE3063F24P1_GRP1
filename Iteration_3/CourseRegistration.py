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
from Person import Person

class CourseRegistration:


    def __init__(self):
        self.manager = SQLiteManagement()
        self.students = self.manager.get_students()
        self.courses = self.manager.get_courses()
        self.course_sections = self.manager.get_course_sections()
        self.advisors = self.manager.get_advisors()
        


    def init(self):
        logger.info("Course Registration system is started.")

        while True:
            current_user = self.login()
            if current_user is None:
                continue

            if isinstance(current_user, Lecturer) and not isinstance(current_user, Advisor):
                break
            #logger.info(f"{current_user.get_name()} {current_user.get_surname()} with ID {current_user.get_id()} has succesfully logged in")
            while True:
                if isinstance(current_user, Student):
                    #NotificationSystem.print_user_notifications(current_user)
                    user_interface = StudentInterface(current_user, self.courses)
                elif isinstance(current_user, Advisor):
                    #NotificationSystem.print_user_notifications(user=current_user)
                    user_interface = AdvisorInterface(current_user)
                elif isinstance(current_user, StudentAffairsStaff):
                    #NotificationSystem.print_user_notifications(user=current_user)
                    user_interface = StudentAffairsStaffInterface(current_user, self.courses, self.course_sections)
                elif isinstance(current_user, DepartmentScheduler):
                    #NotificationSystem.print_user_notifications(user=current_user)
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

    def check_id_and_password(self, entered_user_id: str, entered_password: str) -> Person:
        print(entered_user_id, entered_password)
        return self.manager.check_user(entered_user_id, entered_password)
