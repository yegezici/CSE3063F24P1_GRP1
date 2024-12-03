import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentInterface implements UserInterface {
    Scanner scan;
    Student student;
    ArrayList<Course> courses;

    public StudentInterface() {
        scan = new Scanner(System.in);
    }

    public StudentInterface(Student student, ArrayList<Course> courses) {
        this.student = student;
        scan = new Scanner(System.in);
        this.courses = courses;
    }

    public boolean showMenu() {
        boolean logOut = false;
        switch (getChoice()) {
            case 1:
                showTranscripts();
                break;
            case 2:
                registerCourse();
                break;
            case 3:
                printStudentWeeklySchedule();
                break;
            case 4:
                System.out.println("You have successfully logged out\n");
                logOut = true;
                break;
            default:
                System.out.println("Enter 1, 2, 3 or 4.");
        }

        return logOut;
    }

    public int getChoice() {
        System.out
                .println("Select an operation:\n1. Transcript\n2. Register for course\n3. Weekly Schedule\n4. Log out");
        int choice = 0;
        try {
            choice = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer value.");
        }
        return choice;
    }

    public void showTranscripts() {
        student.getTranscript().showCompletedCourses();
        System.out.println("\nGPA: " + student.getTranscript().calculateGpa());
        student.getTranscript().showCurrentCourses();
        student.getTranscript().showWaitedCourses();

    }

    public void registerCourse() {
        ArrayList<Course> availableCourses = showRegisterableCourses();
        while (true) {
            int totalNumberOfWaitedAndCurrentCourses = student.getTranscript().getCurrentCourses().size()
                    + student.getTranscript().getWaitedCourses().size();
            if (totalNumberOfWaitedAndCurrentCourses == 5) {
                System.out
                        .println("You have reached maximum number of courses. You will be directed to main menu.");
                break;
            }
            boolean isAvailableCoursesEmpty = availableCourses.isEmpty();
            if (isAvailableCoursesEmpty) {
                System.out.println("There are no available courses.");
                break;
            } else {
                System.out.println("These are the courses for registering.");
                printList(availableCourses);
                System.out.print("Select a course. If you want to exit press \"0\".");
                int courseChoice = 0;
                try {
                    courseChoice = (scan.nextInt());
                } catch (InputMismatchException e) {
                    System.out.println("Enter an integer value.");
                }
                if (courseChoice == 0) {
                    break;
                } else if (courseChoice < 0 || courseChoice > availableCourses.size()) {
                    System.out.println("Please enter a valid choice.");
                    continue;
                }
                courseChoice -= 1;
                try {
                    Course selectedCourse = availableCourses.get(courseChoice);
                    showAvailableCourseSections(selectedCourse);
                    int size = selectedCourse.getCourseSections().size();
                    int choice = scan.nextInt();
                    choice -= 1;
                    // Kullanıcı seçiminin geçerli bir aralıkta olup olmadığını kontrol et
                    if (choice < 0 || choice >= size) {
                        throw new IndexOutOfBoundsException("Invalid choice!");
                    }
                    student.registerCourse(selectedCourse.getCourseSections().get(choice));
                    System.out.println(selectedCourse.getCourseName() + "."
                            + selectedCourse.getCourseSections().get(choice).getSectionID()
                            + " is sent to your advisor " + student.getAdvisor().getName() + " "
                            + student.getAdvisor().getSurname() + " for approval.");
                    availableCourses.remove(courseChoice);
                } catch (InputMismatchException e) {
                    System.out.println("Invalid chocie! Please enter an integer value.");
                    scan.nextLine(); // Scanner'daki hatalı girişleri temizler
                } catch (IndexOutOfBoundsException e) {
                    System.out.println(
                            "Please enter a proper section choice. It cannot be greater than all sections and must be greater than 1."
                                    + e.getMessage());
                } catch (Exception e) {
                    System.out.println("There is an error " + e.getMessage());
                }

            }
        }

    }

    public ArrayList<Course> showRegisterableCourses() {
        ArrayList<Course> selectingArray = new ArrayList<>();
        int courseListSize = courses.size();
        for (int i = 0; i < courseListSize; i++) {
            Course course = courses.get(i);
            boolean isCompleted = checkCourseExistInList(course, student.getTranscript().getCompletedCourses());
            boolean isWaited = checkCourseExistInList(course, student.getTranscript().getWaitedCourses());
            boolean isCurrent = checkCourseExistInList(course, student.getTranscript().getCurrentCourses());

            if (!isCompleted && !isWaited && !isCurrent && checkPrerequisite(course)) {
                selectingArray.add(course);
            }
        }
        for(int i = 0; i < selectingArray.size(); i++){
            if(selectingArray.get(i).getSemester() != student.getTranscript().getSemester()){
                selectingArray.remove(i);
                i--;
            }
        }
        return selectingArray;
    }

    public boolean checkPrerequisite(Course course) {
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

    public boolean checkCourseExistInList(Course course, ArrayList<Course> courses) {
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

    private void printList(ArrayList<Course> printedList) {
        int size = printedList.size();
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + "       " + printedList.get(i).getCourseId() + "   "
                    + printedList.get(i).getCourseName());
        }
    }

    private void showAvailableCourseSections(Course selectedCourse) {
        int size = selectedCourse.getCourseSections().size();
        for (int k = 0; k < size; k++) {
            System.out.println((k + 1) + "- Section " + selectedCourse.getCourseSections().get(k).getSectionID());
            System.out.println(selectedCourse.getCourseSections().get(k).getTimeSlots().get(0).getDay() + "   "
                    + selectedCourse.getCourseSections().get(k).getTimeSlots().get(0).getTimeInterval() + "   " + 
                    selectedCourse.getCourseSections().get(k).getTimeSlots().get(0).getClassroom());
        }
    }

    private void printStudentWeeklySchedule() {
        WeeklySchedule weeklySchedule = new WeeklySchedule(student);
        weeklySchedule.printStudentWeeklySchedule();
    }
}
