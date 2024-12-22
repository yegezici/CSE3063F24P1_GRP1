class Transcript:
    def __init__(self, completed_courses=None, current_courses=None, waited_courses=None, 
                 current_sections=None, waited_sections=None, semester=0):
        self.__completed_courses = completed_courses or []
        self.__current_courses = current_courses or []
        self.__waited_courses = waited_courses or []
        self.__current_sections = current_sections or []
        self.__waited_sections = waited_sections or []
        self.semester = semester

    def add_completed_course(self, course):
        if course:
            self.__completed_courses.append(course)
        else:
            print("The course object is null")

    def add_current_course(self, course):
        if course:
            self.__current_courses.append(course)
        else:
            print("The course object is null")

    def add_waited_course(self, course):
        if course:
            self.__waited_courses.append(course)
        else:
            print("The course object is null")

    def delete_from_waited_course(self, course):
        if course in self.__waited_courses:
            self.__waited_courses.remove(course)
        else:
            print("The course object is null")

    def add_current_section(self, course_section):
        if course_section:
            self.__current_sections.append(course_section)
        else:
            print("The course section object is null")

    def delete_from_waited_sections(self, course_section):
        if course_section in self.__waited_sections:
            self.__waited_sections.remove(course_section)
        else:
            print("The course section object is null")

    def show_waited_courses(self):
        print("\nCourses that are waiting for approval listed below:")
        for i, course in enumerate(self.__waited_courses, start=1):
            print(f"{i:3}-   {course.get_course_id():<10} {course.get_course_name():<50} {course.get_credits()}")

    def show_completed_courses(self):
        print("\nCourses that are completed listed below:")
        for i, course in enumerate(self.__completed_courses, start=1):
            print(f"{i:3}-   {course.get_course_id():<10} {course.get_course_name():<50} {course.get_credits():<10} {course.get_grade()}")

    def show_current_courses(self):
        print("\nCourses that are registered listed below:")
        for i, course in enumerate(self.__current_courses, start=1):
            print(f"{i:3}-   {course.get_course_id():<10} {course.get_course_name():<50} {course.get_credits()}")

    def add_course_sections(self, courses):
        course_sections = []
        for course in courses:
            course_sections.extend(course.get_course_sections())
        return course_sections

    def set_completed_courses(self, completed_courses):
        self.__completed_courses = completed_courses

    def set_current_courses(self, current_courses):
        self.__current_courses = current_courses

    def set_waited_courses(self, waited_courses):
        self.__waited_courses = waited_courses

    def get_completed_courses(self):
        return self.__completed_courses

    def get_current_courses(self):
        return self.__current_courses

    def get_waited_courses(self):
        return self.__waited_courses

    def get_current_sections(self):
        return self.__current_sections

    def set_current_sections(self, current_sections):
        self.__current_sections = current_sections

    def get_waited_sections(self):
        return self.__waited_sections

    def set_waited_sections(self, waited_sections):
        self.__waited_sections = waited_sections

    def calculate_gpa(self):
        total_sum = 0
        total_credits = 0

        for course in self.__completed_courses:
            grade = course.get_grade()
            credits = course.get_credits()
            grade_to_gpa = {
                "AA": 4.0, "BA": 3.5, "BB": 3.0, "CB": 2.5, "CC": 2.0,
                "DC": 1.5, "DD": 1.0, "FD": 0.5, "FF": 0.0
            }
            total_sum += grade_to_gpa.get(grade, 0) * credits
            total_credits += credits

        return round(total_sum / total_credits, 2) if total_credits > 0 else 0.0

    def get_semester(self):
        return self.semester

    def set_semester(self, semester):
        self.semester = semester
