import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TestTranscript {

    private Transcript transcript;
    private Course testCourse;
    private Course anotherCourse;

    @Before
    public void setUp() {
        transcript = new Transcript(new ArrayList<Course>(), new ArrayList<Course>(), new ArrayList<Course>());
        testCourse = new Course("IE0503", "Industrial Psychology", 3);
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

     @Test
    public void testAddCourseSections() {
        // Mock data setup
        ArrayList<Course> courses = new ArrayList<>();
        Course course1 = new Course("CSE101", "Data Engineering", 4);
        Course course2 = new Course("IE3107", "Modeling", 8);

        CourseSection section1 = new CourseSection("CSE101.1" , 15);
        CourseSection section2 = new CourseSection("IE3107.2" , 20);

       // Directly manipulate the courseSections list
    ArrayList<CourseSection> courseSections1 = new ArrayList<>();
    courseSections1.add(section1);
    course1.setCourseSections(courseSections1);

    ArrayList<CourseSection> courseSections2 = new ArrayList<>();
    courseSections2.add(section2);
    course2.setCourseSections(courseSections2);

    // Add courses to the list
    courses.add(course1);
    courses.add(course2);

    // Method call
    Transcript transcript = new Transcript(); // Create a new Transcript object
    ArrayList<CourseSection> resultSections = transcript.addCourseSections(courses);

    // Assertions
    assertNotNull("Sections list should not be null", resultSections);
    assertEquals("Sections list should contain 2 sections", 2, resultSections.size());
    assertTrue("Result should contain section1", resultSections.contains(section1));
    assertTrue("Result should contain section2", resultSections.contains(section2));
}

@Test
public void testCalculateGpa() {
    // Mock data setup
    ArrayList<Course> completedCourses = new ArrayList<>();
    Course course1 = new Course("CSE101", "Data Engineering", "BA", 4);
    Course course2 = new Course("IE3107", "Modeling", "CB", 8);
    Course course3 = new Course("CSE0503", "Industrial Psychology", "AA", 5);

    completedCourses.add(course1);
    completedCourses.add(course2);
    completedCourses.add(course3);

    transcript.setCompletedCourses(completedCourses);

    // Method call
    double gpa = transcript.calculateGpa();

    // Assertions
    assertEquals("GPA should be calculated correctly", 3.17, gpa, 0.1);
}

}
