from datetime import date
from Person import Person
from Advisor import Advisor
from Transcript import Transcript
from CourseSection import CourseSection

class Student(Person):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, transcript: 'Transcript' = None, student_id: str = None,):
        super().__init__(name, surname, birthdate, gender, student_id)
        self.__transcript = transcript
        self.__advisor = None
        self.__interface = None
    def get_name(self):
        return super().get_name()

    def get_surname(self):
        return super().get_surname()

    def get_birthdate(self):
        return super().get_birthdate()

    def get_gender(self):
        return super().get_gender()

    def get_id(self) -> str:        
        return self.get_ssn()
    
    def get_ssn(self):
        return super().get_ssn()

    def get_transcript(self):
        return self.__transcript
    
    def set_interface(self, interface):
        self.__interface = interface

    def register_course(self, course_section: 'CourseSection'):  # String tip kullanımı
        try:
            self.__transcript.add_waited_course(course_section.get_parent_course())
            self.__transcript.get_waited_sections().append(course_section)
        except Exception as e:
            print(str(e))
            
    def get_advisor(self):
        return self.__advisor

    def set_advisor(self, advisor: 'Advisor'):  # String tip kullanımı
        self.__advisor = advisor
        
    def initialize_interface(self):
        return self.__interface
