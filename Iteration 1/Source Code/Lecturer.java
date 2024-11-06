import java.util.ArrayList;

public class Lecturer extends Staff{
    protected ArrayList<Course> courses;

    public Lecturer (){

    }

    public ArrayList<Course> getCourses(){
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

}
