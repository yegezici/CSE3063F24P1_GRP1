import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TestDepartmentScheduler {

    private DepartmentScheduler departmentScheduler;
    private CourseSection courseSection;
    private TimeSlot timeSlot;
    private Lecturer lecturer;
    private Student student;
    private Transcript transcript;
    private ArrayList<Student> waitList;

    private CourseSection courseSection1;
    private CourseSection courseSection2;
    private CourseSection courseSection3;
    private TimeSlot timeSlot1;
    private TimeSlot timeSlot2;
    private TimeSlot timeSlot3;

    @Before
    public void setUp() {
        waitList = new ArrayList<>();
        student = new Student("Melisa", "Gezer", new Date(), 'F', new Transcript(), "150120101");
        waitList.add(student);
        departmentScheduler = new DepartmentScheduler();  
        courseSection = new CourseSection("CSE101", 15);
        courseSection.setWaitList(waitList);
        timeSlot = new TimeSlot("09:30-10:20", "M2Z11");
        lecturer = new Lecturer("Dylan", "Obrien");
    }

    @Test
    public void testAssignTimeSlotToSection() {
        departmentScheduler.assignTimeSlotToSection(courseSection, timeSlot);

        assertTrue("TimeSlot should be added to the CourseSection's timeSlots list.", courseSection.getTimeSlots() != null && courseSection.getTimeSlots().contains(timeSlot));
    }

    @Test
    public void testAssignLecturerToSection() {
        
        departmentScheduler.assignLecturerToSection(courseSection, lecturer);

        assertEquals("Lecturer should be correctly assigned to the CourseSection." , lecturer, courseSection.getLecturer());
    }

    @Test
    public void testManageCapacity(){
        int newCapacity = 40;
        departmentScheduler.manageCapacity(courseSection, newCapacity);

        assertEquals("Capacity should be updated to new value." , newCapacity, courseSection.getCapacity());
    }

    @Test
    public void testManageWaitlist() {
        int size = 1; 
        departmentScheduler.manageWaitlist(courseSection, size);
       
        assertEquals("Waitlist size should be reduced by the processed students." , 0, courseSection.getWaitList().size());
    }

    @Test
    public void testHandleTimeConflict() {
        departmentScheduler = new DepartmentScheduler();

        // Create some time slots
        timeSlot1 = new TimeSlot("09:00-10:00", "Room A");
        timeSlot2 = new TimeSlot("10:00-11:00", "Room B");
        timeSlot3 = new TimeSlot("11:00-12:00", "Room C");

        // Create CourseSections and assign time slots directly to the timeSlots list
        courseSection1 = new CourseSection("CSE101-1", 30);
        ArrayList<TimeSlot> timeSlots1 = new ArrayList<>();
        timeSlots1.add(timeSlot1);
        timeSlots1.add(timeSlot2);
        courseSection1.setTimeSlot(timeSlots1);

        courseSection2 = new CourseSection("CSE102-1", 30);
        ArrayList<TimeSlot> timeSlots2 = new ArrayList<>();
        timeSlots2.add(timeSlot2);
        timeSlots2.add(timeSlot3);
        courseSection2.setTimeSlot(timeSlots2);

        courseSection3 = new CourseSection("CSE103-1", 30);
        ArrayList<TimeSlot> timeSlots3 = new ArrayList<>();
        timeSlots3.add(timeSlot1);
        courseSection3.setTimeSlot(timeSlots3);

        // Create a list of CourseSections for the same semester
        ArrayList<CourseSection> semesterCourses = new ArrayList<>();
        semesterCourses.add(courseSection1);
        semesterCourses.add(courseSection2);
        semesterCourses.add(courseSection3);

        // Call handleTimeConflict method to check for available time slots
        ArrayList<String> availableTimeIntervals = departmentScheduler.handleTimeConflict(semesterCourses);

        // Check if "09:00-10:00" is removed (time conflict with courseSection1 and courseSection3)
        assertFalse(availableTimeIntervals.contains("09:00-10:00"));

        // Check if "10:00-11:00" is removed (time conflict with courseSection1 and courseSection2)
        assertFalse(availableTimeIntervals.contains("10:00-11:00"));

        // Check if "11:00-12:00" is removed (time conflict with courseSection2)
        assertFalse(availableTimeIntervals.contains("11:00-12:00"));

        // Check if a non-conflicting time (e.g., "12:00-01:00") is available
        assertTrue(availableTimeIntervals.contains("12:00-01:00"));
    }
}

