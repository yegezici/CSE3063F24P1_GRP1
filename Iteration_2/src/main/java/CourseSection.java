import java.util.ArrayList;

public class CourseSection{
   private String sectionID;
   private TimeSlot timeSlot;
   private String classroom;
   private ArrayList<Student> waitList;
   private int capacity;
   private Lecturer lecturer;
   //constructors
   public CourseSection(){
      
   }
   public CourseSection(String sectionId, String classroom, int capacity, Lecturer lecturer){
     this.sectionID = sectionId;
     this.capacity = capacity;
     this.classroom = classroom;
     this.lecturer = lecturer;
   }
   
   
}