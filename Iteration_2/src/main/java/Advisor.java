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
//constructor
    public Advisor(String name, String surname, Date birthdate, char gender, String ssn, ArrayList<Course> courses, ArrayList<Student> students){
        super(name, surname, birthdate, gender, ssn, courses);
        this.students = students;
    }
//This method adds the course approved by the advisor to the list of courses the student is currently taking and deletes it from the list of those waiting for approval.
    public void approveCourse(Student student, Course course) {
        try {
            student.getTranscript().addCurrentCourse(course);
            student.getTranscript().deleteFromWaitedCourse(course);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
//This method removes the course from the student's waiting list if the advisor does not approve it.
    public void rejectCourse(Student student, Course course) {
        try {
            student.getTranscript().deleteFromWaitedCourse(course);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
//This method adds a new student object to the list of students that the advisor is an advisor for.
    public void addStudent(Student student) {
        try {
            students.add(student);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
//Retrieves the list of students advised by this advisor
    public ArrayList<Student> getStudents() {
        return students;
    }


}
