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
      
   }
   
   
}