import java.util.ArrayList;

public class Transcript {
    private ArrayList<Course> completedCourses;
    private ArrayList<Course> currentCourses;
    private ArrayList<Course> waitedCourses = new ArrayList<>();

    public Transcript(){

    };

    public Transcript(ArrayList<Course> comp, ArrayList<Course> current){
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

    public void addCurrentCourse(Course course){
        try {
            if (course != null)
                currentCourses.add(course);
            else
                System.out.println("The course object is null");
        } catch (NullPointerException e) {
            System.out.println("The completedCourses list has not been initialized.");
        }catch (Exception e1){
            System.out.println(e1.getMessage());
        }
    }
    public void addWaitedCourse(Course course){
        try {
            if (course != null)
                waitedCourses.add(course);
            else
                System.out.println("The course object is null");
        } catch (NullPointerException e) {
            System.out.println("The waitedCourses list has not been initialized.");
        }catch (Exception e1){
            System.out.println(e1.getMessage());
        }
    }

    public void deleteFromWaitedCourse(Course course){
        try {
            if (course != null)
                waitedCourses.remove(course);
            else
                System.out.println("The course object is null");
        } catch (NullPointerException e) {
            System.out.println("The waitedCourses list has not been initialized.");
        }catch (Exception e1){
            System.out.println(e1.getMessage());
        }
    }

    public void showWaitedCourses(){
        System.out.println("Courses that are waiting for approve listed below:");
        for(int i = 0; i < waitedCourses.size(); i++){
            System.out.println((i+1) + "-   " + waitedCourses.get(i).getCourseId() + "   " + waitedCourses.get(i).getCourseName());
        }
    }

    public void showCompletedCourses(){
        System.out.println("Courses that are completed listed below:");
        for(int i = 0; i < completedCourses.size(); i++){
            System.out.println((i+1) + "-   " + completedCourses.get(i).getCourseId() + "   " + completedCourses.get(i).getCourseName());
        }
    }

    public void showCurrentCourses(){
        System.out.println("Courses that are registered listed below:");
        for(int i = 0; i < currentCourses.size(); i++){
            System.out.println((i+1) + "-   " + currentCourses.get(i).getCourseId() + "   " + currentCourses.get(i).getCourseName());
        }
    }

    public void setCompletedCourses(ArrayList<Course> completedCourses) {
        this.completedCourses = completedCourses;
    }

    public void setCurrentCourses(ArrayList<Course> currentCourses) {
        this.currentCourses = currentCourses;
    }

    public void setWaitedCourses(ArrayList<Course> waitedCourses) {
        this.waitedCourses = waitedCourses;
    }

    public ArrayList<Course> getCompletedCourses() {
        return completedCourses;
    }

    public ArrayList<Course> getCurrentCourses() {
        return currentCourses;
    }

    public ArrayList<Course> getWaitedCourses() {
        return waitedCourses != null ? waitedCourses : new ArrayList<>();
    }
}
