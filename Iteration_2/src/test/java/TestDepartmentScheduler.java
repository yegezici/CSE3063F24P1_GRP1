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
    private Lecturer lecturer;
    private Student student;
    private Transcript transcript;
    private ArrayList<Student> waitList;
    private Course course;

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
        course = new MandatoryCourse("IE3107", "Probability", 6);
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
    public void testHandleClassroomConflict() {
        // TimeSlot'ları oluştur ve listeye ekle
        ArrayList<TimeSlot> timeSlots = new ArrayList<>();
        TimeSlot timeSlot1 = new TimeSlot("09:30-10:20", "M2Z11");
        TimeSlot timeSlot2 = new TimeSlot("10:30-11:20", "M3Z12");
        timeSlots.add(timeSlot1);
        timeSlots.add(timeSlot2);

        courseSection1 = new CourseSection("1", 10 , course);
    
        // CourseSection'a ata
        courseSection1.setTimeSlot(timeSlots);
    
        // Test metodu çağrısı
        ArrayList<String> availableClassrooms = departmentScheduler.handleClassroomConflict("09:30-10:20");
    
        // Beklenen sonuçları kontrol et
        assertTrue(availableClassrooms.contains("M2Z11"));
    }
    
}
