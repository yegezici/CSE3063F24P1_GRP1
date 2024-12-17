import sys
from typing import List
from CourseSection import CourseSection
from Course import Course

class StudentAffairsStaffInterface:
    def __init__(self, staff, courses: List['Course'], course_sections: List['CourseSection']):
        self.staff = staff
        self.courses = courses
        self.course_sections = course_sections

    def show_menu(self):
        while True:
            choice = self.get_choice()
            if choice == 1:
                self.add_course()
            elif choice == 2:
                print("You have successfully logged out\n")
                return True
            else:
                print("Enter 1 or 2.")

    def get_choice(self):
        print("1-  Add Course\n2-  Log Out\nSelect an operation: ", end="")
        try:
            choice = int(input())
        except ValueError:
            print("Enter an integer value.")
            choice = -1
        return choice

    def add_course(self):
        course_params = self.ask_course_parameters()
        try:
            new_course = self.staff.create_course(
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
