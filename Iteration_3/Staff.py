from datetime import date
from Person import Person


class Staff(Person):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None):
        super().__init__(name, surname, birthdate, gender, ssn)
        self.ssn = ssn

    def get_id(self):
        return self.ssn

    def get_name(self):
        return super().get_name_field()

    def get_surname(self):
        return super().get_surname_field()

    def get_birthdate(self):
        return super().get_birthdate_field()

    def get_gender(self):
        return super().get_gender_field()
