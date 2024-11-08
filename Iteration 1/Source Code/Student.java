public class Student extends Person{
    private Transcript transcript;
    private String studentID;
    private Advisor advisor;

    public void registerCourse(Course course) {


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
