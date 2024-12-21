import sys
from typing import List
from CourseSection import CourseSection
from Course import Course
from UserInterface import UserInterface
from DepartmentHead import DepartmentHead
from Lecturer import Lecturer
class DepartmentHeadInterface(UserInterface):
    def __init__(self, department_head: 'DepartmentHead', course_sections: 'List[CourseSection]', lecturers: 'List[Lecturer]'):
        self.department_head = department_head
        self.course_sections = course_sections
        self.lecturers = lecturers

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
                print(f"Current capacity is {chosen_section.get_capacity()}")
                print("Enter new capacity:")
                new_capacity = int(input())
                self.department_head.manage_capacity(chosen_section, new_capacity)
            elif choice == 3:
                print("You have successfully logged out\n")
                return True
            else:
                print("Enter 1 or 2.")

    def get_choice(self):
        print("1-  Add Course\n2-  Manage Capacity\nSelect an operation: ", end="")
        try:
            choice = int(input())
        except ValueError:
            print("Enter an integer value.")
            choice = -1
        return choice

    def add_course(self):
        course_params = self.ask_course_parameters()
        try:
            new_course = self.department_head.create_course(
                course_params[0], course_params[1], course_params[3], int(course_params[2]), int(course_params[4])
            )
            self.courses.append(new_course)
            self.course_sections.extend(new_course.get_course_sections())
            # JsonManagement.getInstance().write_course_to_json()  # Uncomment when implementing JSON handling
        except ValueError:
            print("Enter an integer value for course code and course credits.")

    def ask_course_parameters(self):
        course = [None] * 5
        try:
            course[0] = input("Enter course name: ")
            course[1] = input("Enter course code: ")
            course[2] = input("Enter course credits: ")
            course[3] = input("Enter course type (Mandatory (m) / Technical Elective (te) / Non-Technical Elective (nte)): ").lower()

            if course[3] not in ["m", "te", "nte"]:
                raise ValueError("Invalid choice!")

            course[4] = input("How many sections does the course have?: ")

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
            print(f"{idx}- {course.get_course_id()}.{section.get_section_id()}")
