from Iteration_3.Staff import Staff
from Logging_Config import logger
from SQLiteManagement import SqliteManager
from typing import List,Optional
from datetime import date
class Admin(Staff):
    def __init__(self, 
                 _name: str = "", 
                 _surname: str = "", 
                 _birthdate: Optional[date] = None, 
                 _ssn: str = "", 
                 _gender: str = "",
                 students: Optional[List['Student']] = None, 
                 advisors: Optional[List['Advisor']] = None, 
                 lecturers: Optional[List['Lecturer']] = None, 
                 department_schedulers: Optional[List['DepartmentScheduler']] = None):
        super().__init__(name=_name, surname=_surname, birthdate=_birthdate, gender=_gender, ssn=_ssn)
        self._students = students if students is not None else []
        self._advisors = advisors if advisors is not None else []
        self._lecturers = lecturers if lecturers is not None else []
        self._department_schedulers = department_schedulers if department_schedulers is not None else []
    
    def add_student(self, student):
        try:
            #SqliteManager.save_student(student)
            self._students.append(student)
        except Exception as e:
            logger.warning(str(e))
    
    def delete_student(self, student_id_to_be_deleted: str):
        try:
            #SqliteManager.delete_student(student)
            for student in self._students:
                if student.get_id() == student_id_to_be_deleted:
                    self._students.remove(student)
        except Exception as e:
            logger.warning(str(e))
    
    def add_advisor(self, advisor):
        try:
            #SqliteManager.add_advisor(advisor)
            self._advisors.append(advisor)
        except Exception as e:
            logger.warning(str(e))

    def delete_advisor(self, advisor_id):
        try:
            for advisor in self._advisors:
                if advisor.get_id() == advisor_id:
                    self._advisors.remove(advisor)
        except Exception as e:
            logger.warning(str(e))

    def add_lecturer(self, lecturer):
        try:
            #SqliteManager.add_lecturer(lecturer)
            self._lecturers.append(lecturer)
        except Exception as e:
            logger.warning(str(e))
    
    def delete_lecturer(self, lecturer):
        try:
            #SqliteManager.delete_lecturer(lecturer)
            for lecturer in self._lecturers:
                if lecturer.get_id() == lecturer.get_id():
                    self._lecturers.remove(lecturer)
        except Exception as e:
            logger.warning(str(e))
    
    def add_department_scheduler(self, department_scheduler):
        try:
            #SqliteManager.add_department_scheduler(department_scheduler)
            self._department_schedulers.append(department_scheduler)
        except Exception as e:
            logger.warning(str(e))
    
    def delete_department_scheduler(self, department_scheduler):
        try:
            #SqliteManager.delete_department_scheduler(department_scheduler)
            for department_scheduler in self._department_schedulers:
                if department_scheduler.get_id() == department_scheduler.get_id():
                    self._department_schedulers.remove(department_scheduler)
        except Exception as e:
            logger.warning(str(e))

   # Getter and Setter for name
    def get_name(self):
        return super().get_name()

    # Getter and Setter for surname
    def get_surname(self):
        return super().get_surname()

    # Getter and Setter for birthdate
    def get_birthdate(self):
        return super().get_birthdate()

    # Getter and Setter for ssn
    def get_ssn(self):
        return super().get_ssn()
    
     # Getter and Setter for students
    def get_students(self):
        return self._students

    def set_students(self, students):
        self._students = students

    # Getter and Setter for advisors
    def get_advisors(self):
        return self._advisors

    def set_advisors(self, advisors):
        self._advisors = advisors

    # Getter and Setter for lecturers
    def get_lecturers(self):
        return self._lecturers

    def set_lecturers(self, lecturers):
        self._lecturers = lecturers

    # Getter and Setter for department_schedulers
    def get_department_schedulers(self):
        return self._department_schedulers

    def set_department_schedulers(self, department_schedulers):
        self._department_schedulers = department_schedulers