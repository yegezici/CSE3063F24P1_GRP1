import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WeeklySchedule {
    Student student;
    ArrayList<TimeSlot>[] timeSlots;
    
    
    public WeeklySchedule(Student student){
        this.student = student;
        timeSlots = new ArrayList[5];
    }   


    public void printStudentWeeklySchedule() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (String day : days) {
            boolean dayPrinted = false;
            for (CourseSection section : student.getTranscript().getCurrentSections()) {
                if (section.getTimeSlots() != null) { 
                    for (TimeSlot timeSlot : section.getTimeSlots()) {
                        String timeInterval = timeSlot.getTimeInterval();
                        //System.out.println("Time slot interval: " + timeInterval); 
    
                        if (timeInterval != null && timeInterval.contains(day)) { 
                            if (!dayPrinted) {
                                System.out.println(day + ":");
                                dayPrinted = true;
                            }
                            String timeDetails = timeInterval.replace(day + " ", "");
                            String courseName = section.getParentCourse().getCourseName();
                            System.out.println("  " + courseName + "  " + timeDetails);
                        }
                    }
                } else {
                    System.out.println("No time slots available for section " + section.getSectionID());
                }
            }
        }
    }
    
    
    }   