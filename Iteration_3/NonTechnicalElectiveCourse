class NonTechnicalElectiveCourse(Course):
    def __init__(self, course_id: str, course_name: str, credits: int, prerequisite_course: 'Course' = None):
        if prerequisite_course:
            super().__init__(course_id, course_name, credits, prerequisite_course)
        else:
            super().__init__(course_id, course_name, credits)

    def __init__(self, course_id: str, course_name: str, grade: str, credits: int):
        super().__init__(course_id, course_name, grade, credits)

    def get_course_type(self):
        return "Non-Technical Elective"
