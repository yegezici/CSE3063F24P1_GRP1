import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TestTimeSlot {
    private CourseSection courseSection;
    private TimeSlot timeSlot;

    @Before
    public void setUp() {
        courseSection = new CourseSection("CSE101", 15);
        timeSlot = new TimeSlot("09:30-10:20", "M2Z11");
    }

    @Test
    public void testAssignTimeSlot() {
        // Call the assignTimeSlot method
        timeSlot.assignTimeSlot(courseSection);

        // Verify that the time slot was added to the course section's timeSlots list
        ArrayList<TimeSlot> assignedTimeSlots = courseSection.getTimeSlots();
        assertTrue("The time slot should be added to the course section.", 
            assignedTimeSlots != null && assignedTimeSlots.contains(timeSlot));
    }
}
