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
from NotificationSystem import NotificationSystem
from DepartmentHead import DepartmentHead
from SQLiteManagement import SQLiteManagement
from DepartmentHeadInterface import DepartmentHeadInterface
from Logging_Config import logger
from Person import Person

class CourseRegistration:


    def __init__(self):
        self.manager = SQLiteManagement()
        self.students = self.manager.get_students()
        self.courses = self.manager.get_courses()
        self.course_sections = self.manager.get_course_sections()
        self.advisors = self.manager.get_advisors()

    # Initializes the course registration system.
    def init(self):
        logger.info("Course Registration system is started.")
        notification_system = self.manager.get_notification_system()  
        while True:
            current_user = self.login()
            if current_user is None:
                continue

            if current_user == "exit":
                logger.info("Program has been terminated succesfully.")
                break
            logger.info(f"{current_user.get_name()} {current_user.get_surname()} with ID {current_user.get_id()} has succesfully logged in")
            while True:
                user_interface = current_user.initialize_interface()
                
                print(f"Welcome {current_user.get_name()} {current_user.get_surname()}\n")
                notification_system.print_user_notifications(user = current_user)
                if user_interface.show_menu():
                    self.manager.save_all_notifications()
                    self.manager.save_all_students()
                    break


    # Asks the user to enter their user id and password.
    def login(self) -> Optional[Union[Student, Advisor, StudentAffairsStaff, DepartmentScheduler]]:

        print("Welcome!\n1- Login\nPress any other key to exit")
        if input() == "1":
            entered_user_id = input("User ID: ")
            entered_password = input("Password: ")
            return self.check_id_and_password(entered_user_id, entered_password)
        else:
            return "exit"

    # Checks if a user exists with the given user id and password.
    def check_id_and_password(self, entered_user_id: str, entered_password: str) -> Person:
        return self.manager.check_user(entered_user_id, entered_password)
