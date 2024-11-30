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

    public DepartmentSchedulerInterface(DepartmentScheduler departmentScheduler, ArrayList<CourseSection> courseSections) {
        this.departmentScheduler = departmentScheduler;
        this.courseSections = courseSections;
        scan = new Scanner(System.in);
    }

    @Override
    public boolean showMenu() {
        boolean logOut = false;
        showAvailableCourseSections();
        System.out.println("Choose a course section:\n");
        int sectionNo = scan.nextInt() - 1;
        CourseSection chosenSection = courseSections.get(sectionNo);
        if(chosenSection.getTimeSlots() == null){
            System.out.println("Selected course does not have any time slot or classroom yet.");
            setTimeSlot(chosenSection);
        }
        if(chosenSection.getCapacity() == 0){
            System.out.println("Capacity of the selected course has not been set yet.");
            setCapacity(chosenSection);
        }

        switch (getChoice()) {
            case 1:
                System.out.println("Time slots of the selected course is listed below:");
                for(int i = 0; i < chosenSection.getTimeSlots().size(); i++){
                    System.out.println((i+1) + "-   " + chosenSection.getTimeSlots().get(i).getTimeInterval() + "   " + chosenSection.getTimeSlots().get(i).getClassroom());
                }
                System.out.println("Which time slot do you want to update?");
                chosenSection.getTimeSlots().remove(scan.nextInt()-1);
                setTimeSlot(chosenSection);
                break;
            case 2:
                System.out.println("Time slots of the selected course is listed below:");
                for(int i = 0; i < chosenSection.getTimeSlots().size(); i++){
                    System.out.println((i+1) + "-   " + chosenSection.getTimeSlots().get(i).getTimeInterval() + "   " + chosenSection.getTimeSlots().get(i).getClassroom());
                }
                System.out.println("Which classroom do you want to update?");
                TimeSlot chosenTimeSlot = chosenSection.getTimeSlots().get(scan.nextInt()-1);
                String timeInterval = chosenTimeSlot.getTimeInterval();
                ArrayList<String> availableClassrooms = departmentScheduler.handleClassroomConflict(timeInterval);
                for(int i = 0; i < availableClassrooms.size(); i++){
                    System.out.println((i+1) + "- " + availableClassrooms.get(i));
                }
                System.out.println("Choose a new classroom:");
                chosenTimeSlot.setClassroom(availableClassrooms.get(scan.nextInt()-1));
                System.out.println("Classroom has been updated successfully.");
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

    public int getChoice() {
        
        System.out.println("Choose an operation to do with selected course section:\n1- Update time intervals\n2- Update classroom\n3- Manage Capacity\n");
        int choice = 0;
        try {
            choice = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Enter an integer value.");
        }
        return choice;
    }

    public void showTimeIntervals(){
        System.out.println("Available time slots listed below");
        int size = departmentScheduler.handleTimeConflict(courseSections).size();
        for(int i = 0; i < size; i++){
            System.out.println((i+1) + "- " + departmentScheduler.handleTimeConflict(courseSections).get(i));
        }
    }

    public void showClassrooms(String timeInterval){
        int size = departmentScheduler.handleClassroomConflict(timeInterval).size();
        for(int i = 0; i < size; i++){
            System.out.println((i+1) + "- " + departmentScheduler.handleClassroomConflict(timeInterval).get(i));
        }
    }

    public void showAvailableCourseSections(){
        System.out.println("All available course sections are listed below:\n");
        int size = courseSections.size();
        for(int i = 0; i < size; i++){
            System.out.println((i+1) + "- " + courseSections.get(i).getSectionID() + "\n");
        }
    }

    public void setTimeSlot(CourseSection chosenSection){
        
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

    public void setCapacity(CourseSection chosenSection){
        System.out.println("Enter a capacity:");
        int capacity = scan.nextInt();
        chosenSection.setCapacity(capacity);
        System.out.println("Capacity has been set successfully.");
    }
    
}