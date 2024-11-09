import java.util.ArrayList;
import java.util.Date;

public class Lecturer extends Staff{
    protected ArrayList<Course> courses;

    public Lecturer (){
        super();
    }
    public Lecturer(String name, String surname){
        super(name,surname);
    }
    public Lecturer(String name, String surname, Date birthdate, char gender, String ssn, ArrayList<Course> courses){
        super(name, surname, birthdate, gender, ssn);
        this.courses = courses;
    }

    public ArrayList<Course> getCourses(){
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

}
