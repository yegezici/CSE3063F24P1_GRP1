import unittest
import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)

from TimeSlot import TimeSlot
from CourseSection import CourseSection
from Lecturer import Lecturer
from Course import Course

class TestTimeSlot(unittest.TestCase):

    def setUp(self):
         # Create Course and Lecturer objects
        self.test_course = Course(course_id="CSE2225", course_name="Data Structures", credits=7)
        self.test_lecturer = Lecturer(name="Maraz", surname="Ali")  # Create a lecturer
        
        """Set up the necessary objects before each test."""
        self.course_section = CourseSection(section_id="CSE2225.1", capacity=8, lecturer=self.test_lecturer, parent_course=self.test_course)
        self.time_slot = TimeSlot(day="Monday", time_interval="09:30-10:20", classroom="M2Z11")

    def test_assign_time_slot(self):
        """Test if a TimeSlot is successfully assigned to a CourseSection."""
        # Assign the timeSlot to the courseSection
        self.time_slot.assign_time_slot(self.course_section)

        # Retrieve the list of time slots assigned to the course section
        assigned_time_slots = self.course_section.get_time_slots()

        # Check if the timeSlot was added to the courseSection's timeSlots list
        self.assertIn(self.time_slot, assigned_time_slots, "Time slot should be added to the course section.")
        self.assertEqual(len(assigned_time_slots), 1, "There should be exactly one time slot assigned to the course section.")
 
if __name__ == "__main__":
    unittest.main()
 
