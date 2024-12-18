from datetime import date
from Person import Person
from Advisor import Advisor
from Transcript import Transcript

class Student(Person):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, transcript: 'Transcript' = None, student_id: str = None):
        super().__init__(name, surname, birthdate, gender, student_id)
        self.transcript = transcript
        self.advisor = None


    def get_name(self):
        return super().get_name_field()

    def get_surname(self):
        return super().get_surname_field()

    def get_birthdate(self):
        return super().get_birthdate_field()

    def get_gender(self):
        return super().get_gender_field()

    def get_id(self):
        return super().get_id_field()

    def get_transcript(self):
        return self.transcript

    def register_course(self, course_section: 'CourseSection'):  # String tip kullanımı
        self.transcript.add_waited_course(course_section.get_parent_course())
        self.transcript.get_waited_sections().append(course_section)

    def get_advisor(self):
        return self.advisor

    def set_advisor(self, advisor: 'Advisor'):  # String tip kullanımı
        self.advisor = advisor
