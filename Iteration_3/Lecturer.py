from datetime import date
from typing import List, TYPE_CHECKING
from Staff import Staff

if TYPE_CHECKING:
    from Course import Course

class Lecturer(Staff):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, id: str = None, courses: List['Course'] = None):
        self.__name = name
        self.__surname = surname
        self.__birthdate = birthdate
        self.__gender = gender
        self.__id = id
        self.__courses = courses if courses else []

    def get_courses(self):
        return self.__courses

    def set_courses(self, courses: List['Course']):
        self.__courses = courses

    def get_id(self):
        return self.__id

    def get_name(self):
        return self.__name

    def get_surname(self):
        return self.__surname

    def get_birthdate(self):
        return self.__birthdate

    def get_gender(self):
        return self.__gender