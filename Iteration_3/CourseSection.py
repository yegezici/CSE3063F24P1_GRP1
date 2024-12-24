from typing import List, Optional
from Lecturer import Lecturer
from Course import Course
from TimeSlot import TimeSlot

class CourseSection:
    def __init__(self, capacity: int = None, parent_course: 'Course' = None, lecturer: 'Lecturer' = None, section_id: str = None):
        self.__capacity = capacity
        self.__parent_course = parent_course
        self.__lecturer = lecturer
        self.__current_students: List['Student'] = []
        self.section_id = section_id
        self.time_slots: List['TimeSlot'] = []  # Initialize time_slots
        self.wait_list: List['Student'] = []    # Initialize wait_list

    def get_capacity(self) -> int:
        return self.__capacity

    def set_capacity(self, capacity: int) -> None:
        self.__capacity = capacity

    def get_parent_course(self) -> Optional['Course']:
        return self.__parent_course

    def set_parent_course(self, parent_course: 'Course') -> None:
        self.__parent_course = parent_course

    def get_current_students(self) -> List['Student']:
        return self.__current_students

    def add_student_to_section(self, student: 'Student') -> None:
        self.__current_students.append(student)

    def get_lecturer(self) -> Optional['Lecturer']:
        return self.__lecturer
    
    def set_lecturer(self, lecturer: 'Lecturer') -> None:
        self.__lecturer = lecturer

    def get_section_id(self) -> str:
        return self.section_id

    def set_section_id(self, section_id: str) -> None:
        self.section_id = section_id

    def get_time_slots(self) -> List['TimeSlot']:
        return self.time_slots

    def set_time_slots(self, time_slots: List['TimeSlot']) -> None:
        self.time_slots = time_slots

    def get_wait_list(self) -> List['Student']:
        return self.wait_list

    def set_wait_list(self, wait_list: List['Student']) -> None:
        self.wait_list = wait_list

    def has_capacity(self) -> bool:
        return len(self.__current_students) < self.__capacity