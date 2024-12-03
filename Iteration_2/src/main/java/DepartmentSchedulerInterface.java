import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DepartmentSchedulerInterface implements UserInterface {
    DepartmentScheduler departmentScheduler;
    ArrayList<CourseSection> courseSections;
    Scanner scan;

    public DepartmentSchedulerInterface() {
        scan = new Scanner(System.in);
    }

    public DepartmentSchedulerInterface(DepartmentScheduler departmentScheduler,
            ArrayList<CourseSection> courseSections) {
        this.departmentScheduler = departmentScheduler;
        this.courseSections = courseSections;
        scan = new Scanner(System.in);
    }

    public void updateTimeInterval(CourseSection chosenSection) {
        System.out.println("Time slots of the selected course are listed below:");
        for (int i = 0; i < chosenSection.getTimeSlots().size(); i++) {
            System.out.println((i + 1) + "-   " + chosenSection.getTimeSlots().get(i).getDay() + "   "
                    + chosenSection.getTimeSlots().get(i).getTimeInterval() + "   "
                    + chosenSection.getTimeSlots().get(i).getClassroom());
        }
        try {
            System.out.println("Which time slot do you want to update?");
            chosenSection.getTimeSlots().remove(scan.nextInt() - 1);
            setTimeSlot(chosenSection);
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer value.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Enter an integer value between 1 and " + chosenSection.getTimeSlots().size() + ".");
        }
    }

    public void updateClassroom(CourseSection chosenSection) {
        System.out.println("Time slots of the selected course are listed below:");
        for (int i = 0; i < chosenSection.getTimeSlots().size(); i++) {
            System.out.println((i + 1) + "-   " + chosenSection.getTimeSlots().get(i).getDay() + "   "
                    + chosenSection.getTimeSlots().get(i).getTimeInterval() + "   "
                    + chosenSection.getTimeSlots().get(i).getClassroom());
        }
        System.out.println("Which classroom do you want to update?");
        TimeSlot chosenTimeSlot = chosenSection.getTimeSlots().get(scan.nextInt() - 1);
        String day = chosenTimeSlot.getDay();
        String timeInterval = chosenTimeSlot.getTimeInterval();
        ArrayList<String> availableClassrooms = departmentScheduler.handleClassroomConflict(day, timeInterval);
        for (int i = 0; i < availableClassrooms.size(); i++) {
            System.out.println((i + 1) + "- " + availableClassrooms.get(i));
        }
        System.out.println("Choose a new classroom:");
        chosenTimeSlot.setClassroom(availableClassrooms.get(scan.nextInt() - 1));
        System.out.println("Classroom has been updated successfully.");
    }

    public CourseSection chooseCourseSection() {
        showAvailableCourseSections();
        CourseSection chosenSection = null;
        try {
            System.out.println("Choose a course section:");
            int sectionNo = scan.nextInt() - 1;
            chosenSection = courseSections.get(sectionNo);
            if (chosenSection.getTimeSlots().size() == 0) {
                System.out.println("Selected course does not have any time slot or classroom yet.");
                setTimeSlot(chosenSection);
            }
            if (chosenSection.getCapacity() == 0) {
                System.out.println("Capacity of the selected course has not been set yet.");
                setCapacity(chosenSection);
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer value");
            scan.nextLine();
            return null;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Enter an integer value between 1 and " + courseSections.size() + ".");
            scan.nextLine();
            return null;
        }
        return chosenSection;
    }

    @Override
    public boolean showMenu() {
        boolean logOut = false;
        CourseSection chosenSection;
        System.out.println("Enter 1 to access course configuration menu. If you want to quit press any button.");
        String choice = scan.next();
        if(choice.equals("1")){
            while((chosenSection = chooseCourseSection()) == null);
                switch (getChoice()) {
                    case 1:
                        updateTimeInterval(chosenSection);
                    break;
                    case 2:
                        updateClassroom(chosenSection);
                    break;
                    case 3:
                        System.out.println("Current capacity is " + chosenSection.getCapacity());
                        System.out.println("Enter new capacity:");
                        int newCapacity = scan.nextInt();
                        departmentScheduler.manageCapacity(chosenSection, newCapacity);
                    break;
                    default:
                        System.out.println("Enter a number between 1 and 3.");
                }
            return logOut;
            }
        else{
            return true;
        }
    }

    @Override
    public int getChoice() {
        System.out.println(
                "Choose an operation\n1- Update time interval\n2- Update classroom\n3- Manage Capacity");
        int choice = 0;
        try {
            choice = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer value.");
        }
        return choice;
    }

    public void showDays(int semester) {
        ArrayList<String> availableDays = departmentScheduler.getAvailableDays(departmentScheduler.semesterXCourses(semester));
        System.out.println("Available days:");
        for (int i = 0; i < availableDays.size(); i++) {
            System.out.println((i + 1) + "- " + availableDays.get(i));
        }
    }

    public void showTimeIntervals(int semester, String day) {
        System.out.println("Available time slots for " + day + " are listed below:");
        ArrayList<String> availableTimeIntervals = departmentScheduler.handleTimeConflict(departmentScheduler.semesterXCourses(semester), day);
        for (int i = 0; i < availableTimeIntervals.size(); i++) {
            System.out.println((i + 1) + "- " + availableTimeIntervals.get(i));
        }
    }

    public void showClassrooms(String day, String timeInterval) {
        ArrayList<String> availableClassrooms = departmentScheduler.handleClassroomConflict(day, timeInterval);
        System.out.println("Available classrooms for " + day + " " + timeInterval + ":");
        for (int i = 0; i < availableClassrooms.size(); i++) {
            System.out.println((i + 1) + "- " + availableClassrooms.get(i));
        }
    }

    public void showAvailableCourseSections() {
        System.out.println("All available course sections are listed below:");
        int size = courseSections.size();
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + "- " + courseSections.get(i).getParentCourse().getCourseId() + "."
                    + courseSections.get(i).getSectionID());
        }
    }

    public void setTimeSlot(CourseSection chosenSection) {
        int semester = chosenSection.getParentCourse().getSemester();
        showDays(semester);
        System.out.println("Choose a day:");
        String day = departmentScheduler.getAvailableDays(departmentScheduler.semesterXCourses(semester)).get(scan.nextInt() - 1);
        showTimeIntervals(semester, day);
        System.out.println("Choose a time slot:");
        String timeSlotInput = departmentScheduler.handleTimeConflict(departmentScheduler.semesterXCourses(semester), day)
                .get(scan.nextInt() - 1);
        showClassrooms(day, timeSlotInput);
        System.out.println("Choose a classroom:");
        String classroomInput = departmentScheduler.handleClassroomConflict(day, timeSlotInput)
                .get(scan.nextInt() - 1);
        TimeSlot timeSlot = new TimeSlot(day, timeSlotInput, classroomInput);
        departmentScheduler.assignTimeSlotToSection(chosenSection, timeSlot);
        System.out.println("Selected time slot and classroom has been assigned.");
    }

    public void setCapacity(CourseSection chosenSection) {
        System.out.println("Enter a capacity:");
        int capacity = scan.nextInt();
        chosenSection.setCapacity(capacity);
        System.out.println("Capacity has been set successfully.");
    }
}
