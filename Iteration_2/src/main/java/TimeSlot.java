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
  public String getTimeInterval() {
    return timeInterval;
  }
  public String getClassroom() {
    return classroom;
  }
  public void setTimeInterval(String timeInterval) {
    this.timeInterval = timeInterval;
  }
  public void setClassroom(String classroom) {
    this.classroom = classroom;
  }
}
