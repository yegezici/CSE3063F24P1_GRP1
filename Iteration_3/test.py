import sys
import os

# Projenizin ana dizinini (CSE3063F24P1_GRP1) sys.path'e ekleyin
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))

# Kontrol için yazdırabilirsiniz:
print("Güncellenmiş sys.path:", sys.path)

from datetime import date
from Student import Student
from Iteration_3.StudentInterface import StudentInterface
from Iteration_3.Course import Course
from Iteration_3.Transcript import Transcript
from Iteration_3.CourseSection import CourseSection
from Iteration_3.TimeSlot import TimeSlot
from Iteration_3.CourseRegistration import CourseRegistration

dummy_transcript = Transcript()
dummy_transcript.set_semester(1)
dummy_student = Student(name="Hasan", surname="Erz", birthdate=date(2000, 1, 1), gender="M", student_id="150121031",
                        transcript=dummy_transcript)
dummy_course = Course(course_id="MATH1001", course_name="calculus", credits=6, prerequisite_course=None, semester=1,
                      grade="AA")
dummy_course2 = Course(course_id="MATH1002", course_name="calculus2", credits=6, prerequisite_course=dummy_course,
                       semester=1, grade="AA")
dummy_course3 = Course(course_id="CSE1200", course_name="programming", credits=6, prerequisite_course=None, semester=1,
                       grade=None)
dummy_courseSection = CourseSection(section_id="MATH1001.1", lecturer=None, capacity=20, parent_course=dummy_course2)
dummy_courseSection2 = CourseSection(section_id="CSE1200.1", lecturer=None, capacity=20, parent_course=dummy_course2)
dummy_timeSlot = TimeSlot(day="Monday", time_interval="14.00 - 14.50", classroom="M2-Z11")
dummy_timeSlot2 = TimeSlot(day="Monday", time_interval="15.00 - 15.50", classroom="M2-Z11")
dummy_classroom = "M2-Z11"
timeSlotList = [dummy_timeSlot, dummy_timeSlot2]
dummy_courseSection.set_time_slots(timeSlotList)
# StudentInterface nesnesi oluştur
dummy_student.get_transcript().add_completed_course(course=dummy_course)
dummy_course2.set_course_sections([dummy_courseSection, dummy_courseSection2])

dummy_student.get_transcript().add_current_section(dummy_courseSection)
interface = StudentInterface(student=dummy_student)
studentList = [dummy_student]
courseList = [dummy_course, dummy_course2, dummy_course3]
courseSectionList = [dummy_courseSection, dummy_courseSection2]
classrooms = [dummy_classroom]
print(dummy_student.get_id())
course_reg = CourseRegistration(students=studentList, courses=courseList, course_sections=courseSectionList,
                                classrooms=classrooms)

# Menü gösterimi
course_reg.init()
