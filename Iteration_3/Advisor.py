from datetime import date
from typing import List
from Lecturer import Lecturer

class Advisor(Lecturer):
    def __init__(self, name: str ="", surname: str ="", birthdate: date =None, gender: str ='', ssn: str ='', courses=None, students=None):
        super().__init__(name, surname, birthdate, gender, ssn, courses)
        self.students = students or []

    def approve_course(self, student, course_section):
        try:
            if not course_section.has_capacity():
                course_section.wait_list.append(student)
                print("Capacity is full. You are listed in waitlist.")
            else:
                student.transcript.add_current_course(course_section.parent_course)
                student.transcript.add_current_section(course_section)

                for course in student.transcript.waited_courses[:]:
                    if course.course_id == course_section.parent_course.course_id:
                        student.transcript.delete_from_waited_course(course)

                student.transcript.delete_from_waited_sections(course_section)

            course_section.current_students.append(student)
            print("The course has been approved.")
        except Exception as e:
            print(str(e))

    def reject_course(self, student, courseSection):
        try:
            student.transcript.delete_from_waited_course(courseSection.parent_course)
            student.transcript.delete_from_waited_sections(courseSection)
            print("The course has been rejected.")
        except Exception as e:
            print(str(e))

    def add_student(self, student):
        try:
            self.students.append(student)
        except Exception as e:
            print(str(e))

    def get_students(self) -> List:
        return self.students

    def check_section_conflict(self, student, course_section):
        course_sections_of_student = student.transcript.current_sections

        for time_slot in course_section.time_slots:
            for existing_section in course_sections_of_student:
                for existing_time_slot in existing_section.time_slots:
                    if time_slot.time_interval == existing_time_slot.time_interval:
                        return False
        return True
