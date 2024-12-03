import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TestStudentInterface {
    private StudentInterface studentInterface; // The class being tested
    private Student student; // A sample student for testing
    private ArrayList<Course> courses; // List of courses for testing registration and prerequisites

    @Before
    public void setUp() {
        // Initialize a Transcript for the student
        Transcript transcript = new Transcript(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // Create a student instance for testing
        student = new Student("Melisa", "Gezer", new Date(), 'F', transcript, "150120101");

        // Create a list of courses with prerequisite relationships
        courses = new ArrayList<>();
        Course course1 = new MandatoryCourse("IE3107", "Probability", 6); // A course with no prerequisites
        Course course2 = new MandatoryCourse("IE3108", "Modeling", 5); // A course requiring course1 as a prerequisite
        course1.setPrerequisiteCourse(null); 
        course2.setPrerequisiteCourse(course1); // Set course1 as the prerequisite for course2
        courses.add(course1);
        courses.add(course2);

        // Initialize the StudentInterface with the student and course list
        studentInterface = new StudentInterface(student, courses);
    }

    @Test
    public void testShowRegisterableCourses() {
        // Test to show courses the student can register for
        ArrayList<Course> registerableCourses = studentInterface.showRegisterableCourses();

        // Initially, the student should only see courses without prerequisites
        assertEquals("Registerable courses list should contain all courses initially.", 1, registerableCourses.size());

        // Simulate the student completing the first course
        student.getTranscript().getCompletedCourses().add(courses.get(0)); 

        // Verify that the remaining course becomes registerable
        registerableCourses = studentInterface.showRegisterableCourses();
        assertEquals("Registerable courses list should contain MATH101 after prerequisite is met.", 1, registerableCourses.size());
        assertEquals("The remaining course should be IE3108.", "IE3108", registerableCourses.get(0).getCourseId());
    }

    @Test
    public void testCheckPrerequisite() {
        // Test to verify prerequisite checking functionality
        Course mathCourse = courses.get(1); // A course requiring prerequisites
        Course programmingCourse = courses.get(0); // A course without prerequisites

        // Initially, prerequisites for mathCourse should not be met
        assertFalse("Prerequisite for IE3107 should not be met initially.", 
            studentInterface.checkPrerequisite(mathCourse));

        // Simulate completing the prerequisite course
        student.getTranscript().getCompletedCourses().add(programmingCourse); 

        // Verify that the prerequisite is now met
        assertTrue("Prerequisite for MATH101 should be met after completing CS101.", 
            studentInterface.checkPrerequisite(mathCourse));
    }

    @Test
    public void testCheckCourseExistInList() {
        // Test to check if a course exists in a given list
        Course programmingCourse = courses.get(0); 

        // Initially, the course should not exist in the completed courses list
        assertFalse("Course should not exist in the list initially.", 
            studentInterface.checkCourseExistInList(programmingCourse, student.getTranscript().getCompletedCourses()));

        // Add the course to the completed list
        student.getTranscript().getCompletedCourses().add(programmingCourse);

        // Verify that the course now exists in the list
        assertTrue("Course should exist in the list after adding.", 
            studentInterface.checkCourseExistInList(programmingCourse, student.getTranscript().getCompletedCourses()));
    }
}
