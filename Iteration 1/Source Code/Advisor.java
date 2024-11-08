import java.util.ArrayList;

public class Advisor extends Lecturer {
    private ArrayList<Student> students;

    public Advisor(){

    }

    public void approveCourse(Student student, Course course){
        try{
            student.getTranscript().addCurrentCourse(course);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


}
