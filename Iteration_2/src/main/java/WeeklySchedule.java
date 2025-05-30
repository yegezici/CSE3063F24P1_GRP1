import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WeeklySchedule {
    Student student;

    public WeeklySchedule(Student student) {
        this.student = student;
    }

    public void printStudentWeeklySchedule() {
        String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        System.out.println(student.getTranscript().getCurrentCourses().size() + " courses taken this term.");
        for (String day : days) {
            boolean dayPrinted = false;
            for (CourseSection section : student.getTranscript().getCurrentSections()) {
                if (section.getTimeSlots() != null) {
                    for (TimeSlot timeSlot : section.getTimeSlots()) {
                        String timeInterval = timeSlot.getTimeInterval();
                        if (timeInterval != null && timeSlot.getDay().equals(day)) {
                            if (!dayPrinted) {
                                System.out.println(day + ":");
                                dayPrinted = true;
                            }
                            String courseName = section.getParentCourse().getCourseName();
                            System.out
                                    .println("  " + courseName + "  " + timeSlot.getClassroom() + "  " + timeInterval);
                        }
                    }
                } else {
                    System.out.println("No time slots available for section " + section.getSectionID());
                }
            }
        }
    }

}