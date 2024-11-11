import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;

public class TestCourseRegistration {

    ArrayList<Course> courses;
    Transcript transcript;

    @Before
    public void setUp() {
        // Mock data setup for testing
        transcript = new Transcript(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        
        courses = new ArrayList<>();
        
    }

    @Test
    public void testLoadCourses() {
        // Test if the courses are loaded correctly.
        ArrayList<Course> loadedCourses = CourseRegistration.loadCourses();
        
        // Ensure the courses are not null.
        assertNotNull("Courses should be loaded successfully.", loadedCourses);
        
        // Ensure the loaded courses list is not empty.
        assertFalse("Loaded courses list should not be empty.", loadedCourses.isEmpty());
    }

    @Test
    public void testGetAdvisorByUserID() {
        // Test if the advisor is found based on the user ID.
        Advisor advisor = CourseRegistration.getAdvisorByUserID("l123456", courses);
        
        // Ensure that an advisor is found.
        assertNotNull("Advisor should be found for the given user ID.", advisor);
        
        // Ensure that the advisor's user ID matches the provided ID.
        assertEquals("Advisor's user ID should match.", "987654321", advisor.getSsn());
    }

    @Test
    public void testGetStudentByID() {
        // Test if the student is correctly created based on the provided student ID.
        Student student = CourseRegistration.getStudentByID("150121031", courses);
        
        // Ensure that the student is found.
        assertNotNull("Student should be created for the given ID.", student);
        
        // Ensure that the student ID matches the expected ID.
        assertEquals("Student ID should match.", "150121031", student.getStudentID());
    }

    @Test
    public void testAcceptCourseRequest() {
        // Test if a course request is correctly accepted and moved from waited courses to current courses.
        courses = CourseRegistration.loadCourses();
        Student student = CourseRegistration.getStudentByID("150121031", courses);
        Course course = courses.get(2);
        
        // Add the course to waited courses before accepting the request.
        CourseRegistration.addWaitedCourse(student, course);

        // Accept the course request and move it to current courses.
        CourseRegistration.acceptCourseRequest(student, course);
        
        // Ensure the course is moved to current courses.
        assertTrue("Course should be moved to current courses.", student.getTranscript().getCurrentCourses().contains(course));
        
        // Ensure the course is removed from waited courses.
        assertFalse("Course should be removed from waited courses.", student.getTranscript().getWaitedCourses().contains(course));
    }

    @Test
    public void testAddWaitedCourse() {
        courses = CourseRegistration.loadCourses();
        // Test if a course is correctly added to the waited courses list for a student.
        Student student = CourseRegistration.getStudentByID("150121031", courses);
        Course course = courses.get(2);
        

        // Add the course to the student's waited courses.
        CourseRegistration.addWaitedCourse(student, course);
        student = CourseRegistration.getStudentByID("150121031", courses);
        // Ensure the course is added to waited courses.
        assertTrue("Course should be added to waited courses.", student.getTranscript().getWaitedCourses().contains(course));
    }
}
