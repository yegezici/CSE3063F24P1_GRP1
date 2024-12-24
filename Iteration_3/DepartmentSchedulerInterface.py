from typing import List
from CourseSection import CourseSection
from TimeSlot import TimeSlot
from NotificationSystem import NotificationSystem
from DepartmentScheduler import DepartmentScheduler
from Lecturer import Lecturer
from UserInterface import UserInterface
class DepartmentSchedulerInterface(UserInterface):
    def __init__(self, department_scheduler: 'DepartmentScheduler'):
        self.__department_scheduler = department_scheduler
        self.__course_sections = department_scheduler.get_manager().get_course_sections()
        self.__lecturers = department_scheduler.get_manager().get_lecturers()
        self.__notification_system =  None
    
    def set_notification_system(self, notification_system: NotificationSystem):
        self.__notification_system = notification_system
    
    def set_lecturers(self, lecturers: list[Lecturer]):
        self.lecturers = lecturers
        

    def set_lecturers(self, lecturers: list[Lecturer]):
        self.__lecturers = lecturers
    def update_time_interval(self, chosen_section):
        print("Time slots of the selected course are listed below:")
        for idx, slot in enumerate(chosen_section.get_time_slots(), 1):
            print(f"{idx}- {slot.get_day()} {slot.get_time_interval()} {slot.get_classroom()}")
        try:
            print("Which time slot do you want to update?")
            chosen_section.get_time_slots().pop(int(input()) - 1)
            self.set_time_slot(chosen_section)
            print("Time slot has been updated successfully.")
            message = f"Time slot of {chosen_section.get_section_id()} has been updated."
            self.__notification_system.create_notification(sender=self, receiver=chosen_section.get_current_students(), message=message)
            self.__department_scheduler.get_manager().save_time_slots()
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
            available_classrooms = self.__department_scheduler.handle_classroom_conflict(day, time_interval)
            for idx, room in enumerate(available_classrooms, 1):
                print(f"{idx}- {room}")
            print("Choose a new classroom:")
            chosen_time_slot.set_classroom(available_classrooms[int(input()) - 1])
            self.__department_scheduler.get_manager().save_time_slots()
            print("Classroom has been updated successfully.")
            self.__notification_system.create_notification(sender=self, receiver=chosen_section.get_current_students(), message="Classroom of " + chosen_section.get_section_id() + " has been updated.")
        except (ValueError, IndexError):
            print("Enter a valid integer.")

    def update_lecturer(self, chosen_section):
        if chosen_section.get_lecturer():    
            print("Current lecturer is " + chosen_section.get_lecturer().get_name())
        print("Choose a new lecturer:")
        for idx, lecturer in enumerate(self.__lecturers, 1):
            print(f"{idx}- {lecturer.get_name()} {lecturer.get_surname()}")
        try:
            new_lecturer = self.__lecturers[int(input()) - 1]
            if self.__department_scheduler.handle_lecturer_conflict(new_lecturer, chosen_section):
                chosen_section.set_lecturer(new_lecturer)
                print("Lecturer has been updated successfully.")
                self.__department_scheduler.get_manager().update_course_section_lecturer(chosen_section, new_lecturer)
            else:
                print("Selected lecturer has a conflict with the course section.")
        except (ValueError, IndexError):
            print("Enter a valid integer.")
        except:
            print("There is an error in update_lecturer of DSI.")

    def choose_course_section(self):
        self.show_available_course_sections()
        try:
            print("Choose a course section:")
            print("If you want to return main menu press \'0\'.")
            section_no = int(input()) - 1
            if section_no == -1:
                return -1
            chosen_section = self.__course_sections[section_no]
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
        while True:
            print("Enter 1 to access course configuration menu. If you want to quit press any button.")
            choice = input()
            if choice == "1":
                while True:
                    chosen_section = None
                    while chosen_section is None:
                        chosen_section = self.choose_course_section()
                    if chosen_section == -1:
                        break
                    
                    match self.get_choice():
                        case 1:
                            self.update_time_interval(chosen_section)
                        case 2:
                            self.update_classroom(chosen_section)
                        case 3: 
                            self.update_lecturer(chosen_section)
                        case 4:
                            break
                        case _:
                            print("Enter a number between 1 and 4.")
            else:
                return True

    def get_choice(self):
        print("Choose an operation:\n1- Update time interval\n2- Update classroom\n3- Assign lecturer\n4- Back to previous menu")
        try:
            return int(input())
        except ValueError:
            print("Enter a valid integer.")
            return 0

    def show_days(self, semester):
        semester_courses = self.__department_scheduler.semester_x_courses(semester)
        available_days = self.__department_scheduler.get_available_days(semester_courses)
        for idx, day in enumerate(available_days, 1):
            print(f"{idx}- {day}")

    def show_time_intervals(self, semester, day):
        print(f"Available time slots for {day} are listed below:")
        available_slots = self.__department_scheduler.handle_time_conflict(
            self.__department_scheduler.semester_x_courses(semester), day
        )
        for idx, slot in enumerate(available_slots, 1):
            print(f"{idx}- {slot}")

    def show_classrooms(self, day, time_interval):
        available_classrooms = self.__department_scheduler.handle_classroom_conflict(day, time_interval)
        print(f"Available classrooms for {day} {time_interval}:")
        print("len(available_classrooms): ", len(available_classrooms))
        for idx, room in enumerate(available_classrooms, 1):
            print(f"{idx}- {room}")

    def show_available_course_sections(self) -> None:
        print("All available course sections are listed below:")
        for idx, section in enumerate(self.__course_sections, 1):
            course = section.get_parent_course()
            print(f"{idx}-{section.get_section_id()}")

    def set_time_slot(self, chosen_section: CourseSection) -> None:
        semester = chosen_section.get_parent_course().get_semester()
        self.show_days(semester)
        print("Choose a day:")
        day = self.__department_scheduler.get_available_days(
            self.__department_scheduler.semester_x_courses(semester)
        )[int(input()) - 1]
        self.show_time_intervals(semester, day)
        print("Choose a time slot:")
        time_interval = self.__department_scheduler.handle_time_conflict(
            self.__department_scheduler.semester_x_courses(semester), day
        )[int(input()) - 1]
        self.show_classrooms(day, time_interval)
        print("Choose a classroom:")
        classroom = self.__department_scheduler.handle_classroom_conflict(day, time_interval)[int(input()) - 1]
        chosen_section.get_time_slots().append(TimeSlot(day, time_interval, classroom))
        print("Selected time slot and classroom has been assigned.")
        self.__department_scheduler.get_manager().save_time_slots()
