import java.util.ArrayList;

public class Transcript {
    private ArrayList<Course> completedCourses;
    private ArrayList<Course> currentCourses;

    Transcript(){};

    Transcript(ArrayList<Course> comp, ArrayList<Course> current){
        this.completedCourses = comp;
        this.currentCourses = current;
    };
    public void addCompletedCourse(Course course){
        try {
            if (course != null)
                completedCourses.add(course);
            else
                System.out.println("The course object is null");
        } catch (NullPointerException e) {
            System.out.println("The completedCourses list has not been initialized.");
        }catch (Exception e1){
            System.out.println(e1.getMessage());
        }
    }
}
