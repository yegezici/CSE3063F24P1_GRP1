from datetime import date
from typing import List
from Staff import Staff
from Course import Course
from CourseSection import CourseSection
from MandatoryCourse import MandatoryCourse
from NonTechnicalElectiveCourse import NonTechnicalElectiveCourse
from TechnicalElectiveCourse import TechnicalElectiveCourse

class StudentAffairsStaff(Staff):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None):
        super().__init__(name, surname, birthdate, gender, ssn)
    
    def create_course(self, course_name: str, course_id: str, course_type: str, credits: int, number_of_sections: int) -> Course:
        course = None
        if course_type == "m":
            course = MandatoryCourse(course_name, course_id, credits)
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
