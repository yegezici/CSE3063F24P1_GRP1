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
                removeCourse();
                break;
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
        } catch (Exception e) {
            System.out.println("There is an error in getChoice method.");
        }
        return choice;
    }

    public void addCourse() {
        String[] course = askCourseParameters();
        try {
            courses.add(
                    staff.createCourse(course[0], course[1], Integer.parseInt(course[2]), Integer.parseInt(course[3])));
        } catch (NumberFormatException e) {
            System.out.println("Enter an integer value for course code and course credits.");
        }
    }

    public String[] askCourseParameters() {
        String[] course = new String[4];
        try {
            System.out.print("Enter course name: ");
            course[0] = scan.next();
            System.out.print("Enter course code: ");
            course[1] = scan.next();
            System.out.print("Enter course credits: ");
            course[2] = scan.next();
            System.out.print("How many sections does the course have? : ");
            course[3] = scan.next();

        } catch (InputMismatchException e) {
            System.out.println("InputMismatchException in askCourseParameters method.");
        } catch (Exception e) {
            System.out.println("There is an error in askCourseParameters method.");
        }
        return course;
    }

    public void removeCourse() {
        printCourses();
        System.out.println("Which course do you want to remove?");
        int courseIndex = -1;
        Course removedCourse = null;
        try {
            courseIndex = scan.nextInt() - 1;
            removedCourse = courses.remove(courseIndex);
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer value.");
            return;
        }
        System.out.println("The " + removedCourse.getCourseId() + " course has been removed.");
    }

    public void printCourses() {
        for (int i = 0; i < courses.size(); i++) {
            System.out.println(i + 1 + "- " + courses.get(i).toString());
        }
    }
}
