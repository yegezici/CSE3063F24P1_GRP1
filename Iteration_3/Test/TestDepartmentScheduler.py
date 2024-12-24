import unittest
from datetime import date
import sys
import os

current_dir = os.path.dirname(os.path.abspath(__file__))
parent_dir = os.path.abspath(os.path.join(current_dir, '..'))
sys.path.append(parent_dir)
 
# Importing the relevant classes for testing
from CourseSection import CourseSection
from DepartmentScheduler import DepartmentScheduler
from TimeSlot import TimeSlot
from Student import Student
from Transcript import Transcript  
 
class TestDepartmentScheduler(unittest.TestCase):

    def setUp(self):
        # Creating necessary objects for the test
        self.wait_list = []  # Initializing an empty waitlist
        self.course_section = CourseSection(section_id="CS101", capacity=30)  # Creating a course section object with a specific ID and capacity
        self.student = Student("Melisa", "Gezer", date.today(), 'F', Transcript(), "150120101")  # Creating a student object
        self.course_section.set_wait_list(self.wait_list)  # Setting the waitlist for the course section
        # Initializing the DepartmentScheduler object with appropriate details
        self.department_scheduler = DepartmentScheduler("John", "Doe", date.today(), 'M', "123-45-6789", [], [])

    def testAssignTimeSlotToSection(self):
        # Creating a TimeSlot object with specific day, time interval, and classroom
        time_slot = TimeSlot(day="Monday", time_interval="10:00 AM - 12:00 PM", classroom="Room 101")
        
        # Assigning the created time slot to the course section
        self.department_scheduler.assign_time_slot_to_section(self.course_section, time_slot)
        
        # Verifying that the time slot was successfully assigned to the course section
        self.assertEqual(self.course_section.get_time_slots(), [time_slot])

if __name__ == '__main__':
    unittest.main()
