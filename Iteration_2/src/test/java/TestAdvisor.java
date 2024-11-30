import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TestAdvisor {

    private Advisor advisor;
    private Student student;
    private Course course;
    private Transcript transcript;
    ArrayList<Course> courses;
    ArrayList<CourseSection> courseSections;
    
    @Before
    public void setUp() {
        // Mock data setup for testing. This sets up the initial data used by the tests.
        courses = new ArrayList<>();
        course = new Course("IE3107" , "Modeling");
        CourseSection courseSection = new CourseSection("IE0503" , 8);
        courseSection.setParentCourse(course);
        courseSections = new ArrayList<>();

        courses.add(course);
        courseSections.add(courseSection);
        course.setCourseSections(courseSections);
        // Creating a new Transcript with empty course lists.
        transcript = new Transcript(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        
        // Creating a sample student with basic data.
        student = new Student("Melisa", "Gezer", new Date(), 'F', transcript, "150120101");
        
        // Adding the student to a list.
        ArrayList<Student> students = new ArrayList<>();
        students.add(student);

        // Creating an advisor and assigning the course and student to the advisor.
        advisor = new Advisor("Mert", "Tufan", new Date(), 'M', "369258147", courses, students);
    }

    @Test
    public void testApproveCourse() {
        // Add the course to the student's waited courses.
        student.getTranscript().getWaitedCourses().addAll(courses);

        // Approve the course for the student through the advisor.
        advisor.approveCourse(student, courseSections.get(0));

        // Assert that the course is moved to the student's current courses list.
        assertTrue("Course should be added to current courses.", student.getTranscript().getCurrentCourses().contains(course));
        // Assert that the course is removed from the waited courses list.
        assertFalse("Course should be removed from waited courses.", student.getTranscript().getWaitedCourses().contains(course));
    }

    @Test
    public void testRejectCourse() {
        // Add the course to the student's waited courses.
        student.getTranscript().getWaitedCourses().add(course);

        // Reject the course for the student through the advisor.
        advisor.rejectCourse(student, course);

        // Assert that the course is removed from the waited courses list.
        assertFalse("Course should be removed from waited courses.", student.getTranscript().getWaitedCourses().contains(course));
    }

    @Test
    public void testAddStudent() {
        // Create a new student to add to the advisor's list.
        Student newStudent = new Student("Nilsu", "Konakli", new Date(), 'F', new Transcript(), "150123036");

        // Add the new student to the advisor's list.
        advisor.addStudent(newStudent);

        // Assert that the new student has been added to the advisor's list of students.
        assertTrue("New student should be added to the advisor's student list.", advisor.getStudents().contains(newStudent));
    }

    @Test
    public void testGetStudents() {
        // Retrieve the list of students from the advisor.
        ArrayList<Student> students = advisor.getStudents();
        
        // Ensure the students list is not null.
        assertNotNull("Students list should not be null.", students);
        // Assert that there is exactly one student in the advisor's list.
        assertEquals("Students list should contain one student.", 1, students.size());
    }
}