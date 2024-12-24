import unittest
from datetime import date
import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)

# Import necessary classes
from Advisor import Advisor
from Student import Student
from Course import Course
from CourseSection import CourseSection
from Transcript import Transcript
from Lecturer import Lecturer
from TimeSlot import TimeSlot

class TestAdvisor(unittest.TestCase):
    
    def setUp(self):
        """
        Set up the test environment by creating instances of Course, Student, Advisor, etc.
        This function runs before every individual test.
        """
        # Lecturer, course, and course section setup
        lecturer = Lecturer("Dr.", "Smith", date(1975, 6, 15), "M", "123456789")
        self.course = Course("IE3107", "Modeling", 8)
        self.course_section = CourseSection(8, self.course, lecturer, "IE3107.1")
        self.course.set_course_sections([self.course_section])

        # Transcript and student setup
        self.transcript = Transcript([], [], [])
        self.student = Student("Melisa", "Gezer", date(2000, 1, 1), 'F', self.transcript, "150120101")

        # Advisor setup
        self.advisor = Advisor("Mert", "Tufan", date(1980, 1, 1), 'M', "369258147", [self.course], [self.student])

    def test_add_student(self):
        """
        Test if a new student is successfully added to the advisor's student list.
        """
        new_student = Student("Nilsu", "Konakli", date(2002, 5, 20), 'F', Transcript(), "150123036")
        self.advisor.add_student(new_student)
        # Assert that the new student has been added to the advisor's student list
        self.assertIn(new_student, self.advisor.get_students(), "New student was not added to the advisor's student list.")

    def test_reject_course(self):
        """
        Test if a course is successfully removed from the student's waited courses when rejected by the advisor.
        """
        self.student.get_transcript().add_waited_course(self.course)
        self.advisor.reject_course(self.student, self.course_section)
        # Assert that the course was removed from the student's waited courses
        self.assertNotIn(self.course, self.student.get_transcript().get_waited_courses(), "Course was not removed from waited courses.")

    def test_get_students(self):
        """
        Test if the list of students is retrieved correctly.
        """
        students = self.advisor.get_students()
        # Assert that the student list is not None and contains exactly one student
        self.assertIsNotNone(students, "The student list should not be None.")
        self.assertEqual(len(students), 1, "The advisor should have exactly one student.")

    def test_check_section_conflict(self):
        """
        Test if section time conflicts are correctly detected for the student.
        """
        time_slot1 = TimeSlot("Monday", "09:30-10:20", "M2Z11")
        time_slot2 = TimeSlot("Monday", "10:30-11:20", "M2Z12")
        self.course_section.set_time_slots([time_slot1])

        # Create a new section and assign a different time slot
        new_section = CourseSection(8, self.course, self.advisor, "IE3107.2")
        new_section.set_time_slots([time_slot2])
        self.student.get_transcript().add_current_section(self.course_section)

        # Test no time conflict
        no_conflict = self.advisor.check_section_conflict(self.student, new_section)
        self.assertTrue(no_conflict, "There should be no time conflict.")

        # Set the new section to have the same time slot and test for conflict
        new_section.set_time_slots([time_slot1])
        conflict = self.advisor.check_section_conflict(self.student, new_section)
        self.assertFalse(conflict, "A time conflict was not detected.")

if __name__ == '__main__':
    # Run the tests with verbosity level 2 for detailed output
    unittest.main() 
 
