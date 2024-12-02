import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TestStudent {

    private Student student;
    private Transcript transcript;
    private MandatoryCourse testCourse;
    private CourseSection testCourseSection;

    @Before
    public void setUp() {
        // Set up mock data for testing before each test.
        transcript = new Transcript(new ArrayList<Course>(), new ArrayList<Course>(), new ArrayList<Course>());  
        student = new Student("Melisa", "Gezer", new Date(), 'F', transcript, "150120101");
        testCourse = new MandatoryCourse("CSE2225", "Data Structures", 7);  
        testCourseSection = new CourseSection("CSE2225.1" , 8);
        testCourseSection.setParentCourse(testCourse);
    }

    @Test
    public void testRegisterCourseAddsCourseToTranscript() {
        // Test if registering a course properly adds it to the student's transcript.
        student.registerCourse(testCourseSection);

        // Assert that the course is correctly added to the transcript's waited courses list.
        assertTrue("Course should be added to the student's waited courses list in the transcript.", 
                   transcript.getWaitedCourses().contains(testCourse));
    }


}
