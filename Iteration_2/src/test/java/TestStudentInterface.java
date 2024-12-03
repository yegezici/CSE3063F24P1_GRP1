import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TestStudentInterface {
    private StudentInterface studentInterface;
    private Student student;
    private ArrayList<Course> courses;

    @Before
    public void setUp() {
        Transcript transcript = new Transcript(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        student = new Student("Melisa", "Gezer", new Date(), 'F', transcript, "150120101");

        courses = new ArrayList<>();
        Course course1 = new MandatoryCourse("IE3107", "Probability", 6);
        Course course2 = new MandatoryCourse("IE3108", "Modeling", 5);
        course1.setPrerequisiteCourse(null); 
        course2.setPrerequisiteCourse(course1);
        courses.add(course1);
        courses.add(course2);

        studentInterface = new StudentInterface(student, courses);
    }

    @Test
    public void testShowRegisterableCourses() {
        ArrayList<Course> registerableCourses = studentInterface.showRegisterableCourses();
        assertEquals("Registerable courses list should contain all courses initially.", 1, registerableCourses.size());

        student.getTranscript().getCompletedCourses().add(courses.get(0)); 
        registerableCourses = studentInterface.showRegisterableCourses();
        assertEquals("Registerable courses list should contain MATH101 after prerequisite is met.", 1, registerableCourses.size());
        assertEquals("The remaining course should be IE3108.", "IE3108", registerableCourses.get(0).getCourseId());
    }

    @Test
    public void testCheckPrerequisite() {
        Course mathCourse = courses.get(1); 
        Course programmingCourse = courses.get(0); 

        assertFalse("Prerequisite for IE3107 should not be met initially.", studentInterface.checkPrerequisite(mathCourse));

        student.getTranscript().getCompletedCourses().add(programmingCourse); 
        assertTrue("Prerequisite for MATH101 should be met after completing CS101.", studentInterface.checkPrerequisite(mathCourse));
    }

    @Test
    public void testCheckCourseExistInList() {
        Course programmingCourse = courses.get(0); 
        assertFalse("Course should not exist in the list initially.", studentInterface.checkCourseExistInList(programmingCourse, student.getTranscript().getCompletedCourses()));

        student.getTranscript().getCompletedCourses().add(programmingCourse);
        assertTrue("Course should exist in the list after adding.", studentInterface.checkCourseExistInList(programmingCourse, student.getTranscript().getCompletedCourses()));
    }
}
