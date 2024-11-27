import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentAffairsStaffInterface implements UserInterface {
    StudentAffairsStaff staff;
    Scanner scan;

    public StudentAffairsStaffInterface() {
        scan = new Scanner(System.in);
    }

    public StudentAffairsStaffInterface(StudentAffairsStaff staff) {
        this.staff = staff;
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

    public void askCourseParameters(){
        try {
            System.out.print("Enter course name: ");
            String courseName = scan.next();
            System.out.print("Enter course code: ");
            String courseCode = scan.next();
            System.out.print("Enter course credits: ");
            int courseCredits = scan.nextInt();
            

        }catch(InputMismatchException e){

        }catch (Exception e) {

        }
    }


}
