import java.util.ArrayList;
import java.util.Date;


public class Student extends Person{
	private Transcript transcript;
	private Advisor advisor;

	
	public Student() {
		
	}
	//constructor for student object
	public Student(String name, String surname, Date birthdate, char gender, Transcript transcript, String studentID) {
        super(name, surname, birthdate, gender, studentID);
        this.transcript = transcript;
        
    }
	//Adds the course the student wants to enroll in to the waited courses list
	public void registerCourse(CourseSection coursesSection) {
		transcript.addWaitedCourse(coursesSection.getParentCourse());
		transcript.getWaitedSections().add(coursesSection);
	}
//Retrieves the transcript of the student. Transcript object representing the student's academic record
	public Transcript getTranscript() {
		return transcript;
	}
//Retrieves the student ID
    @Override
	public String getID() {
		return this.ID;
	}
//Retrieves the advisor ID assigned to the student
	
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public String getSurname() {
		return this.surname;
	}
	@Override
	public Date getBirthdate() {
		return this.birthdate;
	}
	@Override
	public char getGender() {
		return this.gender;
	}
	
	public Advisor getAdvisor() {
		return advisor;
	}	
	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}
}