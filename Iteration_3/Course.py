from typing import List, Optional, TYPE_CHECKING
from Logging_Config import logger
if TYPE_CHECKING:
    from Student import Student
    from CourseSection import CourseSection
    from Lecturer import Lecturer

class Course:
    def __init__(self,
                 course_id: str,
                 course_name: str,
                 credits: int = 0,
                 prerequisite_course: Optional['Course'] = None,
                 semester: int = 0,
                 grade: Optional[str] = None):
        self.__course_id = course_id
        self.__course_name = course_name
        self.__credits = credits
        self.__prerequisite_course = prerequisite_course
        self.__semester = semester
        self.__grade = grade
        self.__lecturer: Optional['Lecturer'] = None
        self.__students: List['Student'] = []
        self.__course_sections: List['CourseSection'] = []

    def get_course_type(self) -> str:
        raise NotImplementedError("Subclasses must implement get_course_type()")

    def get_course_id(self) -> str:
        return self.__course_id

    def get_course_name(self) -> str:
        return self.__course_name

    def get_lecturer(self) -> Optional['Lecturer']:
        return self.__lecturer

    def get_grade(self) -> Optional[str]:
        return self.__grade
    
    def set_grade(self, grade: str) -> None:
        self.__grade = grade

    def add_student(self, student: 'Student') -> None:
        try:
            if student is not None:
                self.__students.append(student)
            else:
                logger.error("The student object is null")
        except AttributeError:
            logger.error("The students list has not been initialized.")
        except Exception as e:
            logger.error(str(e))

    def get_prerequisite_course(self) -> Optional['Course']:
        return self.__prerequisite_course

    def set_prerequisite_course(self, prerequisite_course: 'Course') -> None:
        self.__prerequisite_course = prerequisite_course

    def get_credits(self) -> int:
        return self.__credits

    def get_students(self) -> List['Student']:
        return self.__students

    def set_students(self, students: List['Student']) -> None:
        self.__students = students

    def get_course_sections(self) -> List['CourseSection']:
        return self.__course_sections

    def set_course_sections(self, course_sections: List['CourseSection']) -> None:
        self.__course_sections = course_sections

    def get_semester(self) -> int:
        return self.__semester

    def __str__(self) -> str:
        return f"{self.__course_name} {self.__course_id}"
    
    def get_credits(self) -> int:
        return self.__credits