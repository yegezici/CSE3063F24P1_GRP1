from datetime import date
from Student import Student
from StudentInterface import StudentInterface
from Course import Course
from Transcript import Transcript
from CourseSection import CourseSection
from TimeSlot import TimeSlot
from CourseRegistration import CourseRegistration

dummy_transcript = Transcript()
dummy_transcript.set_semester(1)
dummy_student = Student(name="John", surname="Doe", birthdate=date(2000, 1, 1), gender="M", student_id="150121031",transcript=dummy_transcript)
dummy_course = Course(course_id="MATH1001",course_name="calculus",credits=6,prerequisite_course=None,semester=1,grade="AA")
dummy_course2 = Course(course_id="MATH1002",course_name="calculus2",credits=6,prerequisite_course=dummy_course,semester=1,grade="AA")
dummy_course3 = Course(course_id="CSE1200",course_name="programming",credits=6,prerequisite_course=None,semester=1,grade=None)
dummy_courseSection = CourseSection(section_id="MATH1001.1",lecturer=None,capacity=20,parent_course=dummy_course2)
dummy_courseSection2 = CourseSection(section_id="CSE1200.1",lecturer=None,capacity=20,parent_course=dummy_course2)
dummy_timeSlot = TimeSlot(day="Monday",time_interval="14.00 - 14.50",classroom="M2-Z11")
dummy_timeSlot2 = TimeSlot(day="Monday",time_interval="15.00 - 15.50",classroom="M2-Z11")
dummy_classroom = "M2-Z11"
timeSlotList = [dummy_timeSlot,dummy_timeSlot2]
dummy_courseSection.set_time_slots(timeSlotList)
# StudentInterface nesnesi oluştur
dummy_student.get_transcript().add_completed_course(course=dummy_course)
dummy_course2.set_course_sections([dummy_courseSection,dummy_courseSection2])

dummy_student.get_transcript().add_current_section(dummy_courseSection)
interface = StudentInterface(student=dummy_student)
studentList = [dummy_student]
courseList = [dummy_course,dummy_course2,dummy_course3]
courseSectionList = [dummy_courseSection,dummy_courseSection2]
classrooms = [dummy_classroom]
course_reg = CourseRegistration(students=studentList,courses=courseList,course_sections=courseSectionList,classrooms=classrooms)

# Menü gösterimi
course_reg.init()
