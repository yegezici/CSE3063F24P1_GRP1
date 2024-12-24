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
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None, manager = None):
        super().__init__(name, surname, birthdate, gender, ssn)
        self.__interface = None
        self.__manager = manager
        
    def create_course(self, course_name: str, course_id: str, course_type: str, credits: int, number_of_sections: int, capacity: int, 
                      semester : int , prerequisite_id : str) -> Course:
        course = None
        if course_type == "m":
            course = MandatoryCourse(course_id, course_name, credits, semester= semester)
        elif course_type == "te":
            course = TechnicalElectiveCourse(course_id, course_name, credits, semester= semester)
        elif course_type == "nte":
            course = NonTechnicalElectiveCourse(course_id, course_name, credits)
        if course_type != "nte":
            for prerequisite_course in self.__manager.get_courses():
                if prerequisite_course.get_course_id() == prerequisite_id:
                    course.set_prerequisite_course(prerequisite_course)
                    break
        course.set_course_sections(self.create_course_section(number_of_sections, course, capacity))
        self.__manager.save_course(course)
        return course
    
    def create_course_section(self, number_of_sections: int, parent_course: Course, capacity: int) -> List[CourseSection]:
        try:
            sections = []
            for i in range(number_of_sections):
                course_section = CourseSection(capacity, parent_course, None, str(i + 1))
                course_section.set_capacity(capacity)
                course_section.set_parent_course(parent_course)
                course_section.set_section_id(parent_course.get_course_name() + "." + str(i + 1))
                sections.append(course_section)
                self.__manager.get_course_sections().append(course_section)   
            return sections
        except Exception as e:
            print(str(e))

    def set_capacity(self, chosen_section):
        print("Enter a capacity:")
        chosen_section.set_capacity(int(input()))
        print("Capacity has been set successfully.")

    def manage_capacity(self, course_section, new_capacity, notification_system: NotificationSystem):
        
        if new_capacity < course_section.get_capacity():
            print("New capacity cannot be less than the current capacity.")
            return False
        else:
            size_increase = new_capacity - course_section.get_capacity()
            course_section.set_capacity(new_capacity)
            self.__manager.update_course_section_capacity(course_section.get_section_id(), new_capacity)
            self.manage_waitlist(course_section, size_increase, notification_system)
            return True

    def manage_waitlist(self, course_section: CourseSection, size, notification_system: NotificationSystem):
        try:
            waitlist = course_section.get_wait_list()
            for _ in range(size):
                if not waitlist:
                    break
                student = waitlist.pop(0)
                student.get_transcript().add_current_course(course_section.parent_course)
                notification_system.create_notification(sender=self, receiver=student, message="Capacity of " +course_section.get_name() + " has been updated, You are now registered to the course.")
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

    def get_interface(self):
        return self.__interface
    
    def get_manager(self):
        return self.__manager
    
    def set_interface(self, interface):
        self.__interface = interface
    def initialize_interface(self):
        return self.__interface