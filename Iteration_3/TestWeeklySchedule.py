import unittest
from io import StringIO
import sys
from unittest.mock import MagicMock
from WeeklySchedule import WeeklySchedule
from Student import Student
from CourseSection import CourseSection
from TimeSlot import TimeSlot
from Course import Course
from Lecturer import Lecturer

class TestWeeklySchedule(unittest.TestCase):

    def setUp(self):
        # Mock Student
        self.student_mock = MagicMock(Student)

        # Mock Transcript ve metotları
        self.transcript_mock = MagicMock()
        self.student_mock.get_transcript = self.transcript_mock
        
        # Mock current_courses
        self.current_courses_mock = [Course("Course1", "CS101"), Course("Course2", "CS102")]
        self.transcript_mock.get_current_courses.return_value = self.current_courses_mock
        
        # Mock Lecturer
        self.lecturer_mock = MagicMock(Lecturer)

        # CourseSection mock nesnelerini oluşturuyoruz
        self.current_sections_mock = [
            MagicMock(CourseSection),
            MagicMock(CourseSection)
        ]
        
        # CourseSection'ların özelliklerini ayarlıyoruz
        for i, section in enumerate(self.current_sections_mock):
            section.get_time_slots.return_value = [TimeSlot("10:00-12:00", "Monday", "Room 101")]
            section.get_parent_course.return_value = Course(f"Course{i+1}", f"CS{i+101}")
            section.get_section_id.return_value = i + 1

        # Transcript mock metodunu ayarlıyoruz
        self.transcript_mock.get_current_sections.return_value = self.current_sections_mock
        
        # Set up WeeklySchedule with mocked student
        self.weekly_schedule = WeeklySchedule(self.student_mock)

    def test_print_student_weekly_schedule(self):
        # Output'u yakalamak için sys.stdout'u StringIO ile değiştiriyoruz
        captured_output = StringIO()
        sys.stdout = captured_output

        # `print_student_weekly_schedule` metodunu çağırıyoruz
        self.weekly_schedule.print_student_weekly_schedule()

        # Beklenen çıktıları kontrol ediyoruz
        self.assertIn("2 courses taken this term.", captured_output.getvalue())
        self.assertIn("Monday:", captured_output.getvalue())
        self.assertIn("Course1  Room 101  10:00-12:00", captured_output.getvalue())

        # sys.stdout'u eski haline getiriyoruz
        sys.stdout = sys.__stdout__

if __name__ == "__main__":
    unittest.main(verbosity=2)
