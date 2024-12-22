from datetime import date
from Person import Person

class Staff(Person):
    def __init__(self, _name: str = None, _surname: str = None, _birthdate: date = None, _gender: str = None, _ssn: str = None):
        super().__init__(_name, _surname, _birthdate, _gender, _ssn)

    def get_ssn(self):
        """Override abstract method to return the SSN"""
        return self._ssn

    def get_name(self):
        """Override abstract method to return the name"""
        return self._name

    def get_surname(self):
        """Override abstract method to return the surname"""
        return self._surname

    def get_birthdate(self):
        """Override abstract method to return the birthdate"""
        return self._birthdate

    def get_gender(self):
        """Override abstract method to return the gender"""
        return self._gender
