from typing import List, Optional

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
        self.lecturer: Optional[Lecturer] = None
        self.students: List[Student] = []
        self.course_sections: List[CourseSection] = []

    def get_course_type(self) -> str:
        raise NotImplementedError("Subclasses must implement get_course_type()")

    # Retrieves the unique identifier of the course
    def get_course_id(self) -> str:
        return self.course_id

    # Retrieves the name of the course
    def get_course_name(self) -> str:
        return self.course_name

    # Retrieves the lecturer responsible for teaching this course
    def get_lecturer(self) -> Optional['Lecturer']:
        return self.lecturer

    # Retrieves the grade
    def get_grade(self) -> Optional[str]:
        return self.grade

    # Adds student to the course if student is not null
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

    # Retrieves prerequisite course
    def get_prerequisite_course(self) -> Optional['Course']:
        return self.prerequisite_course

    def set_prerequisite_course(self, prerequisite_course: 'Course') -> None:
        self.prerequisite_course = prerequisite_course

    # Retrieves the number of credits assigned to this course
    def get_credits(self) -> int:
        return self.credits

    # Retrieves the list of students
    def get_students(self) -> List['Student']:
        return self.students

    def set_students(self, students: List['Student']) -> None:
        self.students = students

    # Retrieves the list of course sections
    def get_course_sections(self) -> List['CourseSection']:
        return self.course_sections

    def set_course_sections(self, course_sections: List['CourseSection']) -> None:
        self.course_sections = course_sections

    # Retrieves the semester
    def get_semester(self) -> int:
        return self.semester

    # String representation of the course
    def __str__(self) -> str:
        return f"{self.course_name} {self.course_id}"
