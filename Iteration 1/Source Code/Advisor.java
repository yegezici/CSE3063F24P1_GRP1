import java.util.ArrayList;
import java.util.Date;

public class Advisor extends Lecturer {
    private ArrayList<Student> students;

    public Advisor() {
        super();
    }
    public Advisor(String name, String surname){
      super(name,surname);
    }

    public Advisor(String name, String surname, Date birthdate, char gender){
        super(name, surname, birthdate, gender);
    }

    public void approveCourse(Student student, Course course) {
        try {
            student.getTranscript().addCurrentCourse(course);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addStudent(Student student) {
        try {
            students.add(student);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
