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


class CourseRegistration:
    def __init__(self):
        # JSON dosyalarından verileri yükle
        self.students: List[Student] = JsonManagement.get_instance().get_students()
        self.courses: List[Course] = JsonManagement.get_instance().get_courses()
        self.course_sections: List[CourseSection] = JsonManagement.get_instance().get_course_sections()
        self.classrooms: List[str] = JsonManagement.get_instance().get_classrooms()

    def init(self):
        while True:
            current_user = self.login()
            if current_user is None:
                continue

            if isinstance(current_user, Lecturer) and not isinstance(current_user, Advisor):
                break

            while True:
                if isinstance(current_user, Student):
                    user_interface = StudentInterface(current_user, self.courses)
                elif isinstance(current_user, Advisor):
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

    def check_id_and_password(self, entered_user_id: str, entered_password: str) -> Optional[
        Union[Student, Advisor, StudentAffairsStaff, DepartmentScheduler]]:
        if not entered_user_id or not entered_password:
            print("Please enter user id and password.")
            return None

        user_prefix = entered_user_id[0]
        valid_prefixes = {'o': 'students', 'l': 'advisors', 'a': 'studentAffairsStaffs', 'd': 'departmentSchedulers'}

        if user_prefix not in valid_prefixes:
            print("Invalid prefix. Use 'o' for student, 'l' for lecturer, 'a' for affairs staff, 'd' for scheduler.")
            return None

        person_type = valid_prefixes[user_prefix]
        user_id = entered_user_id[1:]

        file_path = "parameters.json"
        if self.check_id_and_password_of_person(user_id, entered_password, file_path, person_type):
            return JsonManagement.get_instance().get_person_by_id(user_id, person_type)

        print("Wrong User ID or Password")
        return None

    def check_id_and_password_of_person(self, user_id: str, password: str, file_path: str, person_type: str) -> bool:
        try:
            with open(file_path, "r") as file:
                data = json.load(file)
                for person in data.get(person_type, []):
                    if person["userID"] == user_id and person["password"] == password:
                        return True
                    elif person["userID"] == user_id:
                        print("Wrong password")
                        return False
        except Exception as e:
            print("An error occurred while checking credentials:", e)
        return False


# Singleton JSON Management Class (Simplified)
class JsonManagement:
    _instance = None

    @staticmethod
    def get_instance():
        if JsonManagement._instance is None:
            JsonManagement()
        return JsonManagement._instance

    def __init__(self):
        if JsonManagement._instance is not None:
            raise Exception("This class is a singleton!")
        else:
            JsonManagement._instance = self

    def get_students(self) -> List[Student]:
        return self._load_json_data("students")

    def get_courses(self) -> List[Course]:
        return self._load_json_data("courses")

    def get_course_sections(self) -> List[CourseSection]:
        return self._load_json_data("courseSections")

    def get_classrooms(self) -> List[str]:
        return self._load_json_data("classrooms")

    def get_person_by_id(self, user_id: str, person_type: str) -> Optional[
        Union[Student, Advisor, StudentAffairsStaff, DepartmentScheduler]]:
        # Return specific user object by ID
        return None

    def save_student(self, student: Student):
        print(f"Saving student {student}")

    def _load_json_data(self, key: str):
        try:
            with open("parameters.json", "r") as file:
                data = json.load(file)
                return data.get(key, [])
        except FileNotFoundError:
            print(f"{key} data file not found.")
            return []
