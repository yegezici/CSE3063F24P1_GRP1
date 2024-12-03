import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TestDepartmentScheduler {

    private DepartmentScheduler departmentScheduler;
    private CourseSection courseSection;
    private TimeSlot timeSlot;
    private Student student;
    private ArrayList<Student> waitList;
    private Course course;
    private CourseSection courseSection1;

    @Before
    public void setUp() {
        // Initialize the waitlist and add a test student
        waitList = new ArrayList<>();
        student = new Student("Melisa", "Gezer", new Date(), 'F', new Transcript(), "150120101");
        waitList.add(student);
        ArrayList<String> classrooms = new ArrayList<>();

        // Initialize available classrooms for the DepartmentScheduler
        classrooms.add("M2Z11"); 
        classrooms.add("M3Z12");
        classrooms.add("M2Z04");
        departmentScheduler = new DepartmentScheduler("", "", new ArrayList<>(), classrooms); 
        course = new MandatoryCourse("IE3107", "Probability", 6);
        courseSection = new CourseSection("CSE101", 15);
        courseSection.setWaitList(waitList);
        timeSlot = new TimeSlot("Monday","09:30-10:20", "M2Z11");
    }

    @Test
    public void testAssignTimeSlotToSection() {
        departmentScheduler.assignTimeSlotToSection(courseSection, timeSlot);

        // Assert that the TimeSlot is correctly added to the CourseSection
        assertTrue("TimeSlot should be added to the CourseSection's timeSlots list.", courseSection.getTimeSlots() != null && courseSection.getTimeSlots().contains(timeSlot));
    }

    @Test
    public void testManageCapacity(){
        int newCapacity = 40;
        // Test updating the capacity of a CourseSection
        departmentScheduler.manageCapacity(courseSection, newCapacity);

        // Assert that the capacity is updated correctly
        assertEquals("Capacity should be updated to new value.", newCapacity, courseSection.getCapacity());
    }

    @Test
    public void testManageWaitlist() {
        int size = 1; 
         // Test managing the waitlist of a CourseSection
         departmentScheduler.manageWaitlist(courseSection, size);

         // Assert that the waitlist size is reduced correctly
        assertEquals("Waitlist size should be reduced by the processed students." , 0, courseSection.getWaitList().size());
    }

    @Test
    public void testHandleClassroomConflict() {
        
        ArrayList<TimeSlot> timeSlots = new ArrayList<>();
        TimeSlot timeSlot1 = new TimeSlot("Monday","09:30-10:20", "M2Z11");
        TimeSlot timeSlot2 = new TimeSlot("Monday","10:30-11:20", "M3Z12");
        timeSlots.add(timeSlot1);
        timeSlots.add(timeSlot2);

        // Create and assign the TimeSlots to a CourseSection
        courseSection1 = new CourseSection("1", 10 , course);
        courseSection1.setTimeSlot(timeSlots);
    
        // Test handling classroom conflicts for a specific time interval
        ArrayList<String> availableClassrooms = departmentScheduler.handleClassroomConflict("Monday","09:30-10:20");

        // Assert that the classroom list reflects the conflict handling
        assertTrue(availableClassrooms.contains("M2Z11"));
    }
    
}