from datetime import date
from Person import Person


class Staff(Person):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None):
        super().__init__(name, surname, birthdate, gender, ssn)

    def get_id(self):
        return super().get_id_field()

    def get_name(self):
        return super().get_name_field()

    def get_surname(self):
        return super().get_surname_field()

    def get_birthdate(self):
        return super().get_birthdate_field()

    def get_gender(self):
        return super().get_gender_field()
