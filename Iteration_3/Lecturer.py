from datetime import date
from typing import List, TYPE_CHECKING
from Staff import Staff

if TYPE_CHECKING:
    from Course import Course

class Lecturer(Staff):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None, courses: List['Course'] = None):
        super().__init__(name, surname, birthdate, gender, ssn)
        self.__courses = courses if courses else []

    def get_courses(self):
        return self.__courses

    def set_courses(self, courses: List['Course']):
        self.__courses = courses


    def get_id(self):
        return super().get_id()
      
    def get_name(self):
        return super().get_name()

    def get_surname(self):
        return super().get_surname()

    def get_birthdate(self):
        return super().get_birthdate()

    def get_gender(self):
        return super().get_gender()

