import unittest
from datetime import date
from Advisor import Advisor 
from Student import Student  
from Course import Course
from MandatoryCourse import MandatoryCourse
from CourseSection import CourseSection  
from Transcript import Transcript  
from TimeSlot import TimeSlot

#class Transcript:
#    def __init__(self):
 #       self.current_courses = []
 #       self.waited_courses = []
        
class TestAdvisor(unittest.TestCase):
    
    def setUp(self):
        
        self.courses = []
        self.current_courses = []  
        self.waited_courses = [] 
        self.course = MandatoryCourse("IE3107", "Modeling", 8)
        self.course_section = CourseSection("IE3107.1", 8)
        self.course_section.parent_course = self.course
        self.course_sections = []

        self.courses.append(self.course)
        self.course_sections.append(self.course_section)
        self.course.course_sections = self.course_sections

        self.transcript = Transcript([], [], [])
        self.student = Student("Melisa", "Gezer", date.today(), 'F', self.transcript, "150120101")
        self.students = [self.student]

        self.advisor = Advisor("Mert", "Tufan", date.today(), 'M', "369258147", self.courses, self.students)
        
    def test_approve_course(self):
        self.student.transcript.waited_courses.extend(self.courses)
        self.advisor.approve_course(self.student, self.course_sections[0])

       # self.assertIn(self.course, self.student.transcript.current_courses, "Course should be added to current courses.")
        #self.assertNotIn(self.course, self.student.transcript.waited_courses, "Course should be removed from waited courses.")

    def test_reject_course(self):
        self.student.transcript.waited_courses.append(self.course)
        self.advisor.reject_course(self.student, self.course)

        self.assertNotIn(self.course, self.student.transcript.waited_courses, "Course should be removed from waited courses.")

    def test_add_student(self):
        new_student = Student("Nilsu", "Konakli", date.today(), 'F', Transcript(), "150123036")
        self.advisor.add_student(new_student)

        self.assertIn(new_student, self.advisor.get_students(), "New student should be added to the advisor's student list.")

    def test_get_students(self):
        students = self.advisor.get_students()

        self.assertIsNotNone(students, "Students list should not be null.")
        self.assertEqual(len(students), 1, "Students list should contain one student.")

    def test_check_section_conflict(self):
        existing_time_slot = TimeSlot("Monday", "09:30-10:20", "M2Z11")
        existing_section = CourseSection("CSE101.1", 30)
        existing_section.time_slots.append(existing_time_slot)
        self.student.transcript.add_current_section(existing_section)

        new_time_slot = TimeSlot("Monday", "10:30-11:20", "M2Z12")
        new_section = CourseSection("CSE102.1", 30)
        new_section.time_slots.append(new_time_slot)

        result_no_conflict = self.advisor.check_section_conflict(self.student, new_section)
        self.assertTrue(result_no_conflict, "Sections should not conflict.")

        conflicting_time_slot = TimeSlot("Monday", "09:30-10:20", "M2Z14")
        new_section.time_slots.clear()
        new_section.time_slots.append(conflicting_time_slot)

        result_conflict = self.advisor.check_section_conflict(self.student, new_section)
        self.assertFalse(result_conflict, "Sections should conflict.")


if __name__ == '__main__':
   unittest.main()