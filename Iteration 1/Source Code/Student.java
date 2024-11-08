import java.util.ArrayList;
import java.util.Date;


public class Student extends Person{
	private Transcript transcript;
	private String studentID;
	private Advisor advisor;
	private ArrayList<Course> registeredCourse;
	
	public Student() {
		
	}
	
	public Student(String name, String surname, Date birthdate, char gender, Transcript transcript, Advisor advisor, String studentID) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.advisor = advisor;
        this.transcript = transcript;
        this.studentID = studentID;
        registeredCourse = new ArrayList<>(); 
    }
	
	public void registerCourse(Course course) {
		transcript.addWaitedCourse(course);
		
	}

	public Transcript getTranscript() {
		return transcript;
	}

	public String getStudentID() {
		return studentID;
	}

	public Advisor getAdvisor() {
		return advisor;
	}
	
}
