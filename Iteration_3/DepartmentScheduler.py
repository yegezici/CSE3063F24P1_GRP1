from datetime import date
from CourseSection import CourseSection
from typing import List,Optional
from Lecturer import Lecturer
from NotificationSystem import NotificationSystem

class DepartmentScheduler:
    def __init__(self, course_sections=None, all_time_intervals=None):
        self.__course_sections = course_sections or []
        self.__all_time_intervals = all_time_intervals or []

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
        try:
            waitlist = course_section.get_wait_list()
            for _ in range(size):
                if not waitlist:
                    break
                student = waitlist.pop(0)
                student.transcript.add_current_course(course_section.parent_course)
                NotificationSystem.create_notification(sender=self, receiver=student, message="Capacity of " +course_section.get_name() + " has been updated, You are now registered to the course.")
            course_section.set_wait_list(waitlist)
        except Exception as e:
            print(str(e))


    def handle_time_conflict(self, semester_courses, day):
        try:
            available_times = self.__all_time_intervals.copy()
            for section in semester_courses:
                for time_slot in section.time_slots:
                    if time_slot.day == day and time_slot.time_interval in available_times:
                        available_times.remove(time_slot.time_interval)
            return available_times
        except Exception as e:
            print(str(e))
        
    def handle_classroom_conflict(self, day, time_interval):
        try:
            available_classrooms = self.all_classrooms.copy()
            for section in self.course_sections:
                for time_slot in section.time_slots:
                    if time_slot.day == day and time_slot.time_interval == time_interval:
                        if time_slot.classroom in available_classrooms:
                            available_classrooms.remove(time_slot.classroom)
            return available_classrooms
        except Exception as e:
            print(str(e))
            
    def handle_lecturer_conflict(self, lecturer, course_section):
        try:
            for section in self.__course_sections:
                if section.lecturer == lecturer:
                    for slot in section.time_slots:
                        for new_slot in course_section.time_slots:
                            if slot.time_interval == new_slot.time_interval:
                                return False
            return True
        except Exception as e:
            print(str(e))
            
    def get_available_days(self, semester_courses):
        all_days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
        day_to_times = {day: self.__all_time_intervals.copy() for day in all_days}

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
