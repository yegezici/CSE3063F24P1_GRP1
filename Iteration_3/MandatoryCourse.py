from Course import Course

class MandatoryCourse(Course):
    def __init__(self, course_id: str, course_name: str, credits: int, prerequisite_course: 'Course' = None, semester: int = None):
        if prerequisite_course and semester is not None:
            super().__init__(course_id, course_name, credits, prerequisite_course, semester)
        elif semester is not None:
            super().__init__(course_id, course_name, credits, semester)
        elif prerequisite_course:
            super().__init__(course_id, course_name, credits, prerequisite_course)
        else:
            super().__init__(course_id, course_name, credits)

    def get_course_type(self):
        return "Mandatory"
