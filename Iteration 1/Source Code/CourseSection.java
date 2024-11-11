
public class CourseSection extends Course{
   private int sectionNumber;

   public CourseSection(){
      super();
   }

   public CourseSection(String courseId, String courseName){
      super(courseId, courseName);
   }

   public CourseSection(String courseId, String courseName, int credits, String prerequisiteID){
      super(courseId, courseName, credits, prerequisiteID);
   }

   public CourseSection(String courseId, String courseName, int credits, String prerequisiteID, int sectionNumber){
      super(courseId, courseName, credits, prerequisiteID);
      this.sectionNumber = sectionNumber;
   }
}
