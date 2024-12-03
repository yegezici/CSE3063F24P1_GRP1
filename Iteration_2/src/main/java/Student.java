import java.util.Date;

public class Student extends Person {
    private Transcript transcript;
    private Advisor advisor;

    public Student() {}

    public Student(String name, String surname, Date birthdate, char gender, Transcript transcript, String studentID) {
        super(name, surname, birthdate, gender, studentID);
        this.transcript = transcript;
    }

    @Override
    public String getName() {
        return super.getNameField();
    }

    @Override
    public String getSurname() {
        return super.getSurnameField();
    }

    @Override
    public Date getBirthdate() {
        return super.getBirthdateField();
    }

    @Override
    public char getGender() {
        return super.getGenderField();
    }

    @Override
    public String getID() {
        return super.getIDField();
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void registerCourse(CourseSection courseSection) {
        transcript.addWaitedCourse(courseSection.getParentCourse());
        transcript.getWaitedSections().add(courseSection);
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }
}
