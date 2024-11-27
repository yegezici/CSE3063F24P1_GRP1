import java.util.ArrayList;
import java.util.Date;

public class DepartmentScheduler extends Staff {
    private String[][] courseSections;
    private String[] allClassrooms;

    public DepartmentScheduler() {
        super();
    }

    public DepartmentScheduler(String name, String surname) {
        super(name, surname);
    }

    // Constructor for Department Scheduler role.
    public DepartmentScheduler(String name, String surname, Date birthdate, char gender, String ssn,
                               String[][] courseSections, String[] allClassrooms) {
        super(name, surname, birthdate, gender, ssn);
        this.courseSections = courseSections;
        this.allClassrooms = allClassrooms;
    }

    // Assign time slot for specific section.
    public void assignTimeSlotToSection(CourseSection courseSection, TimeSlot timeSlot) {
        try {
            if (courseSection == null || timeSlot == null) {
                throw new IllegalArgumentException("CourseSection or TimeSlot cannot be null.");
            }
            timeSlot.assignTimeSlot(courseSection);
        } catch (Exception e) {
            System.err.println("Error assigning time slot to section: " + e.getMessage());
        }
    }

    // Assign the lecturer to specific section.
    public void assignLecturerToSection(CourseSection courseSection, Lecturer lecturer) {
        try {
            if (courseSection == null || lecturer == null) {
                throw new IllegalArgumentException("CourseSection or Lecturer cannot be null.");
            }
            courseSection.setLecturer(lecturer);
        } catch (Exception e) {
            System.err.println("Error assigning lecturer to section: " + e.getMessage());
        }
    }

    // Change capacity of specific section.
    public void manageCapacity(CourseSection courseSection, int newCapacity) {
        try {
            if (courseSection == null) {
                throw new IllegalArgumentException("CourseSection cannot be null.");
            }
            if (newCapacity < courseSection.getCapacity()) {
                throw new IllegalArgumentException("New capacity cannot be smaller than the old capacity.");
            }

            int oldCapacity = courseSection.getCapacity();
            courseSection.setCapacity(newCapacity);

            // Manage waitlist based on new capacity.
            manageWaitlist(courseSection, newCapacity - oldCapacity);
        } catch (Exception e) {
            System.err.println("Error managing capacity: " + e.getMessage());
        }
    }

    // Make changes in waitlist.
    public void manageWaitlist(CourseSection courseSection, int size) {
        try {
            if (courseSection == null) {
                throw new IllegalArgumentException("CourseSection cannot be null.");
            }
            ArrayList<Student> studentsToSection = courseSection.getWaitList();

            for (int i = 0; i < size && !studentsToSection.isEmpty(); i++) {
                Student student = studentsToSection.get(0);

                if (student.getTranscript() == null) {
                    throw new NullPointerException("Student's transcript cannot be null.");
                }

                // Add course to student's transcript.
                student.getTranscript().addCurrentCourse(courseSection.getParentCourse());

                // Remove student from the waitlist.
                studentsToSection.remove(0);
            }

            courseSection.setWaitList(studentsToSection);
        } catch (Exception e) {
            System.err.println("Error managing waitlist: " + e.getMessage());
        }
    }
}
