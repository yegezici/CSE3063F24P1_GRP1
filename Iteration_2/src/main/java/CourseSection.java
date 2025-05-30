import java.sql.Time;
import java.util.ArrayList;

public class CourseSection {
   private String sectionID;
   private ArrayList<TimeSlot> timeSlots;
   private ArrayList<Student> waitList;
   private int capacity;
   private Lecturer lecturer;
   private Course parentCourse;
   private ArrayList<Student> currentStudents;

   // constructors
   public CourseSection() {
      this.timeSlots = new ArrayList<TimeSlot>();
      this.currentStudents = new ArrayList<Student>();
      this.waitList = new ArrayList<Student>();
   }

   public CourseSection(String sectionID, int capacity, Course parentCourse) {
      this();
      this.capacity = capacity;
      this.sectionID = sectionID;
      this.parentCourse = parentCourse;
   }

   public CourseSection(String sectionId, int capacity, Lecturer lecturer) {
      this();
      this.sectionID = sectionId;
      this.capacity = capacity;
      this.lecturer = lecturer;
   }

   public CourseSection(String sectionId, int capacity) {
      this();
      this.sectionID = sectionId;
      this.capacity = capacity;
   }

   public String getSectionID() {
      return this.sectionID;
   }

   public void setSectionID(String sectionID) {
      this.sectionID = sectionID;
   }

   public ArrayList<TimeSlot> getTimeSlots() {
      return timeSlots;
   }

   public void setTimeSlot(ArrayList<TimeSlot> timeSlots) {
      this.timeSlots = timeSlots;
   }

   public ArrayList<Student> getWaitList() {
      return waitList;
   }

   public void setWaitList(ArrayList<Student> waitList) {
      this.waitList = waitList;
   }

   public Lecturer getLecturer() {
      return lecturer;
   }

   public void setLecturer(Lecturer lecturer) {
      this.lecturer = lecturer;
   }

   public int getCapacity() {
      return this.capacity;
   }

   public void setCapacity(int capacity) {
      this.capacity = capacity;
   }

   public Course getParentCourse() {
      return parentCourse;
   }

   public void setParentCourse(Course parentCourse) {
      this.parentCourse = parentCourse;
   }

   public ArrayList<Student> getCurrentStudents() {
      return currentStudents;
   }

   public void addStudentToSection(Student currentStudent) {
      this.currentStudents.add(currentStudent);
   }

}