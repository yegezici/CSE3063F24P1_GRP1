import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class TestTranscript {

    private Transcript transcript;
    private Course testCourse;
    private Course anotherCourse;

    @Before
    public void setUp() {
        transcript = new Transcript();
        testCourse = new Course("CSE2225", "Data Structures", 7);
        anotherCourse = new Course("MATH1002", "Calculus I", 5);
    }

    //addWaitedCourse Test: Checks that the course has been added to the waited courses list
    @Test
    public void testAddWaitedCourse() {
        transcript.addWaitedCourse(testCourse);
        assertTrue("Course should be added to the waited courses list.", transcript.getWaitedCourses().contains(testCourse));
    }

    //getWaitedCourses Test: Checks that the list of expected courses contains the correct content
    @Test
    public void testGetWaitedCourses() {
        transcript.addWaitedCourse(testCourse);
        transcript.addWaitedCourse(anotherCourse);

        List<Course> expectedCourses = new ArrayList<>();
        expectedCourses.add(testCourse);
        expectedCourses.add(anotherCourse);

        assertEquals("The waited courses list should contain all added courses.", expectedCourses, transcript.getWaitedCourses());
    }

    //removeWaitedCourse Test: Checks that the course has been deleted from the list of expected courses
    @Test
    public void testDeleteFromWaitedCourse() {
        //First add course to waited course list
        transcript.addWaitedCourse(testCourse);

        //Try to delete the course from the list by calling the method
        transcript.deleteFromWaitedCourse(testCourse);

        //To check if the course has been deleted, the course should not be in the waited courses list
        assertFalse("Waited courses list should no longer contain the removed course.", transcript.getWaitedCourses().contains(testCourse));
    }
}
