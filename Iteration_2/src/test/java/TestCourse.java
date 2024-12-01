import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TestCourse {

    private Course course;
    private Student student;
    private Lecturer lecturer;
    private Course prerequisiteCourse;
    private Transcript transcript;

    @Before
    public void setUp() {
        // Initialize the Course object
        course = new Course("CSE2225", "Data Structures", 7);

        // Initialize the student list and add it to the course
        ArrayList<Student> studentList = new ArrayList<>();
        course.setStudents(studentList);

        // Initialize the Student object
        student = new Student("Melisa", "Gezer", new Date(), 'F', transcript, "150120101");

        // Initialize the prerequisite course
        prerequisiteCourse = new Course("MATH1002", "Calculus I", 5);
    }

    @Test
    public void testAddStudent() {
        // Test if a student can be successfully added to the course.
        course.addStudent(student);
        
        // Assert that the student has been added to the course's student list.
        System.out.println(course == null);
        System.out.println(student == null);
        assertTrue("Student should be added to the course.", course.getStudents().contains(student));
    }

    @Test
    public void testGetCourseId() {
        // Test if the correct course ID is returned by the method.
        assertEquals("Course ID should be CSE2225", "CSE2225", course.getCourseId());
    }

    @Test
    public void testSetAndGetPrerequisiteCourse() {
        // Test if the prerequisite course can be set and retrieved correctly.
        course.setPrerequisiteCourse(prerequisiteCourse);
        
        // Assert that the prerequisite course is correctly set.
        assertEquals("Prerequisite course should be MATH1002", prerequisiteCourse, course.getPrerequisiteCourse());
    }

    @Test
    public void testGetCredits() {
        // Test if the correct number of credits is returned for the course.
        assertEquals("The course should have 7 credits.", 7, course.getCredits());
    }
}
