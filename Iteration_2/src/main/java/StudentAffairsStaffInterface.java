import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentAffairsStaffInterface implements UserInterface {
    StudentAffairsStaff staff;
    Scanner scan;
    ArrayList<Course> courses;

    public StudentAffairsStaffInterface() {
        scan = new Scanner(System.in);
    }

    public StudentAffairsStaffInterface(StudentAffairsStaff staff, ArrayList<Course> courses) {
        this.staff = staff;
        this.courses = courses;
        scan = new Scanner(System.in);
    }
    

    public boolean showMenu() {
        switch (getChoice()) {
            case 1:
                addCourse();
                break;
            case 2:
                return true;
                //break;
            case 3:
                return true;
            default:
            System.out.println("Enter 1, 2 or 3.");
                break;
        }

        return false;
    }


    public int getChoice() {
        System.out.print("1-  Add Course\n2-  Remove Course\n3-  Log Out\nSelect an operation: ");
        int choice = 0;
        try {
            choice = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer value.");
        }catch(Exception e){
            System.out.println("There is an error in getChoice method."); 
        }
        return choice;
    }

    public void addCourse() {
        askCourseParameters();
        staff.createCourse();
    }

    public Course askCourseParameters(){
        Course course = null;
        try {
            System.out.print("Enter course name: ");
            String courseName = scan.next();
            System.out.print("Enter course code: ");
            String courseCode = scan.next();
            System.out.print("Enter course credits: ");
            int courseCredits = scan.nextInt();
            System.out.print("How many sections does the course have? : ");
            int numberOfSections = scan.nextInt();
            course = new Course(courseName, courseCode, courseCredits);
            for(int k = 0 ; k < numberOfSections ; k++ ){ 
              course.getCourseSections().add(new CourseSection(String.valueOf(k+1),100, course));
            }
            
        }catch(InputMismatchException e){

        }catch (Exception e) {

        }
        return course;
    }




}