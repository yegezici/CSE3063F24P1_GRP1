import java.util.ArrayList;
import java.util.Date;


public class Student extends Person{
	private Transcript transcript;
	private String studentID;
	private Advisor advisor;
	
	public Student() {
		
	}
	
	public Student(String name, String surname, Date birthdate, char gender, Transcript transcript, String studentID, Advisor advisor) {
        super(name, surname, birthdate, gender);
        this.advisor = advisor;
        this.transcript = transcript;
        this.studentID = studentID;
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
