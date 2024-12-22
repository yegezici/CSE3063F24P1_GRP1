from datetime import date
from typing import List
from Staff import Staff

class StudentAffairsStaff(Staff):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None):
        super().__init__(name, surname, birthdate, gender, ssn)
        
    def get_id(self):
        return self.__ssn
    
    def get_name(self):
        return self.__name
    
    def get_surname(self):
        return self.__surname
    
    def get_birthdate(self):
        return self.__birthdate
    
    def get_gender(self):
        return self.__gender
