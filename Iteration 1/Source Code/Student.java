import java.util.ArrayList;
import java.util.Date;


public class Student extends Person{
	private Transcript transcript;
	private String studentID;
	private String advisorID;
	
	public Student() {
		
	}
	
	public Student(String name, String surname, Date birthdate, char gender, Transcript transcript, String studentID, String advisorID) {
        super(name, surname, birthdate, gender);
        this.advisorID = advisorID;
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

	public String getAdvisorID() {
		return advisorID;
	}
	
}
