import java.util.ArrayList;

public class Transcript {
    private ArrayList<Course> completedCourses;
    private ArrayList<Course> currentCourses;
    private ArrayList<Course> waitedCourses;

    public Transcript(){

    };

    public Transcript(ArrayList<Course> comp, ArrayList<Course> current){
        this.completedCourses = comp;
        this.currentCourses = current;
    };
//constructor for Transcript object
    public Transcript(ArrayList<Course> completedCourses, ArrayList<Course> currentCourses, ArrayList<Course> waitedCourses){
        this.completedCourses = completedCourses;
        this.currentCourses = currentCourses;
        this.waitedCourses = waitedCourses;
    }
//This method adds a course to the list of completed courses if it has been completed.
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
//This method adds the courses approved by the advisor from the waiting list to the currently taken courses list.
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
    //Adds the courses selected by the student and sent for approval to the waiting course list
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
//This method deletes the courses from the waiting course list according to the approval or rejection status of the place where it is called.
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
//Scans the waitedCourses list and prints the courses there
    public void showWaitedCourses(){
        System.out.println("\nCourses that are waiting for approve listed below:");
        for(int i = 0; i < waitedCourses.size(); i++){
            System.out.printf("%d-   %-10s %-50s %d\n", (i+1), waitedCourses.get(i).getCourseId(), waitedCourses.get(i).getCourseName(), waitedCourses.get(i).getCredits());
        }
    }
//Scans the completedCourses list and prints the courses there
    public void showCompletedCourses(){
        System.out.println("\nCourses that are completed listed below:");
        for(int i = 0; i < completedCourses.size(); i++){
            System.out.printf("%d-   %-10s %-50s %d\n", (i+1), completedCourses.get(i).getCourseId(), completedCourses.get(i).getCourseName(), completedCourses.get(i).getCredits());
        }
    }
//Scans the currentCourses list and prints the courses there
    public void showCurrentCourses(){
        System.out.println("\nCourses that are registered listed below:");
        for(int i = 0; i < currentCourses.size(); i++){
            System.out.printf("%d-   %-10s %-50s %d\n", (i+1), currentCourses.get(i).getCourseId(), currentCourses.get(i).getCourseName(), currentCourses.get(i).getCredits());
        }
    }
//Sets the list of courses that the student has completed
    public void setCompletedCourses(ArrayList<Course> completedCourses) {
        this.completedCourses = completedCourses;
    }
//Sets the list of courses that the student is currently enrolled in
    public void setCurrentCourses(ArrayList<Course> currentCourses) {
        this.currentCourses = currentCourses;
    }
//Sets the list of courses that the student is waiting to enroll in
    public void setWaitedCourses(ArrayList<Course> waitedCourses) {
        this.waitedCourses = waitedCourses;
    }
//Retrieves the list of courses that the student has completed.
    public ArrayList<Course> getCompletedCourses() {
        return completedCourses;
    }
//Retrieves the list of courses that the student is currently enrolled in
    public ArrayList<Course> getCurrentCourses() {
        return currentCourses;
    }

    // Retrieves the list of courses that the student is waiting to enroll in.
    // If the waitlist is null, returns an empty ArrayList   

    public ArrayList<Course> getWaitedCourses() {
        return waitedCourses != null ? waitedCourses : new ArrayList<>();
    }


}


