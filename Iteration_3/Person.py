from datetime import date

class Person:
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None):
        self.__name = name
        self.__surname = surname
        self.__birthdate = birthdate
        self.__gender = gender
        self.__ssn = ssn

    def get_name(self):
        return self.__name

    def get_surname(self):
        return self.__surname

    def get_birthdate(self):
        return self.__birthdate

    def get_gender(self):
        return self.__gender

    def get_ssn(self):
        return self.__ssn
