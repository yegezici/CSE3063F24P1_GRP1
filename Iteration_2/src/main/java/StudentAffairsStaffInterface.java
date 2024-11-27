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

                break;
            case 2:

                break;
            default:
            System.out.println("Enter 1 or 2.");
                break;
        }

        return false;
    }


    public int getChoice() {
        System.out.print("1-  Add Course\n2-  Remove Course\nSelect an operation: ");
        int choice = 0;
        try {
            choice = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer value.");
        }
        return choice;
    }

    public void addCourse() {

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
            

        }catch(InputMismatchException e){

        }catch (Exception e) {

        }
        return course;
    }


}
