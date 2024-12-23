from datetime import date
from CourseSection import CourseSection
from typing import List,Optional
from Staff import Staff

class DepartmentScheduler(Staff):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None, course_sections=None, all_time_intervals=None):
        super().__init__(name, surname, birthdate, gender, ssn)
        self.__course_sections = course_sections or []
        self.__all_time_intervals = all_time_intervals or []
        self.__interface = None
        self.__all_classrooms = ["M2Z09", "M2Z10", "M2Z11", "M2Z12", "M2Z04"]
    def assign_time_slot_to_section(self, course_section, time_slot):
        if not course_section or not time_slot:
            raise ValueError("CourseSection or TimeSlot cannot be null.")
        time_slot.assign_time_slot(course_section)

    def assign_lecturer_to_section(self, course_section, lecturer):
        if not course_section or not lecturer:
            raise ValueError("CourseSection or Lecturer cannot be null.")
        if self.handle_lecturer_conflict(lecturer, course_section):
            course_section.set_lecturer(lecturer)

    def get_available_classrooms(self, time_slot):
        available_classrooms = self.__all_time_intervals.copy()
        for section in self.__course_sections:
            for slot in section.get_time_slots():
                if slot.get_time_interval() == time_slot.get_time_interval():
                    if time_slot.get_classroom() in available_classrooms:
                        available_classrooms.remove(time_slot.get_classroom())
        return available_classrooms

    def handle_time_conflict(self, semester_courses, day):
        try:
            available_times = self.__all_time_intervals.copy()
            for section in semester_courses:
                for time_slot in section.get_time_slots():
                    if time_slot.get_day() == day and time_slot.get_time_interval() in available_times:
                        available_times.remove(time_slot.get_time_interval())
            return available_times
        except Exception as e:
            print(str(e))
        
    def handle_classroom_conflict(self, day, time_interval):
        try:
            available_classrooms = self.__all_classrooms.copy()
            for section in self.get_course_sections():
                for time_slot in section.get_time_slots():
                    if time_slot.get_day() == day and time_slot.get_time_interval() == time_interval:
                        if time_slot.get_classroom() in available_classrooms:
                            available_classrooms.remove(time_slot.get_classroom())
            return available_classrooms
        except Exception as e:
            print(str(e))
            
    def handle_lecturer_conflict(self, lecturer, course_section):
        try:
            for section in self.__course_sections:
                if section.get_lecturer() == lecturer:
                    for slot in section.get_time_slots():
                        for new_slot in course_section.get_time_slots():
                            if slot.get_time_interval() == new_slot.get_time_interval():
                                return False
            return True
        except Exception as e:
            print(str(e))
            
    def get_available_days(self, semester_courses: list) -> list:
        all_days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
        day_to_times = {day: self.__all_time_intervals.copy() for day in all_days}

        for section in semester_courses:
            for slot in section.get_time_slots():
                if slot.get_day() in day_to_times:
                    if slot.get_time_interval() in day_to_times[slot.get_day()]:
                        day_to_times[slot.get_day()].remove(slot.get_time_interval())

        return [day for day, times in day_to_times.items() if times]
    
    def semester_x_courses(self, x):
        semester_x_courses = []

        for course_section in self.__course_sections:
            if course_section.get_parent_course().get_semester() == x:
                semester_x_courses.append(course_section)
        return semester_x_courses

    def get_id(self):
        return super().get_ssn()

    def get_name(self):
        return super().get_name()

    def get_surname(self):
        return super().get_surname()

    def get_birthdate(self):
        return super().get_birthdate()

    def get_gender(self):
        return super().get_gender()
    
    def set_interface(self, interface):
        self.__interface = interface
        
    def initialize_interface(self):
        return self.__interface