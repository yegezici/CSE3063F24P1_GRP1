import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

public class TestStudent {

    private Student student;
    private Transcript transcript;
    private Course testCourse;

    @Before
    public void setUp() {
        // Set up mock data for testing before each test.
        transcript = new Transcript();  
        student = new Student("Melisa", "Gezer", new Date(), 'F', transcript, "150123031", "123456789");
        testCourse = new Course("CSE2225", "Data Structures", 7);  
    }

    @Test
    public void testRegisterCourseAddsCourseToTranscript() {
        // Test if registering a course properly adds it to the student's transcript.
        student.registerCourse(testCourse);

        // Assert that the course is correctly added to the transcript's waited courses list.
        assertTrue("Course should be added to the student's waited courses list in the transcript.", 
                   transcript.getWaitedCourses().contains(testCourse));
    }

    @Test
    public void testRegisterCourseWithNullCourse() {
        // Test if trying to register a null course correctly throws a NullPointerException.
        try {
            student.registerCourse(null);
            fail("Registering a null course should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
