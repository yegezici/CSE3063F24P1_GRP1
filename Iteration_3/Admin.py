from Iteration_3.Staff import Staff
from Logging_Config import logger
from SQLiteManagement import SqliteManager
from typing import List,Optional
from datetime import date
class Admin(Staff):
    def __init__(self, 
                 name: str = "", 
                 surname: str = "", 
                 birthdate: Optional[date] = None, 
                 ssn: str = "", 
                 students: Optional[List['Student']] = None, 
                 advisors: Optional[List['Advisor']] = None, 
                 lecturers: Optional[List['Lecturer']] = None, 
                 department_schedulers: Optional[List['DepartmentScheduler']] = None):
        self.name = name
        self.surname = surname
        self.birthdate = birthdate
        self.ssn = ssn
        self.students = students if students is not None else []
        self.advisors = advisors if advisors is not None else []
        self.lecturers = lecturers if lecturers is not None else []
        self.department_schedulers = department_schedulers if department_schedulers is not None else []
        self.__interface = None
    
    def add_student(self, student):
        try:
            #SqliteManager.save_student(student)
            self.students.append(student)
        except Exception as e:
            logger.warning(str(e))
    
    def delete_student(self, student_id_to_be_deleted: str):
        try:
            #SqliteManager.delete_student(student)
            for student in self.students:
                if student.get_id() == student_id_to_be_deleted:
                    self.students.remove(student)
        except Exception as e:
            logger.warning(str(e))
    
    def add_advisor(self, advisor):
        try:
            #SqliteManager.add_advisor(advisor)
            self.advisors.append(advisor)
        except Exception as e:
            logger.warning(str(e))

    def delete_advisor(self, advisor):
        try:
            #SqliteManager.delete_advisor(advisor)
            self.advisors.remove(advisor)
        except Exception as e:
            logger.warning(str(e))

    def add_lecturer(self, lecturer):
        try:
            #SqliteManager.add_lecturer(lecturer)
            self.lecturers.append(lecturer)
        except Exception as e:
            logger.warning(str(e))
    
    def delete_lecturer(self, lecturer):
        try:
            #SqliteManager.delete_lecturer(lecturer)
            self.lecturers.remove(lecturer)
        except Exception as e:
            logger.warning(str(e))
    
    def add_department_scheduler(self, department_scheduler):
        try:
            #SqliteManager.add_department_scheduler(department_scheduler)
            self.department_schedulers.append(department_scheduler)
        except Exception as e:
            logger.warning(str(e))
    
    def delete_department_scheduler(self, department_scheduler):
        try:
            #SqliteManager.delete_department_scheduler(department_scheduler)
            self.department_schedulers.remove(department_scheduler)
        except Exception as e:
            logger.warning(str(e))

    def set_interface(self, interface):
        self.__interface = interface
    
    def initialize_interface(self):
        return self.__interface
    