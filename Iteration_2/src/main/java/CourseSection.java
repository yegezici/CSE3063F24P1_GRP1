import java.util.ArrayList;

public class CourseSection{
   private String sectionID;
   private ArrayList<TimeSlot> timeSlots;
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
   public int getCapacity(){
      return this.capacity;
   }
   public void setCapacity(int capacity){
      this.capacity = capacity;
   }
   
   
   
}