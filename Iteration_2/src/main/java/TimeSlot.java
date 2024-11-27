public class TimeSlot {
   private String timeInterval;
   private String classroom; 

   public TimeSlot(){

   }
   public TimeSlot(String timeInterval, String classroom){
    this.classroom = classroom;
    this.timeInterval = timeInterval;
   }

   public void assignTimeSlot(CourseSection courseSection){
     courseSection.getTimeSlots().add(this);
     
   }
}
