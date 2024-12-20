import sys
import os

# Projenizin ana dizinini (CSE3063F24P1_GRP1) sys.path'e ekleyin
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))


# Kontrol için yazdırabilirsiniz:
print("Güncellenmiş sys.path:", sys.path)
from Iteration_3.Advisor import Advisor
from datetime import date
from Student import Student
from Iteration_3.StudentInterface import StudentInterface
from Iteration_3.Course import Course
from Iteration_3.Transcript import Transcript
from Iteration_3.CourseSection import CourseSection
from Iteration_3.TimeSlot import TimeSlot
from Iteration_3.CourseRegistration import CourseRegistration
from Iteration_3.Admin import Admin

dummy_transcript = Transcript()
dummy_transcript.set_semester(1)
dummy_student = Student(name="Hasan", surname="Erz", birthdate=date(2000, 1, 1), gender="M", student_id="150121031",
                        transcript=dummy_transcript)
dummy_student2 = Student(name="Alperen", surname="Denen Kisi", birthdate=date(2001, 1, 1), gender="M", student_id="150121035",
                        transcript=dummy_transcript)                        
dummy_advisor = Advisor(name="Murat",surname="Ganiz",birthdate=date(1970,1,1),gender="m",ssn="123456",courses=None,students=[dummy_student])
dummy_course = Course(course_id="MATH1001", course_name="calculus", credits=6, prerequisite_course=None, semester=1,
                      grade="AA")
dummy_course2 = Course(course_id="MATH1002", course_name="calculus2", credits=6, prerequisite_course=dummy_course,
                       semester=1, grade="AA")
dummy_course3 = Course(course_id="CSE1200", course_name="programming", credits=6, prerequisite_course=None, semester=1,
                       grade=None)
dummy_courseSection = CourseSection(section_id="MATH1001.1", lecturer=None, capacity=20, parent_course=dummy_course2)
dummy_courseSection2 = CourseSection(section_id="MATH1001.2", lecturer=None, capacity=20, parent_course=dummy_course2)
dummy_timeSlot = TimeSlot(day="Monday", time_interval="14.00 - 14.50", classroom="M2-Z11")
dummy_timeSlot2 = TimeSlot(day="Monday", time_interval="15.00 - 15.50", classroom="M2-Z11")
dummy_classroom = "M2-Z11"
timeSlotList = [dummy_timeSlot]
timeSlotList2 = [dummy_timeSlot2]
dummy_courseSection.set_time_slots(timeSlotList)
dummy_courseSection2.set_time_slots(timeSlotList2)
# StudentInterface nesnesi oluştur
dummy_student.get_transcript().add_completed_course(course=dummy_course)

dummy_course2.set_course_sections([dummy_courseSection2, dummy_courseSection])
sections = dummy_course2.get_course_sections()
print("Sections:", sections)
for index, section in enumerate(sections, start=1):
    print(f"Section {index}: {section}")


dummy_student.get_transcript().add_current_section(dummy_courseSection)
interface = StudentInterface(student=dummy_student)
studentList = [dummy_student]
courseList = [dummy_course, dummy_course2, dummy_course3]
courseSectionList = [dummy_courseSection, dummy_courseSection2]
classrooms = [dummy_classroom]

admin = Admin(students=studentList, advisors=[dummy_advisor], lecturers=None, department_schedulers=None)
admin.add_student(dummy_student2)
admin.add_advisor(dummy_advisor)
admin.add_lecturer(dummy_advisor)
for student in admin.students:
    print(student)
admin.delete_student(dummy_student2)
for student in admin.students:
    print(student)
course_reg = CourseRegistration(students=studentList, courses=courseList, course_sections=courseSectionList,
                                classrooms=classrooms,advisors=[dummy_advisor])

# Menü gösterimi
course_reg.init()
