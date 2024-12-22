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
        self.notificationSystem = NotificationSystem()

    def init(self):
        logger.info("Course Registration system is started.")
        notificationSystem = NotificationSystem()  
        while True:
            current_user = self.login()
            if current_user is None:
                continue

            #logger.info(f"{current_user.get_name()} {current_user.get_surname()} with ID {current_user.get_id()} has succesfully logged in")
            while True:
                user_interface = current_user.initialize_interface()
                """
                if isinstance(current_user, Student):     
                    user_interface = StudentInterface(current_user, self.courses, self.notificationSystem)
                elif isinstance(current_user, Advisor):
                    notificationSystem.print_user_notifications(user = current_user)
                    user_interface = AdvisorInterface(current_user)
                    user_interface = AdvisorInterface(current_user, self.notificationSystem)
                elif isinstance(current_user, StudentAffairsStaff):
                    notificationSystem.print_user_notifications(user = current_user)
                    user_interface = StudentAffairsStaffInterface(current_user, self.courses, self.course_sections, self.notificationSystem)
                elif isinstance(current_user, DepartmentScheduler):
                    notificationSystem.print_user_notifications(user = current_user)
                    user_interface = DepartmentSchedulerInterface(current_user, self.course_sections, self.notificationSystem)
                elif isinstance(current_user, DepartmentHead):
                    notificationSystem.print_user_notifications(user = current_user)
                    user_interface = DepartmentHeadInterface(current_user, self.course_sections, self.courses)
                """
                if user_interface.show_menu():
#-------------------------------BU KISIMDA NOTIFICATIONLARIN KAYDEDILMESI GEREKMEKTEDIR---------------------------------
                    #self.manager.save_all_notifications()
                    self.manager.save_all_students()
                    break



    def login(self) -> Optional[Union[Student, Advisor, StudentAffairsStaff, DepartmentScheduler]]:

        print("Welcome!\n1- Login\nPress any other key to exit")
        if input() == "1":
            entered_user_id = input("User ID: ")
            entered_password = input("Password: ")
            return self.check_id_and_password(entered_user_id[1:], entered_password)
        else:
            logger.info("Program has been terminated succesfully.")
            return None

    def check_id_and_password(self, entered_user_id: str, entered_password: str) -> Person:
        return self.manager.check_user(entered_user_id, entered_password)
