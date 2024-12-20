import unittest
from datetime import date
from CourseSection import CourseSection
from DepartmentScheduler import DepartmentScheduler
from TimeSlot import TimeSlot
from Student import Student
from Transcript import Transcript  # Transcript sınıfını da import ettiğinizden emin olun

class TestDepartmentScheduler(unittest.TestCase):

    def setUp(self):
        # Test için gerekli nesneleri oluşturuyoruz
        self.wait_list = []
        #self.course('Japanese' , "JP101" , 5)
        self.course_section = CourseSection(section_id="CS101", capacity=30)
        self.student = Student("Melisa", "Gezer", date.today(), 'F', Transcript(), "150120101")
        self.course_section.set_wait_list(self.wait_list)
        self.department_scheduler = DepartmentScheduler("John", "Doe", [], [])  # DepartmentScheduler nesnesini başlatıyoruz

    def testAssignTimeSlotToSection(self):
        # TimeSlot nesnesi oluşturulmalı
        time_slot = TimeSlot(start="10:00 AM", end="12:00 PM")
        self.department_scheduler.assign_time_slot_to_section(self.course_section, time_slot)
        self.assertEqual(self.course_section.get_time_slots(), [time_slot])

    def testManageCapacity(self):
        new_capacity = 35
        self.department_scheduler.manage_capacity(self.course_section, new_capacity)
        self.assertEqual(self.course_section.get_capacity(), new_capacity)

    def testManageWaitlist(self):
        self.wait_list.append(self.student)  # Test için öğrenci ekliyoruz
        self.course_section.set_wait_list(self.wait_list)
        size_increase = 5
        self.department_scheduler.manage_waitlist(self.course_section, size_increase)
        self.assertIn(self.student, self.course_section.get_current_students())  # Öğrencinin derse eklendiğini kontrol ediyoruz

    def testHandleClassroomConflict(self):
        # Classroom conflict testi için uygun bir implementasyon yapılmalı
        pass

if __name__ == '__main__':
    unittest.main()
