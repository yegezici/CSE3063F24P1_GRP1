from typing import List
from CourseSection import CourseSection
from TimeSlot import TimeSlot
from NotificationSystem import NotificationSystem
from DepartmentScheduler import DepartmentScheduler
from Lecturer import Lecturer
from UserInterface import UserInterface
class DepartmentSchedulerInterface(UserInterface):
    def __init__(self, department_scheduler: 'DepartmentScheduler', course_sections: 'List[CourseSection]', lecturers: 'List[Lecturer]', notification_system: 'NotificationSystem' = None):
        self.department_scheduler = department_scheduler
        self.course_sections = course_sections
        self.lecturers = lecturers
        self.notification_system = notification_system

    def update_time_interval(self, chosen_section):
        print("Time slots of the selected course are listed below:")
        for idx, slot in enumerate(chosen_section.get_time_slots(), 1):
            print(f"{idx}- {slot.get_day()} {slot.get_time_interval()} {slot.get_classroom()}")
        try:
            print("Which time slot do you want to update?")
            #chosen_section.get_time_slots().pop(int(input()) - 1)
            self.set_time_slot(chosen_section)
            print("Time slot has been updated successfully.")
            self.notification_system.create_notification(sender=self, receiver=chosen_section.get_current_students(), message="Time slot of " + chosen_section.get_name() + " has been updated.")
        except (ValueError, IndexError):
            print("Enter a valid integer within the list range.")

    def update_classroom(self, chosen_section):
        print("Time slots of the selected course are listed below:")
        for idx, slot in enumerate(chosen_section.get_time_slots(), 1):
            print(f"{idx}- {slot.get_day()} {slot.get_time_interval()} {slot.get_classroom()}")
        try:
            print("Which classroom do you want to update?")
            chosen_time_slot = chosen_section.get_time_slots()[int(input()) - 1]
            day = chosen_time_slot.get_day()
            time_interval = chosen_time_slot.get_time_interval()
            available_classrooms = self.department_scheduler.handle_classroom_conflict(day, time_interval)
            for idx, room in enumerate(available_classrooms, 1):
                print(f"{idx}- {room}")
            print("Choose a new classroom:")
            chosen_time_slot.set_classroom(available_classrooms[int(input()) - 1])
            print("Classroom has been updated successfully.")
            self.notification_system.create_notification(sender=self, receiver=chosen_section.get_current_students(), message="Classroom of " + chosen_section.get_name() + " has been updated.")
        except (ValueError, IndexError):
            print("Enter a valid integer.")

    def update_lecturer(self, chosen_section):
        if self.lecturers:
            print("Current lecturer is " + chosen_section.get_lecturer().get_name())
        print("Choose a new lecturer:")
        for idx, lecturer in enumerate(self.lecturers, 1):
            print(f"{idx}- {lecturer.get_name()}")
        try:
            new_lecturer = self.lecturers[int(input()) - 1]
            if self.department_scheduler.handle_lecturer_conflict(new_lecturer, chosen_section):
                chosen_section.set_lecturer(new_lecturer)
                print("Lecturer has been updated successfully.")
            else:
                print("Selected lecturer has a conflict with the course section.")
        except (ValueError, IndexError):
            print("Enter a valid integer.")

    def choose_course_section(self):
        self.show_available_course_sections()
        try:
            print("Choose a course section:")
            section_no = int(input()) - 1
            chosen_section = self.course_sections[section_no]
            if not chosen_section.get_lecturer():
                print("Selected course does not have any lecturer yet.")
                self.update_lecturer(chosen_section)
            if not chosen_section.time_slots:
                print("Selected course does not have any time slot or classroom yet.")
                self.set_time_slot(chosen_section)
            return chosen_section
        except (ValueError, IndexError):
            print("Enter a valid integer within the list range.")
            return None

    def show_menu(self):
        print("Enter 1 to access course configuration menu. If you want to quit press any button.")
        choice = input()
        if choice == "1":
            while True:
                chosen_section = None
                while chosen_section is None:
                    chosen_section = self.choose_course_section()
                    
                match self.get_choice():
                    case 1:
                        self.update_time_interval(chosen_section)
                    case 2:
                        self.update_classroom(chosen_section)
                    case 3: 
                        self.update_lecturer(chosen_section)
                    case _:
                        print("Enter a number between 1 and 3.")
        else:
            return True

    def get_choice(self):
        print("Choose an operation:\n1- Update time interval\n2- Update classroom\n3- Assign lecturer")
        try:
            return int(input())
        except ValueError:
            print("Enter a valid integer.")
            return 0

    def show_days(self, semester):
        print("SHOW DAYS GIRDI MI 1")
        semester_courses = self.department_scheduler.semester_x_courses(semester)
        available_days = self.department_scheduler.get_available_days(semester_courses)
        print("SHOW DAYS GIRDI MI 2")
        print("Available days:")
        for idx, day in enumerate(available_days, 1):
            print(f"{idx}- {day}")

    def show_time_intervals(self, semester, day):
        print(f"Available time slots for {day} are listed below:")
        available_slots = self.department_scheduler.handle_time_conflict(
            self.department_scheduler.semester_x_courses(semester), day
        )
        for idx, slot in enumerate(available_slots, 1):
            print(f"{idx}- {slot}")

    def show_classrooms(self, day, time_interval):
        available_classrooms = self.department_scheduler.handle_classroom_conflict(day, time_interval)
        print(f"Available classrooms for {day} {time_interval}:")
        print("len(available_classrooms): ", len(available_classrooms))
        for idx, room in enumerate(available_classrooms, 1):
            print(f"{idx}- {room}")

    def show_available_course_sections(self) -> None:
        print("All available course sections are listed below:")
        for idx, section in enumerate(self.course_sections, 1):
            course = section.get_parent_course()
            print(f"{idx}-{section.get_section_id()}")

    def set_time_slot(self, chosen_section):
        print("GIRDI MI")
        semester = chosen_section.get_parent_course().get_semester()
        print("GIRDI MI1")
        self.show_days(semester)
        print("Choose a day:")
        day = self.department_scheduler.get_available_days(
            self.department_scheduler.semester_x_courses(semester)
        )[int(input()) - 1]
        self.show_time_intervals(semester, day)
        print("Choose a time slot:")
        time_interval = self.department_scheduler.handle_time_conflict(
            self.department_scheduler.semester_x_courses(semester), day
        )[int(input()) - 1]
        self.show_classrooms(day, time_interval)
        print("Choose a classroom:")
        classroom = self.department_scheduler.handle_classroom_conflict(day, time_interval)[int(input()) - 1]
        chosen_section.get_time_slots().append(TimeSlot(day, time_interval, classroom))
        while(True):
            print("Choose a lecturer:")
            for index in self.lecturers:
                print(self.lecturers[index].get_id() + "  " + self.lecturers[index].get_name())
            lecturer = self.lecturers[input()]
            if (self.department_scheduler.handle_lecturer_conflict(lecturer, chosen_section) == False):
                print("Selected lecturer has a conflict with the course section. Please choose another one.")
                continue
            else:
                chosen_section.set_lecturer(lecturer)
                break
        print("Selected time slot and classroom has been assigned.")
