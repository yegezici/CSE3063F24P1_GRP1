import java.util.ArrayList;
import java.util.Date;


public class Student extends Person{
	private Transcript transcript;
	private String studentID;
	private String advisorID;
	
	public Student() {
		
	}
	//constructor for student object
	public Student(String name, String surname, Date birthdate, char gender, Transcript transcript, String studentID, String advisorID) {
        super(name, surname, birthdate, gender);
        this.advisorID = advisorID;
        this.transcript = transcript;
        this.studentID = studentID;
    }
	//Adds the course the student wants to enroll in to the waited courses list
	public void registerCourse(Course course) {
		transcript.addWaitedCourse(course);
		
	}
//Retrieves the transcript of the student. Transcript object representing the student's academic record
	public Transcript getTranscript() {
		return transcript;
	}
//Retrieves the student ID
	public String getStudentID() {
		return studentID;
	}
//Retrieves the advisor ID assigned to the student
	public String getAdvisorID() {
		return advisorID;
	}
	
}
