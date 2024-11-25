import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdvisorInterface extends UserInterface{
    Advisor advisor;
    Scanner scan;

    public AdvisorInterface() {
        scan = new Scanner(System.in);
    }

    public AdvisorInterface(Advisor advisor) {
        this.advisor = advisor;
        scan = new Scanner(System.in);
    }

    @Override
    public boolean showMenu() {
        boolean logOut = false;
        switch (getChoice()) {
            case 1:
                logOut = showStudentsMenu();
                break;
                case 2:
                System.out.println("You have successfully logged out\n");
                logOut = true;
                break;
            default:
                System.out.println("Enter 1 or 2.");
        }
        return logOut;
    }
    public int getChoice() {
        System.out.println("Select an operation:\n1-  Students Menu\n2-  Log Out"); 
        int choice = scan.nextInt();
        return choice;
    }
    public boolean showStudentsMenu(){
        boolean logOut = false;
        int numOfStudents = advisor.getStudents().size();
        for (int i = 0; i < numOfStudents; i++) {
            System.out.println((i + 1) + "-    " + advisor.getStudents().get(i).getStudentID());
        }
        System.out.print("Which student do you select? :\nIf you want to log out, enter \"0\": ");
        int studentIndex = scan.nextInt() - 1;
        if (studentIndex == -1)
            return false;
        Student currentStudent = advisor.getStudents().get(studentIndex);
        int numberOfStudents = advisor.getStudents().size();
        if(studentIndex >= numberOfStudents){
            System.out.println("Please write a number between 1 and " + numberOfStudents + ".");
        }else if (currentStudent == null) {
            System.out.println("Student is null.");
        }
        studentOperations(currentStudent);
        return logOut;
    }
    public void approveCourse(Student student, Course course){
        advisor.approveCourse(student, course);
        System.out.println("The course has been approved.");
        if(student.getTranscript().getWaitedCourses().isEmpty())
            System.out.println("No more courses to approve.");
    }

    public void rejectCourse(Student student, Course course){
        advisor.rejectCourse(student, course);
        System.out.println("The course has been rejected.");
    }

    public boolean courseOperations(Student student, int courseIndex) {
        boolean logOut = false;
        Course course = student.getTranscript().getWaitedCourses().get(courseIndex - 1);
        System.out.print("Do you want to approve this course?(y/n): \nIf you want to turn back enter \"0\": ");
        String approve = scan.next();
        if (!approve.equals("0") && !approve.equals("y") && !approve.equals("n")) {
            System.out.println("Enter \"y\", \"n\" or \"0\".");

        } else if (approve.equals("0")) {
            logOut = true;
        } else if (approve.equals("y")) {
            approveCourse(student, course);
        } else {
            rejectCourse(student, course);
        }
        
        return logOut;
    }

    public void studentOperations(Student student){
        while (true) {
            if(student.getTranscript().getWaitedCourses().isEmpty()){
                System.out.println("All waited courses have been approved. You will be directed to main menu.");
                break;
            }
            student.getTranscript().showWaitedCourses();
            int courseIndex = -1;
            System.out.print("Which course do you want  to select?: \nIf you want to turn back enter \"0\": ");
            try {
                courseIndex = scan.nextInt();
                if(courseIndex == 0)
                    break;
                int size = student.getTranscript().getWaitedCourses().size();
                if (courseIndex <= 0 || courseIndex > size) {
                    System.out.println("Enter a value between 1 and " + size);
                } else {
                    if(courseOperations(student, courseIndex))
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter an integer value");
                break;
            }


        }
    }
}
