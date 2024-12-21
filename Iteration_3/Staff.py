from datetime import date
from Person import Person

class Staff(Person):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None):
        super().__init__(name, surname, birthdate, gender, ssn)

    def get_ssn(self):
        """Override abstract method to return the SSN"""
        return self._Person__ssn

    def get_name(self):
        """Override abstract method to return the name"""
        return self._Person__name

    def get_surname(self):
        """Override abstract method to return the surname"""
        return self._Person__surname

    def get_birthdate(self):
        """Override abstract method to return the birthdate"""
        return self._Person__birthdate

    def get_gender(self):
        """Override abstract method to return the gender"""
        return self._Person__gender
