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
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Enter an integer value between 1 and " + courseSections.size() + ".");
        }
        return chosenSection;
    }

    public void updateTimeInterval(CourseSection chosenSection) {
        System.out.println("Time slots of the selected course is listed below:");
        for (int i = 0; i < chosenSection.getTimeSlots().size(); i++) {
            System.out.println((i + 1) + "-   " + chosenSection.getTimeSlots().get(i).getTimeInterval() + "   "
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
        System.out.println("Time slots of the selected course is listed below:");
        for (int i = 0; i < chosenSection.getTimeSlots().size(); i++) {
            System.out.println((i + 1) + "-   " + chosenSection.getTimeSlots().get(i).getTimeInterval() + "   "
                    + chosenSection.getTimeSlots().get(i).getClassroom());
        }
        System.out.println("Which classroom do you want to update?");
        TimeSlot chosenTimeSlot = chosenSection.getTimeSlots().get(scan.nextInt() - 1);
        String timeInterval = chosenTimeSlot.getTimeInterval();
        ArrayList<String> availableClassrooms = departmentScheduler.handleClassroomConflict(timeInterval);
        for (int i = 0; i < availableClassrooms.size(); i++) {
            System.out.println((i + 1) + "- " + availableClassrooms.get(i));
        }
        System.out.println("Choose a new classroom:");
        chosenTimeSlot.setClassroom(availableClassrooms.get(scan.nextInt() - 1));
        System.out.println("Classroom has been updated successfully.");
    }

    @Override
    public boolean showMenu() {
        boolean logOut = false;
        CourseSection chosenSection = chooseCourseSection();
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
            case 4:
                System.out.println("You have successfully logged out\n");
                logOut = true;
                break;
            default:
                System.out.println("Enter a number between 1 and 4.");
        }
        return logOut;
    }

    @Override
    public int getChoice() {
        System.out.println(
                "Choose an operation\n1- Update time interval\n2- Update classroom\n3- Manage Capacity \n4- Log Out");
        int choice = 0;
        try {
            choice = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer value.");
        }
        return choice;
    }

    public void showTimeIntervals() {
        System.out.println("Available time slots listed below");
        int size = departmentScheduler.handleTimeConflict(courseSections).size();
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + "- " + departmentScheduler.handleTimeConflict(courseSections).get(i));
        }
    }

    public void showClassrooms(String timeInterval) {
        int size = departmentScheduler.handleClassroomConflict(timeInterval).size();
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + "- " + departmentScheduler.handleClassroomConflict(timeInterval).get(i));
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
        showTimeIntervals();
        System.out.println("Choose a time slot:");
        int timeSlotNo = scan.nextInt() - 1;
        String timeSlotInput = departmentScheduler.handleTimeConflict(courseSections).get(timeSlotNo);
        showClassrooms(timeSlotInput);
        System.out.println("Choose a classroom:");
        int classroomNo = scan.nextInt() - 1;
        String classroomInput = departmentScheduler.handleClassroomConflict(timeSlotInput).get(classroomNo);
        TimeSlot timeSlot = new TimeSlot(timeSlotInput, classroomInput);
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