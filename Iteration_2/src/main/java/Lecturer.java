import java.util.ArrayList;
import java.util.Date;

public class Lecturer extends Staff{
    private ArrayList<Course> courses;

    //constructors
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

    //Retrieves the list of courses taught by this lecturer
    public ArrayList<Course> getCourses(){
        return courses;
    }
//Sets the list of courses for this lecturer
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

}
