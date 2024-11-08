import java.util.List;
import java.util.ArrayList;
import java.util.Date;


public class Student extends Person{
	private Transcript transcript;
	private String studentID;
	private Advisor advisor;
	private List<Course> registeredCourse;
	
	
	public Student(String name, String surname, Date birthdate, char Gender, String studentID) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.Gender = Gender;
        this.advisor = advisor;
        this.transcript = transcript;
        this.studentID = studentID;
        registeredCourse = new ArrayList<>(); 
    }
	
	public void registerCourse(Course course) {
	
		
	}

	public Transcript getTranskript() {
		return transcript;
	}

	public String getStudentID() {
		return studentID;
	}

	public Advisor getAdvisor() {
		return advisor;
	}
	
}
