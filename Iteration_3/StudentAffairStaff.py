from datetime import date
from typing import List
from Staff import Staff

class StudentAffairsStaff(Staff):
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, ssn: str = None):
        super().__init__(name, surname, birthdate, gender, ssn)
        
    def get_id(self):
        return super().get_ssn()
    
    def get_name(self):
        return super().get_name()
    
    def get_surname(self):
        return super().get_surname()
    
    def get_birthdate(self):
        return super().get_birthdate()
    
    def get_gender(self):
        return super().get_gender()
