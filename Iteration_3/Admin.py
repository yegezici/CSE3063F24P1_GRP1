from Staff import Staff
from Logging_Config import logger
from typing import List, Optional, Union
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

        super().__init__(_name, _surname, _birthdate, _gender, _ssn)
        self._students: List['Student'] = students if students is not None else []
        self._advisors: List['Advisor'] = advisors if advisors is not None else []
        self._lecturers: List['Lecturer'] = lecturers if lecturers is not None else []
        self._department_schedulers: List['DepartmentScheduler'] = department_schedulers if department_schedulers is not None else []
        self.__interface: Optional[object] = None
        from SQLiteManagement import SQLiteManagement
        self.manager: SQLiteManagement = SQLiteManagement()

    def add_student(self, student: 'Student') -> None:
        try:
            self.manager.save_student(student)
            self._students.append(student)
        except Exception as e:
            logger.warning(str(e))

    def delete_student(self, student_id_to_be_deleted: str) -> None:
        try:
            for student in self._students:
                if student.get_id() == student_id_to_be_deleted:
                    self._students.remove(student)
        except Exception as e:
            logger.warning(str(e))

    def add_advisor(self, advisor: 'Advisor') -> None:
        try:
            self._advisors.append(advisor)
        except Exception as e:
            logger.warning(str(e))

    def delete_advisor(self, advisor_id: str) -> None:
        try:
            for advisor in self._advisors:
                if advisor.get_id() == advisor_id:
                    self._advisors.remove(advisor)
        except Exception as e:
            logger.warning(str(e))

    def add_lecturer(self, lecturer: 'Lecturer') -> None:
        try:
            self._lecturers.append(lecturer)
        except Exception as e:
            logger.warning(str(e))

    def delete_lecturer(self, lecturer_id: str) -> None:
        try:
            for lecturer in self._lecturers:
                if lecturer.get_id() == lecturer_id:
                    self._lecturers.remove(lecturer)
        except Exception as e:
            logger.warning(str(e))

    def add_department_scheduler(self, department_scheduler: 'DepartmentScheduler') -> None:
        try:
            self._department_schedulers.append(department_scheduler)
        except Exception as e:
            logger.warning(str(e))

    def delete_department_scheduler(self, department_scheduler_id: str) -> None:
        try:
            for department_scheduler in self._department_schedulers:
                if department_scheduler.get_id() == department_scheduler_id:
                    self._department_schedulers.remove(department_scheduler)
        except Exception as e:
            logger.warning(str(e))

    # Getter and Setter for name
    def get_name(self) -> str:
        return super().get_name()

    # Getter and Setter for surname
    def get_surname(self) -> str:
        return super().get_surname()

    # Getter and Setter for birthdate
    def get_birthdate(self) -> Optional[date]:
        return super().get_birthdate()

    # Getter and Setter for ssn
    def get_id(self) -> str:
        return super().get_id()

    # Getter and Setter for students
    def get_students(self) -> List['Student']:
        return self._students

    def set_students(self, students: List['Student']) -> None:
        self._students = students

    # Getter and Setter for advisors
    def get_advisors(self) -> List['Advisor']:
        return self._advisors

    def set_advisors(self, advisors: List['Advisor']) -> None:
        self._advisors = advisors

    # Getter and Setter for lecturers
    def get_lecturers(self) -> List['Lecturer']:
        return self._lecturers

    def set_lecturers(self, lecturers: List['Lecturer']) -> None:
        self._lecturers = lecturers

    # Getter and Setter for department_schedulers
    def get_department_schedulers(self) -> List['DepartmentScheduler']:
        return self._department_schedulers

    def set_department_schedulers(self, department_schedulers: List['DepartmentScheduler']) -> None:
        self._department_schedulers = department_schedulers

    def set_interface(self, interface):
        self.__interface = interface

    def initialize_interface(self):
        return self.__interface
