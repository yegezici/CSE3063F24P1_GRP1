from datetime import date
from CourseSection import CourseSection
from typing import List,Optional
from Lecturer import Lecturer

class DepartmentScheduler(Lecturer):
    def __init__(self, name: str ="", surname: str ="", birthdate: date=None, gender: str ='', ssn: str ='', course_sections: List['CourseSection'] =None, all_classrooms: List[str] =None):
        self.name = name
        self.surname = surname
        self.course_sections = course_sections
        self.all_classrooms = all_classrooms
        self.all_time_intervals = [
            "8:30-9:20", "9:30-10:20", "10:30-11:20", "11:30-12:20",
            "12:30-13:20", "13:30-14:20", "14:30-15:20", "15:30-16:20"
        ]

    def assign_time_slot_to_section(self, course_section, time_slot):
        if not course_section or not time_slot:
            raise ValueError("CourseSection or TimeSlot cannot be null.")
        time_slot.assign_time_slot(course_section)

    def assign_lecturer_to_section(self, course_section, lecturer):
        if not course_section or not lecturer:
            raise ValueError("CourseSection or Lecturer cannot be null.")
        if self.handle_lecturer_conflict(lecturer, course_section):
            course_section.set_lecturer(lecturer)

    def manage_capacity(self, course_section, new_capacity):
        if new_capacity < course_section.capacity:
            raise ValueError("New capacity cannot be smaller than the old capacity.")
        size_increase = new_capacity - course_section.capacity
        course_section.set_capacity(new_capacity)
        self.manage_waitlist(course_section, size_increase)

    def manage_waitlist(self, course_section, size):
        waitlist = course_section.get_wait_list()
        for _ in range(size):
            if not waitlist:
                break
            student = waitlist.pop(0)
            student.transcript.add_current_course(course_section.parent_course)
        course_section.set_wait_list(waitlist)

    def handle_time_conflict(self, semester_courses, day):
        available_times = self.all_time_intervals.copy()
        for section in semester_courses:
            for time_slot in section.time_slots:
                if time_slot.day == day and time_slot.time_interval in available_times:
                    available_times.remove(time_slot.time_interval)
        return available_times

    def handle_classroom_conflict(self, day, time_interval):
        available_classrooms = self.all_classrooms.copy()
        for section in self.course_sections:
            for time_slot in section.time_slots:
                if time_slot.day == day and time_slot.time_interval == time_interval:
                    if time_slot.classroom in available_classrooms:
                        available_classrooms.remove(time_slot.classroom)
        return available_classrooms

    def handle_lecturer_conflict(self, lecturer, course_section):
        for section in self.course_sections:
            if section.lecturer == lecturer:
                for slot in section.time_slots:
                    for new_slot in course_section.time_slots:
                        if slot.time_interval == new_slot.time_interval:
                            return False
        return True

    def get_available_days(self, semester_courses):
        all_days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
        day_to_times = {day: self.all_time_intervals.copy() for day in all_days}

        for section in semester_courses:
            for slot in section.time_slots:
                if slot.day in day_to_times:
                    day_to_times[slot.day].remove(slot.time_interval)

        return [day for day, times in day_to_times.items() if times]

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
