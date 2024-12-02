public class TimeSlot {
   private String day;
   private String timeInterval;
   private String classroom; 

   public TimeSlot(){

   }
   public TimeSlot(String day, String timeInterval, String classroom){
    this.day = day;
    this.classroom = classroom;
    this.timeInterval = timeInterval;
   }

   public void assignTimeSlot(CourseSection courseSection){
     courseSection.getTimeSlots().add(this);
   }
  
  
  public String getDay(){
    return day;
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
  public void setDay(String day){
    this.day = day;
  }
}
