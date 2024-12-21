from Lecturer import Lecturer
from Course import Course
from CourseSection import CourseSection
from MandatoryCourse import MandatoryCourse
from NonTechnicalElectiveCourse import NonTechnicalElectiveCourse
from TechnicalElectiveCourse import TechnicalElectiveCourse
from datetime import date
from typing import List
from NotificationSystem import NotificationSystem
class DepartmentHead(Lecturer):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, id: str = None):
        super().__init__(name, surname, birthdate, gender, id)
    
    def create_course(self, course_name: str, course_id: str, course_type: str, credits: int, number_of_sections: int) -> Course:
        course = None
        if course_type == "m":
            course = MandatoryCourse(course_id, course_name, credits)
        elif course_type == "te":
            course = TechnicalElectiveCourse(course_id, course_name, credits)
        elif course_type == "nte":
            course = NonTechnicalElectiveCourse(course_id, course_name, credits)
        
        course.set_course_sections(self.create_course_section(number_of_sections, course))
        return course
    
    def create_course_section(self, number_of_sections: int, parent_course: Course) -> List[CourseSection]:
        try:
            sections = []
            for i in range(number_of_sections):
                course_section = CourseSection()
                course_section.set_parent_course(parent_course)
                course_section.set_section_id(str(i + 1))
                sections.append(course_section)
            return sections
        except Exception as e:
            print(str(e))

    def set_capacity(self, chosen_section):
        print("Enter a capacity:")
        chosen_section.set_capacity(int(input()))
        print("Capacity has been set successfully.")

    def manage_capacity(self, course_section, new_capacity):
        if new_capacity < course_section.capacity:
            raise ValueError("New capacity cannot be smaller than the old capacity.")
        size_increase = new_capacity - course_section.get_capacity()
        course_section.set_capacity(new_capacity)
        self.manage_waitlist(course_section, size_increase)

    def manage_waitlist(self, course_section: CourseSection, size):
        try:
            waitlist = course_section.get_wait_list()
            for _ in range(size):
                if not waitlist:
                    break
                student = waitlist.pop(0)
                student.get_transcript().add_current_course(course_section.parent_course)
                NotificationSystem.create_notification(sender=self, receiver=student, message="Capacity of " +course_section.get_name() + " has been updated, You are now registered to the course.")
            course_section.set_wait_list(waitlist)
        except Exception as e:
            print(str(e))
        
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