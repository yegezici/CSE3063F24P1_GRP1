from datetime import date
from Person import Person
from Advisor import Advisor
from Transcript import Transcript
from CourseSection import CourseSection

class Student(Person):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, transcript: 'Transcript' = None, student_id: str = None,):
        self.__name = name
        self.__surname = surname
        self.__birthdate = birthdate
        self.__gender = gender
        self.__transcript = transcript
        self.__id = student_id
        self.__advisor = None
        self.__interface = None

    def get_name(self):
        return self.__name

    def get_surname(self):
        return self.__surname

    def get_birthdate(self):
        return self.__birthdate

    def get_gender(self):
        return self.__gender

    def get_id(self) -> str:        
        return self.__id
    
    def get_ssn(self):
        return self.__id

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

    def get_interface(self):
        return self.__interface