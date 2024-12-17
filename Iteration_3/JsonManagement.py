import json
from typing import List, Optional
from datetime import datetime


class JsonManagement:
    _instance = None

    def __new__(cls):
        if cls._instance is None:
            cls._instance = super().__new__(cls)
        return cls._instance

    def __init__(self):
        if not hasattr(self, 'initialized'):
            self.prerequisites = []
            self.students = []
            self.courses = self.load_courses()
            self.add_prerequisite()
            self.course_sections = self.load_course_sections()
            self.set_course_sections_of_courses()
            self.classrooms = self.read_classrooms()
            self.find_students()
            self.initialized = True

    @classmethod
    def get_instance(cls):
        if cls._instance is None:
            return cls()
        return cls._instance

    def get_courses(self):
        return self.courses

    def get_students(self):
        return self.students

    def get_course_sections(self):
        return self.course_sections

    def get_classrooms(self):
        return self.classrooms

    def find_students(self):
        student_ids = [
            "150121031", "150121032", "150121033", "150121034", "150121035",
            "150122036", "150122037", "150122038", "150122039", "150122040",
            "150122041", "150122042"
        ]

        for student_id in student_ids:
            self.get_student_by_id(student_id)

        for course_section in self.course_sections:
            for student in self.students:
                if course_section in student.transcript.current_sections:
                    course_section.add_student_to_section(student)

    def load_course_sections(self):
        file_path = "iteration_2/src/main/java/courseSections.json"
        course_sections_list = []

        with open(file_path, 'r') as reader:
            root_object = json.load(reader)

            for course_id, sections_array in root_object.items():
                for section in sections_array:
                    section_id = str(section['sectionID'])
                    time = section.get('time', '')
                    classroom = section.get('classroom', '')
                    capacity = section['capacity']

                    course_section = None
                    if not time:
                        course_section = CourseSection(section_id, capacity)
                    else:
                        time_parts = time.split(' ', 1)
                        day = time_parts[0]
                        time_interval = time_parts[1]
                        time_slot = TimeSlot(day, time_interval, classroom)
                        course_section = CourseSection(section_id, capacity)
                        course_section.time_slots.append(time_slot)

                    course = next((c for c in self.courses if c.course_id == course_id), None)
                    if course:
                        course_section.parent_course = course
                        course_sections_list.append(course_section)

        return course_sections_list

    def set_course_sections_of_courses(self):
        for course in self.courses:
            course.course_sections = [
                cs for cs in self.course_sections
                if cs.parent_course and cs.parent_course.course_id == course.course_id
            ]

    def load_courses(self):
        courses = []
        file_path = "iteration_2/src/main/java/courseList.json"

        with open(file_path, 'r') as reader:
            json_object = json.load(reader)

            course_types = {
                'mandatory': MandatoryCourse,
                'technicalElective': TechnicalElectiveCourse,
                'nonTechnicalElective': NonTechnicalElectiveCourse
            }

            for course_type, course_class in course_types.items():
                for course_json in json_object.get(course_type, []):
                    course_id = course_json['courseID']
                    course_name = course_json['name']
                    credits = int(course_json['credits'])
                    prerequisite = course_json.get('prerequisite', '')

                    if course_type != 'nonTechnicalElective':
                        semester = int(course_json['year'])
                        course = course_class(course_id, course_name, credits, semester)
                    else:
                        course = course_class(course_id, course_name, credits)

                    self.prerequisites.append(f"{prerequisite}-{course_id}")
                    courses.append(course)

        return courses

    def add_prerequisite(self):
        for course in self.prerequisites:
            parts = course.split('-')
            prerequisite_id = parts[0]
            course_id = parts[1]

            for c in self.courses:
                if c.course_id == course_id:
                    for p in self.courses:
                        if p.course_id == prerequisite_id:
                            c.prerequisite_course = p

    def get_student_affairs_staff_by_id(self, affair_id):
        file_path = "Iteration_2/src/main/java/parameters.json"

        with open(file_path, 'r') as reader:
            json_data = json.load(reader)

            for affair in json_data.get('studentAffairsStaffs', []):
                if affair['userID'] == affair_id:
                    return StudentAffairsStaff(
                        affair['userID'],
                        affair['name'],
                        affair['surname']
                    )

        print(f"Affair with ID: {affair_id} not found.")
        return None

    def get_department_scheduler_by_id(self, scheduler_id):
        file_path = "Iteration_2/src/main/java/parameters.json"

        with open(file_path, 'r') as reader:
            json_data = json.load(reader)

            for scheduler in json_data.get('departmentSchedulers', []):
                if scheduler['userID'] == scheduler_id:
                    return DepartmentScheduler(
                        scheduler['name'],
                        scheduler['surname'],
                        self.course_sections,
                        self.classrooms
                    )

        print(f"Scheduler with ID: {scheduler_id} not found.")
        return None

    def check_student_if_exists(self, student_id):
        return next((student for student in self.students if student.id == student_id), None)

    def get_student_by_id(self, student_id):
        student = self.get_student_by_id_without_advisor(student_id)
        if student:
            self.set_advisor_for_student(student)
        return student

    def get_student_by_id_without_advisor(self, student_id):
        existing_student = self.check_student_if_exists(student_id)
        if existing_student:
            return existing_student

        file_path = "Iteration_2/src/main/java/parameters.json"

        with open(file_path, 'r') as reader:
            json_data = json.load(reader)

            for student_json in json_data.get('students', []):
                if student_json['userID'] == student_id:
                    name = student_json['name']
                    surname = student_json['surname']
                    date = student_json['birthdate']
                    birthdate = datetime.strptime(date, "%Y-%m-%d")
                    gender = student_json['gender'][0]

                    transcript = self.create_transcript(student_id)
                    student = Student(name, surname, birthdate, gender, transcript, student_id)
                    self.create_array_list(student)
                    return student

        return None

    def set_advisor_for_student(self, student):
        file_path = "Iteration_2/src/main/java/parameters.json"

        with open(file_path, 'r') as reader:
            json_data = json.load(reader)

            for student_json in json_data.get('students', []):
                if student_json['userID'] == student.id:
                    advisor_id = student_json['advisorID']
                    student.advisor = self.get_advisor_by_user_id(advisor_id)
                    break

    def get_advisor_by_user_id(self, user_id):
        file_path = "Iteration_2/src/main/java/parameters.json"

        with open(file_path, 'r') as reader:
            json_data = json.load(reader)

            for advisor_json in json_data.get('advisors', []):
                if advisor_json['userID'] == user_id:
                    name = advisor_json['name']
                    surname = advisor_json['surname']
                    date = advisor_json['birthdate']
                    birthdate = datetime.strptime(date, "%Y-%m-%d")
                    gender = advisor_json['gender'][0]
                    ssn = advisor_json['ssn']

                    students = [
                        self.get_student_by_id_without_advisor(student_id)
                        for student_id in advisor_json.get('students', [])
                    ]

                    courses = [
                        course for course in self.courses
                        if course.course_id in advisor_json.get('courses', [])
                    ]

                    return Advisor(name, surname, birthdate, gender, ssn, courses, students)

        print(f"Advisor with userID: {user_id} not found.")
        return None

    def save_course_sections_of_data(self, course_json, course_sections, section_type):
        course_sections_data = []
        for course_section in course_sections:
            section_data = {
                "courseID": course_section.parent_course.course_id,
                "sectionID": course_section.section_id
            }
            course_sections_data.append(section_data)
        course_json[section_type] = course_sections_data

    def fill_course_data(self, course_json, courses, course_list_type):
        completed_courses = []
        for course in courses:
            course_data = {
                "courseID": course.course_id,
                "courseName": course.course_name,
                "credits": course.credits
            }
            if course_list_type == "completedCourses":
                course_data["grade"] = course.grade
            completed_courses.append(course_data)
        course_json[course_list_type] = completed_courses

    def save_student(self, student):
        file_path = f"Iteration_2/src/main/java/{student.id}.json"

        try:
            with open(file_path, 'w') as writer:
                student_data = {
                    "year": student.transcript.semester
                }
                self.fill_course_data(student_data, student.transcript.completed_courses, "completedCourses")
                self.fill_course_data(student_data, student.transcript.current_courses, "currentCourses")
                self.fill_course_data(student_data, student.transcript.waited_courses, "waitedCourses")
                self.save_course_sections_of_data(student_data, student.transcript.current_sections, "currentSections")
                self.save_course_sections_of_data(student_data, student.transcript.waited_sections, "waitedSections")

                json.dump(student_data, writer)
        except Exception as e:
            print(f"Error writing student data to file: {e}")

    def read_sections_for_students(self, file_path, section_type):
        new_course_sections = []

        with open(file_path, 'r') as reader:
            json_object = json.load(reader)

            section_array = json_object.get(section_type, [])
            for course in section_array:
                parent_course_id = course['courseID']
                section_id = course['sectionID']

                for cs in self.course_sections:
                    if (cs.parent_course.course_id == parent_course_id and
                            cs.section_id == section_id):
                        new_course_sections.append(cs)
                        break

        return new_course_sections

    def read_courses_for_students(self, file_path, course_list_type):
        course_list = []

        with open(file_path, 'r') as reader:
            json_object = json.load(reader)

            course_array = json_object.get(course_list_type, [])
            for course_json in course_array:
                course_id = course_json['courseID']
                course_name = course_json['courseName']
                credits = int(course_json['credits'])

                course_type = next((c.course_type for c in self.courses if c.course_id == course_id), '')

                if course_list_type == "completedCourses":
                    grade = course_json.get('grade')

                    if course_type == "Mandatory":
                        new_course = MandatoryCourse(course_id, course_name, grade, credits)
                    elif course_type == "Technical Elective":
                        new_course = TechnicalElectiveCourse(course_id, course_name, grade, credits)
                    elif course_type == "Non-Technical Elective":
                        new_course = NonTechnicalElectiveCourse(course_id, course_name, grade, credits)
                else:
                    if course_type == "Mandatory":
                        new_course = MandatoryCourse(course_id, course_name, credits)
                    elif course_type == "Technical Elective":
                        new_course = TechnicalElectiveCourse(course_id, course_name, credits)
                    elif course_type == "Non-Technical Elective":
                        new_course = NonTechnicalElectiveCourse(course_id, course_name, credits)

                course_list.append(new_course)

        return course_list

    def create_transcript(self, student_id):
        file_path = f"Iteration_2/src/main/java/{student_id}.json"

        with open(file_path, 'r') as reader:
            json_object = json.load(reader)

            semester = int(json_object['year'])
            completed_courses = self.read_courses_for_students(file_path, "completedCourses")
            current_courses = self.read_courses_for_students(file_path, "currentCourses")
            waited_courses = self.read_courses_for_students(file_path, "waitedCourses")
            current_sections = self.read_sections_for_students(file_path, "currentSections")
            waited_sections = self.read_sections_for_students(file_path, "waitedSections")

            for waited_course in waited_courses:
                for waited_section in waited_sections:
                    if waited_course.course_id == waited_section.parent_course.course_id:
                        waited_course.course_sections.append(waited_section)

            return Transcript(
                completed_courses,
                current_courses,
                waited_courses,
                current_sections,
                waited_sections,
                semester
            )
