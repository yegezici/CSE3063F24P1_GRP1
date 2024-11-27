import java.util.ArrayList;

public class CourseSection{
   private String sectionID;
   private TimeSlot timeSlot;
   private ArrayList<Student> waitList;
   private int capacity;
   private Lecturer lecturer;
   //constructors
   public CourseSection(){
      
   }
   public CourseSection(String sectionId, int capacity, Lecturer lecturer){
     this.sectionID = sectionId;
     this.capacity = capacity;
     this.lecturer = lecturer;
   }

   public void assignLecturer(){
      //sonra doldurulacak
   }


   public String getSectionID(){
      return this.sectionID;
   }
   public void setSectionID(String sectionID){
      this.sectionID = sectionID;
   }
   public TimeSlot getTimeSlot() {
      return timeSlot;
   }
   public void setTimeSlot(TimeSlot timeSlot) {
      this.timeSlot = timeSlot;
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
   public int capacity(){
      return this.capacity;
   }
   public void setCapacity(int capacity){
      this.capacity = capacity;
   }
   
   
   
}