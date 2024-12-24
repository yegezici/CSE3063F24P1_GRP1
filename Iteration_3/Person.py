from datetime import date
from abc import ABC, abstractmethod

class Person(ABC):
    def __init__(self, _name: str = None, _surname: str = None, _birthdate: date = None, _gender: str = None, _ssn: str = None):
        self._name = _name
        self._surname = _surname
        self._birthdate = _birthdate
        self._gender = _gender
        self._ssn = _ssn

    @abstractmethod
    def get_name(self):
        """Get the name of the person"""
        pass

    @abstractmethod
    def get_surname(self):
        """Get the surname of the person"""
        pass

    @abstractmethod
    def get_birthdate(self):
        """Get the birthdate of the person"""
        pass

    @abstractmethod
    def get_gender(self):
        """Get the gender of the person"""
        pass
    @abstractmethod
    def initialize_interface(self, interface):
        pass

    def get_id(self):
        return self._ssn
        
