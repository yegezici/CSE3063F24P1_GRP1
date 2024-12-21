from datetime import date
from abc import ABC, abstractmethod

class Person(ABC):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None):
        self.__name = name
        self.__surname = surname
        self.__birthdate = birthdate
        self.__gender = gender
        self.__ssn = ssn

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
    def get_ssn(self):
        """Get the SSN of the person"""
        pass
