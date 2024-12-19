from Iteration_3.Staff import Staff
from Logging_Config import logger
from SQLiteManagement import SqliteManager
class Admin(Staff):
    def __init__(self, name: str ="", surname: str ="", birthdate: date ="",ssn="",students="",advisors="",lecturers="",department_schedulers=""):
        self.name = name
        self.surname = surname
        self.birthdate = birthdate
        self.ssn = ssn
        self.students = students
        self.advisors = advisors
        self.lecturers = lecturers
    
    def add_student(self, student):
        try:
            SqliteManager.save_student(student)
            self.students.append(student)
        except Exception as e:
            logger.warning(str(e))
    
    def delete_student(self, student):
        try:
            SqliteManager.delete_student(student)
            self.students.remove(student)
        except Exception as e:
            logger.warning(str(e))
    
    def add_advisor(self, advisor):
        try:
            SqliteManager.add_advisor(advisor)
            self.advisors.append(advisor)
        except Exception as e:
            logger.warning(str(e))

    def delete_advisor(self, advisor):
        try:
            SqliteManager.delete_advisor(advisor)
            self.advisors.remove(advisor)
        except Exception as e:
            logger.warning(str(e))

    def add_lecturer(self, lecturer):
        try:
            SqliteManager.add_lecturer(lecturer)
            self.lecturers.append(lecturer)
        except Exception as e:
            logger.warning(str(e))
    
    def delete_lecturer(self, lecturer):
        try:
            SqliteManager.delete_lecturer(lecturer)
            self.lecturers.remove(lecturer)
        except Exception as e:
            logger.warning(str(e))
    
    def add_department_scheduler(self, department_scheduler):
        try:
            SqliteManager.add_department_scheduler(department_scheduler)
            self.department_schedulers.append(department_scheduler)
        except Exception as e:
            logger.warning(str(e))
    
    def delete_department_scheduler(self, department_scheduler):
        try:
            SqliteManager.delete_department_scheduler(department_scheduler)
            self.department_schedulers.remove(department_scheduler)
        except Exception as e:
            logger.warning(str(e))

    