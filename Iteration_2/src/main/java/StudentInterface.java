import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentInterface extends UserInterface{
    Scanner scan;
    Student student;
    
    public StudentInterface() {
        scan = new Scanner(System.in);
    }

    public StudentInterface(Student student) {
        this.student = student;
        scan = new Scanner(System.in);
    }

    public boolean showMenu() {
        return true;
    }
    public int getChoice(){
        System.out.println("Select an operation:\n1. Transcript\n2. Register for course\n3. Log out");
        int choice = scan.nextInt();
        return choice;
    }
    
    public void showTranscripts(){
        student.getTranscript().showCompletedCourses();
        student.getTranscript().showCurrentCourses();
        student.getTranscript().showWaitedCourses();
    }
    
    public void registerCourse(Course course) {
        ArrayList<Course> selectingArray = new ArrayList<>();
        boolean isCompleted = viewTranscript(course, student.getTranscript().getCompletedCourses());
        boolean isWaited = viewTranscript(course, student.getTranscript().getWaitedCourses());
        boolean isCurrent = viewTranscript(course, student.getTranscript().getCurrentCourses());
        
        if (!isCompleted && !isWaited && !isCurrent && checkPrerequisite(course)) {
            selectingArray.add(course);
        }
    }

    public boolean checkPrerequisite(Course course){
        Course prerequisite = course.getPrerequisiteCourse();
        boolean prerequisiteMet = prerequisite == null; // No prerequisite means it's met

        // If there is a prerequisite, check if it's completed
        if (prerequisite != null) {
            for (Course completed : student.getTranscript().getCompletedCourses()) {
                if (completed.getCourseId().equals(prerequisite.getCourseId())) {
                    prerequisiteMet = true;
                    break;
                }
            }
        }

        return prerequisiteMet;
    
    }

    public boolean viewTranscript(Course course,ArrayList<Course> courses) {
        boolean isCompleted = false;
        if (courses != null) {
            int coursesListSize = courses.size();
            for (int k = 0; k < coursesListSize; k++) {
                if (course.getCourseId().equals(courses.get(k).getCourseId())) {
                    isCompleted = true;
                    break;
                }
            }
        }
        return isCompleted;
    }
    
    

    public static boolean studentInterface(Student student, ArrayList<Course> courses) {
        Scanner scan = new Scanner(System.in);
        System.out.println("1. Transcript\n2. Register for course\n3. Log out");
        boolean logout = false;
        int choice = -1;
        try {
            choice = scan.nextInt();
        } catch (InputMismatchException e) {
        }
        switch (choice) {
                // Choice 1: Display the student's transcript, showing completed, current, and waited courses
            case 1:
                student.getTranscript().showCompletedCourses();
                student.getTranscript().showCurrentCourses();
                student.getTranscript().showWaitedCourses();
                break;
                // Choice 2: Handle the course registration process
            case 2:
                System.out.println("These are the courses for registering.");
                ArrayList<Course> selectingArray = new ArrayList<>();
                int index = 1;
                for (int j = 0; j < courses.size(); j++) {
                    boolean isCompleted = false;
                    boolean isWaited = false;
                    boolean isCurrent = false;
                    // Check if the course is in the completed courses
                    if (student.getTranscript().getCompletedCourses() != null) {
                        for (int k = 0; k < student.getTranscript().getCompletedCourses().size(); k++) {
                            if (courses.get(j).getCourseId().equals(student.getTranscript().getCompletedCourses().get(k).getCourseId())) {
                                isCompleted = true;
                                break;
                            }
                        }
                    }

                    // Check if the course is in the waited courses
                    if (student.getTranscript().getWaitedCourses() != null) {
                        for (int k = 0; k < student.getTranscript().getWaitedCourses().size(); k++) {
                            if (courses.get(j).getCourseId().equals(student.getTranscript().getWaitedCourses().get(k).getCourseId())) {
                                isWaited = true;
                                break;
                            }
                        }
                    }
                    if (student.getTranscript().getCurrentCourses() != null) {
                        for (int k = 0; k < student.getTranscript().getCurrentCourses().size(); k++) {
                            if (courses.get(j).getCourseId().equals(student.getTranscript().getCurrentCourses().get(k).getCourseId())) {
                                isCurrent = true;
                                break;
                            }
                        }
                    }


                        // Register the course if it is not in the completed courses
                        if (!isCompleted && !isWaited && !isCurrent) {

                            Course prerequisite = courses.get(j).getPrerequisiteCourse();
                            boolean prerequisiteMet = prerequisite == null; // No prerequisite means it's met

                            // If there is a prerequisite, check if it's completed
                            if (prerequisite != null) {
                                for (Course completed : student.getTranscript().getCompletedCourses()) {
                                    if (completed.getCourseId().equals(prerequisite.getCourseId())) {
                                        prerequisiteMet = true;
                                        break;
                                    }
                                }
                            }

                            // Add course if prerequisites are met
                            if (prerequisiteMet) {
                                selectingArray.add(courses.get(j));
                            }
                        }
                    }
                System.out.println("Select a course. If you want to exit press 0.");
                int capacity = student.getTranscript().getWaitedCourses().size() + student.getTranscript().getCurrentCourses().size();
                int takenCourseNumber = 0;
                printList(selectingArray);
                while (true) {
                    if (selectingArray.isEmpty()) {
                        System.out.println("There is no course to register");
                        break;
                    }

                    int courseChoice = (scan.nextInt());

                    if (courseChoice == 0) {
                        break;
                    }

                    if (courseChoice < 0 || courseChoice > selectingArray.size()) {
                        System.out.println("Please enter a valid choice.");
                        continue;
                    }
                    courseChoice += -1;
                    if (student.getTranscript().getCurrentCourses().size() + student.getTranscript().getWaitedCourses().size() == 5) {
                        System.out.println("You have reached maximum number of courses. You will be directed to main menu.");
                        break;
                    } else {
                        // Register the selected course and add it to the waited courses list
                        student.registerCourse(selectingArray.get(courseChoice));
                        addWaitedCourse(student, selectingArray.get(courseChoice));
                        System.out.println(selectingArray.get(courseChoice).getCourseName() + " " + "is succesfully registered.");
                        selectingArray.remove(courseChoice);
                        printList(selectingArray);
                        takenCourseNumber++;

                    }
                }
                break;
            case 3:
                System.out.println("You are successfully logged out.\n");
                logout = true;
                break;
            default:
                System.out.println("Enter an integer value between 1 and 3");
        }
        return logout;
    }
}
