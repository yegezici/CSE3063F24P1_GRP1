from Course import Course

class TechnicalElectiveCourse(Course):
    def __init__(self, course_id, course_name, credits, prerequisite_course=None, semester=None, grade=None):
        if grade is not None:
            super().__init__(course_id, course_name, grade, credits)
        elif prerequisite_course is not None and semester is not None:
            super().__init__(course_id, course_name, credits, prerequisite_course, semester)
        elif semester is not None:
            super().__init__(course_id, course_name, credits, semester=semester)
        else:
            super().__init__(course_id, course_name, credits)

    def get_course_type(self):
        return "Technical Elective"
