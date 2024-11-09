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

    public Advisor(String name, String surname, Date birthdate, char gender, String ssn, ArrayList<Course> courses, ArrayList<Student> students){
        super(name, surname, birthdate, gender, ssn, courses);
        this.students = students;
    }

    public void approveCourse(Student student, Course course) {
        try {
            student.getTranscript().addCurrentCourse(course);
            student.getTranscript().deleteFromWaitedCourse(course);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void rejectCourse(Student student, Course course) {
        try {
            student.getTranscript().deleteFromWaitedCourse(course);
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

    public ArrayList<Student> getStudents() {
        return students;
    }


}
