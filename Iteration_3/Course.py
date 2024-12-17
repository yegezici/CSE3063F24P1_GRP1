from typing import List, Optional, TYPE_CHECKING

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
        self.course_id = course_id
        self.course_name = course_name
        self.credits = credits
        self.prerequisite_course = prerequisite_course
        self.semester = semester
        self.grade = grade
        self.lecturer: Optional['Lecturer'] = None
        self.students: List['Student'] = []
        self.course_sections: List['CourseSection'] = []

    def get_course_type(self) -> str:
        raise NotImplementedError("Subclasses must implement get_course_type()")

    def get_course_id(self) -> str:
        return self.course_id

    def get_course_name(self) -> str:
        return self.course_name

    def get_lecturer(self) -> Optional['Lecturer']:
        return self.lecturer

    def get_grade(self) -> Optional[str]:
        return self.grade

    def add_student(self, student: 'Student') -> None:
        try:
            if student is not None:
                self.students.append(student)
            else:
                print("The student object is null")
        except AttributeError:
            print("The students list has not been initialized.")
        except Exception as e:
            print(str(e))

    def get_prerequisite_course(self) -> Optional['Course']:
        return self.prerequisite_course

    def set_prerequisite_course(self, prerequisite_course: 'Course') -> None:
        self.prerequisite_course = prerequisite_course

    def get_credits(self) -> int:
        return self.credits

    def get_students(self) -> List['Student']:
        return self.students

    def set_students(self, students: List['Student']) -> None:
        self.students = students

    def get_course_sections(self) -> List['CourseSection']:
        return self.course_sections

    def set_course_sections(self, course_sections: List['CourseSection']) -> None:
        self.course_sections = course_sections

    def get_semester(self) -> int:
        return self.semester

    def __str__(self) -> str:
        return f"{self.course_name} {self.course_id}"
