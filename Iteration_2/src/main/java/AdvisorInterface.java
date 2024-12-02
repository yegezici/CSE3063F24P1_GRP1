import java.util.InputMismatchException;
import java.util.Scanner;

public class AdvisorInterface implements UserInterface {
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
        System.out.print("1-  Students Menu\n2-  Log Out\nSelect an operation: ");
        int choice = 0;
        try{
        choice = scan.nextInt();
        }catch(InputMismatchException e){
            System.out.println("Enter an integer value.");
        }
        return choice;
    }

    public boolean showStudentsMenu() {
        boolean logOut = false;
        int numberOfStudents = advisor.getStudents().size();
        for (int i = 0; i < numberOfStudents; i++) {
            System.out.println((i + 1) + "-    " + advisor.getStudents().get(i).getID());
        }
        int studentIndex = -1;
        try{
        System.out.print("Which student do you select? :\nIf you want to log out, enter \"0\": ");
        studentIndex = scan.nextInt() - 1;
        }catch(InputMismatchException e){
            System.out.println("Enter an integer value.");
            return false;
        }
        if (studentIndex == -1)
            return false;
        else if (studentIndex >= (numberOfStudents - 1)) {
            System.out.println("Please write a number between 1 and " + numberOfStudents + ".");
            return false;
        }
        Student currentStudent = advisor.getStudents().get(studentIndex);
        if (currentStudent == null)
            System.out.println("Student is null.");

        studentOperations(currentStudent);
        return logOut;
    }

    public void approveCourse(Student student, CourseSection courseSection) {
        advisor.approveCourse(student, courseSection);
        System.out.println(courseSection.getParentCourse().getCourseId() + " has been approved.");
        if (student.getTranscript().getWaitedCourses().isEmpty())
            System.out.println("No more courses to approve.");
    }

    public void rejectCourse(Student student, CourseSection course) {
        advisor.rejectCourse(student, course.getParentCourse());
        System.out.println("The course has been rejected.");
    }

    public boolean courseOperations(Student student, int courseIndex) {
        boolean logOut = false;
    
        // Get Course Section.
        CourseSection courseSection = student.getTranscript().getWaitedCourses().get(courseIndex - 1).getCourseSections().get(0);
    
        // Advisor will check that is there any conflict. If there is, request will be rejected.
        if (!advisor.checkSectionConflict(student, courseSection)) {
            System.out.println("There is a conflict between sections. The course cannot be approved! It is automatically rejected.");
            rejectCourse(student, courseSection);
            return logOut;
        }
    
        // If there isn't any conflict, get confirmation from user.
        System.out.print("Do you want to approve this course?(y/n): \nIf you want to turn back enter \"0\": ");
        String approve = scan.next();
    
        // Check confirmation message.
        if (!approve.equals("0") && !approve.equals("y") && !approve.equals("n")) {
            System.out.println("Enter \"y\", \"n\" or \"0\".");
        } else if (approve.equals("0")) {
            logOut = true;
        } else if (approve.equals("y")) {
            approveCourse(student, courseSection); // Approve Course.
        } else {
            rejectCourse(student, courseSection); // Reject Course.
        }
    
        return logOut;
    }
    

    public void studentOperations(Student student) {
        while (true) {
            if (student.getTranscript().getWaitedCourses().isEmpty()) {
                System.out.println("All waited courses have been approved. You will be directed to main menu.");
                break;
            }
            student.getTranscript().showWaitedCourses();
            int courseIndex = -1;
            System.out.print("Which course do you want  to select?: \nIf you want to turn back enter \"0\": ");
            try {
                courseIndex = scan.nextInt();
                if (courseIndex == 0)
                    break;
                int size = student.getTranscript().getWaitedCourses().size();
                if (courseIndex <= 0 || courseIndex > size) {
                    System.out.println("Enter a value between 1 and " + size);
                } else {
                    if (courseOperations(student, courseIndex))
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter an integer value");
                break;
            }

        }
    }
}
