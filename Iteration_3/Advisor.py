from datetime import date
from typing import List
from Lecturer import Lecturer
from Logging_Config import logger
class Advisor(Lecturer):
    def __init__(self, name: str = "", surname: str = "", birthdate: date = None, gender: str = '', ssn: str = '', courses=None, students=None):
        super().__init__(name, surname, birthdate, gender, ssn, courses)
        self.__students = students or []
        self.__interface = None

    def approve_course(self, student, course_section):
        try:
            if not course_section.has_capacity():
                course_section.get_wait_list().append(student)
                print("Capacity is full. You are listed in waitlist.")
            else:
                student.get_transcript().add_current_course(course_section.get_parent_course())
                student.get_transcript().add_current_section(course_section)

                for course in student.get_transcript().get_waited_courses()[:]:
                    if course.get_course_id() == course_section.get_parent_course().get_course_id():
                        student.get_transcript().delete_from_waited_course(course)

                student.get_transcript().delete_from_waited_sections(course_section)

            course_section.add_student_to_section(student)
            print("The course has been approved.")
            logger.info(f"{course_section.get_parent_course().get_course_name()} has been approved.----------------------{student.get_name()} {student.get_surname()} {course_section.get_parent_course().get_course_name()}")
        except Exception as e:
            print(str(e))

    def reject_course(self, student, course_section)-> bool :
        try:
            student.get_transcript().delete_from_waited_course(course_section.get_parent_course())
            student.get_transcript().delete_from_waited_sections(course_section)
            print("The course has been rejected.")
            logger.info(f"{course_section.get_parent_course().get_course_name()} has been rejected.----------------------{student.get_name()} {student.get_surname()} {course_section.get_parent_course().get_course_name()}")
        except Exception as e:
            logger.warning(str(e))

    def add_student(self, student):
        try:
            self.__students.append(student)
        except Exception as e:
            logger.warning(str(e))

    def get_students(self) -> List:
        try:
            return self.__students
        except Exception as e:
            logger.warning(str(e))
            return []

    def check_section_conflict(self, student, course_section):
        try:
            course_sections_of_student = student.get_transcript().get_current_sections()

            for time_slot in course_section.get_time_slots():
                for existing_section in course_sections_of_student:
                    for existing_time_slot in existing_section.get_time_slots():
                        if time_slot.get_time_interval() == existing_time_slot.get_time_interval():
                            if time_slot.get_day() == existing_time_slot.get_day():
                                return False
            return True
        except Exception as e:
            logger.error(f"{e}")
            return False

    def get_birthdate(self):
        return super().get_birthdate()
   
    def get_courses(self):
        return super().get_courses()
    
    def get_gender(self) -> str:
        return super().get_gender()

    def get_name(self) -> str:
        return super().get_name()

    def get_surname(self) -> str:
        return super().get_surname()

    def get_id(self) -> str:
        return super().get_id()

    def set_interface(self, interface):
        self.__interface = interface

    def initialize_interface(self):
        return self.__interface
    
    def get_interface(self):
        return self.__interface