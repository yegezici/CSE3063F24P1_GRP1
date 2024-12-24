import unittest
from io import StringIO
from unittest.mock import MagicMock
import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)

from WeeklySchedule import WeeklySchedule
from Student import Student
from CourseSection import CourseSection
from TimeSlot import TimeSlot
from Course import Course

class TestWeeklySchedule(unittest.TestCase):

    def setUp(self):
        """
        Set up the test environment by mocking the necessary objects.
        """
        # Mock Student object.
        self.student_mock = MagicMock(Student)

        # Mock Transcript and its methods.
        self.transcript_mock = MagicMock()
        self.student_mock.get_transcript.return_value = self.transcript_mock

        # Mock current_courses list.
        self.current_courses_mock = [MagicMock(spec=Course), MagicMock(spec=Course)]
        self.current_courses_mock[0].get_course_name.return_value = "Course1"
        self.current_courses_mock[1].get_course_name.return_value = "Course2"
        self.transcript_mock.get_current_courses.return_value = self.current_courses_mock

        # Mock current_sections list (CourseSection objects).
        self.current_sections_mock = [MagicMock(spec=CourseSection), MagicMock(spec=CourseSection)]

        for i, section in enumerate(self.current_sections_mock):
            # Mock TimeSlot for each section.
            mock_time_slot = MagicMock()
            mock_time_slot.get_day.return_value = "Monday"
            mock_time_slot.get_time_interval.return_value = "10:00-12:00"
            mock_time_slot.get_classroom.return_value = "Room 101"

            section.get_time_slots.return_value = [mock_time_slot]  # Assign time slots to the section.
            section.get_parent_course.return_value = self.current_courses_mock[i]  # Associate course with section.
            section.get_section_id.return_value = f"Section{i+1}"  # Set section ID.

        # Set the mocked current sections to the transcript.
        self.transcript_mock.get_current_sections.return_value = self.current_sections_mock

        # Initialize WeeklySchedule with the mocked student.
        self.weekly_schedule = WeeklySchedule(self.student_mock)

    def test_print_student_weekly_schedule(self):
        """
        Test if the weekly schedule is correctly printed for the student.
        """
        # Capture the output by redirecting sys.stdout to StringIO.
        captured_output = StringIO()
        sys.stdout = captured_output

        # Call the `print_student_weekly_schedule` method to generate output.
        self.weekly_schedule.print_student_weekly_schedule()

        # Check if the expected output is in the captured output.
        self.assertIn("2 courses taken this term.", captured_output.getvalue())  # Verifying number of courses.
        self.assertIn("Monday:", captured_output.getvalue())  # Verifying the day of the week.
        self.assertIn("Course1  Room 101  10:00-12:00", captured_output.getvalue())  # Verifying course details.

        # Restore the original sys.stdout.
        sys.stdout = sys.__stdout__
 
if __name__ == "__main__":
    unittest.main() 
