import java.util.ArrayList;
import java.util.Date;

public class Advisor extends Lecturer {
    private ArrayList<Student> students;

    public Advisor() {
        super();
    }
    public Advisor(String name, String surname){
      super(name,surname);
    }
//constructor
    public Advisor(String name, String surname, Date birthdate, char gender, String ssn, ArrayList<Course> courses, ArrayList<Student> students){
        super(name, surname, birthdate, gender, ssn, courses);
        this.students = students;
    }
//This method adds the course approved by the advisor to the list of courses the student is currently taking and deletes it from the list of those waiting for approval.
    public void approveCourse(Student student, CourseSection courseSection) {
        try {
            if(!(courseSection.getCapacity() > courseSection.getCurrentStudents().size() )){
                    courseSection.getWaitList().add(student);
            }else{
            student.getTranscript().addCurrentCourse(courseSection.getParentCourse());
            student.getTranscript().addCurrentSection(courseSection);
            student.getTranscript().deleteFromWaitedCourse(courseSection.getParentCourse());}
            student.getTranscript().deleteFromWaitedSections(courseSection);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
//This method removes the course from the student's waiting list if the advisor does not approve it.
    public void rejectCourse(Student student, Course course) {
        try {
            student.getTranscript().deleteFromWaitedCourse(course);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
//This method adds a new student object to the list of students that the advisor is an advisor for.
    public void addStudent(Student student) {
        try {
            students.add(student);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
//Retrieves the list of students advised by this advisor
    public ArrayList<Student> getStudents() {
        return students;
    }
  
    // If there is conflict between student's sections, return false; else true. 
    public boolean checkSectionConflict(Student student, CourseSection courseSection){
        boolean isConflict = false;
        ArrayList<CourseSection> courseSectionsOfStudent = student.getTranscript().getCurrentSections();

        for(int i = 0; i < courseSection.getTimeSlots().size(); i++){
            TimeSlot timeSlot = courseSection.getTimeSlots().get(i);
            for(int k = 0; k < courseSectionsOfStudent.size(); k++){
                for(int z = 0; z < courseSectionsOfStudent.get(k).getTimeSlots().size(); z++){
                    String sectionTimeInterval = courseSectionsOfStudent.get(k).getTimeSlots().get(z).getTimeInterval();
                    if(timeSlot.getTimeInterval().equals(sectionTimeInterval)){
                        return false;
                    }
                }
            }
        }

        return !isConflict;
    }
  
}
