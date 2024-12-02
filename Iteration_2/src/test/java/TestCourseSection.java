import java.util.Date;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TestCourseSection {
    private CourseSection courseSection;
    private Student student;
    private Transcript transcript;

    @Before
    public void setUp() {
        student = new Student("Melisa", "Gezer", new Date(), 'F', new Transcript(), "150120101");
        courseSection = new CourseSection("CSE101", 15);
    }

    @Test
    public void testAddStudentToSection(){
         // Before adding the student, currentStudents should be empty
         assertTrue("Current students list should be empty before adding a student", courseSection.getCurrentStudents().isEmpty());

         // Add the student to the section
         courseSection.addStudentToSection(student);
 
         // After adding the student, currentStudents should contain the student
         assertTrue("Current students list should contain the added student", courseSection.getCurrentStudents().contains(student));

    }

}
