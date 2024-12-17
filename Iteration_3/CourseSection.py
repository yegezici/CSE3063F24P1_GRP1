from typing import List, Optional
from datetime import time

class CourseSection:
    def __init__(self,
                 section_id: str = "",
                 capacity: int = 0,
                 parent_course: Optional['Course'] = None,
                 lecturer: Optional['Lecturer'] = None):
        self.section_id: str = section_id
        self.time_slots: List['TimeSlot'] = []
        self.wait_list: List['Student'] = []
        self.capacity: int = capacity
        self.lecturer: Optional['Lecturer'] = lecturer
        self.parent_course: Optional['Course'] = parent_course
        self.current_students: List['Student'] = []

    # Getters and Setters
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

    def get_lecturer(self) -> Optional['Lecturer']:
        return self.lecturer

    def set_lecturer(self, lecturer: 'Lecturer') -> None:
        self.lecturer = lecturer

    def get_capacity(self) -> int:
        return self.capacity

    def set_capacity(self, capacity: int) -> None:
        self.capacity = capacity

    def get_parent_course(self) -> Optional['Course']:
        return self.parent_course

    def set_parent_course(self, parent_course: 'Course') -> None:
        self.parent_course = parent_course

    def get_current_students(self) -> List['Student']:
        return self.current_students

    def add_student_to_section(self, student: 'Student') -> None:
        self.current_students.append(student)
