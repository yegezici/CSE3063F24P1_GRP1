import sys
from typing import List
from CourseSection import CourseSection
from Course import Course
from UserInterface import UserInterface
from DepartmentHead import DepartmentHead
from Lecturer import Lecturer
from NotificationSystem import NotificationSystem
class DepartmentHeadInterface(UserInterface):
    def __init__(self, department_head: DepartmentHead, course_sections: 'List[CourseSection]'):
        self.department_head = department_head
        self.course_sections = department_head.get_manager().get_course_sections()
        self.lecturers = department_head.get_manager().get_lecturers()
        self.__notification_system = department_head.get_manager().get_notification_system()
        
    def set_lecturers(self, lecturers: list[Lecturer]) ->None:
        self.lecturers = lecturers
    def set_notification_system(self, notification_system: NotificationSystem):
        self.__notification_system = notification_system
        
    def show_menu(self):
        while True:
            choice = self.get_choice()
            if choice == 1:
                self.add_course()
            elif choice == 2:
                self.show_available_course_sections()
                print("Choose a course section:")
                section_no = int(input()) - 1
                chosen_section = self.course_sections[section_no]
                done = False
                while(done is False):
                    print(f"Current capacity is {chosen_section.get_capacity()}")
                    print("Enter new capacity:")
                    new_capacity = int(input())
                    done = self.department_head.manage_capacity(chosen_section, new_capacity, self.__notification_system)
            elif choice == 3:
                print("You have successfully logged out\n")
                return True
            else:
                print("Enter 1 or 2.")

    def get_choice(self):
        print("1-  Add Course\n2-  Manage Capacity\n3-  Log Out\nSelect an operation: ", end="")
        try:
            choice = int(input())
        except ValueError:
            print("Enter an integer value.")
            choice = -1
        return choice

    def add_course(self):
        course_params = self.ask_course_parameters()
        try:
            from DepartmentScheduler import DepartmentScheduler
            new_course = self.department_head.create_course(
                course_params[0], course_params[1], course_params[3], int(course_params[2]), int(course_params[4]), int(course_params[5]),
                int(course_params[6]),course_params[7])
            for section in self.course_sections:
                for i in range(len(new_course.get_course_sections())):
                    if(section.get_section_id() == new_course.get_course_sections()[i].get_section_id()):
                        print("Course already exists.")
                        return
            print(f"{new_course.get_course_id()} - {new_course.get_course_name()} - {new_course.get_credits()} - {new_course.get_course_type()} - This course has been added.")
            self.course_sections.extend(new_course.get_course_sections())
            department_scheduler = None
            for lecturer in self.lecturers:
                if isinstance(lecturer, DepartmentScheduler):
                    department_scheduler = lecturer
            self.__notification_system.create_notification(self.department_head, department_scheduler, f"Course with ID: {new_course.get_course_id()} has been added and is waiting to get scheduled.")
        except ValueError:
            print("Enter an integer value for course code and course credits.")

    def ask_course_parameters(self):
        course = [None] * 8
        try:
            course[0] = input("Enter course name: ")
            course[1] = input("Enter course code: ")
            course[2] = input("Enter course credits: ")
            course[3] = input("Enter course type (Mandatory (m) / Technical Elective (te) / Non-Technical Elective (nte)): ").lower()

            if course[3] not in ["m", "te", "nte"]:
                raise ValueError("Invalid choice!")
            if course [3]  != "nte":
                course[6] = int(input("Enter the semester of cours: "))
                course[7] = input("Enter the prerequisite course code: ")
            else:
                course[6] = 0
            course[4] = input("How many sections does the course have?: ")
            course[5] = input("Enter section capacity: ")
            
        except ValueError as e:
            print(f"{e} Please enter a valid choice!")
        return course

    def remove_course(self):
        self.print_courses()
        print("Which course do you want to remove?")
        try:
            course_index = int(input()) - 1
            removed_course = self.courses.pop(course_index)
            print(f"The {removed_course.get_course_id()} course has been removed.")
        except ValueError:
            print("Enter an integer value.")
        except IndexError:
            print("Invalid course selection.")

    def print_courses(self):
        for i, course in enumerate(self.courses, 1):
            print(f"{i}- {course}")

    def show_available_course_sections(self):
        print("All available course sections are listed below:")
        for idx, section in enumerate(self.course_sections, 1):
            course = section.get_parent_course()
            print(f"{idx}- {section.get_section_id()}")


    def set_interface(self, interface):
        self.__interface = interface
    
    def initialize_interface(self):
        return self.__interface