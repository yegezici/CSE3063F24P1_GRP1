from typing import List

class Lecturer(Staff):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None, courses: List['Course'] = None):
        super().__init__(name, surname, birthdate, gender, ssn)
        self.courses = courses if courses else []

    def get_courses(self):
        return self.courses

    def set_courses(self, courses: List['Course']):
        self.courses = courses
