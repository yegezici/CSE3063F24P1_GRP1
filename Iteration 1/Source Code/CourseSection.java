
public class CourseSection extends Course{
   private int sectionNumber;

   public CourseSection(){
      super();
   }

   public CourseSection(String courseId, String courseName){
      super(courseId, courseName);
   }

   public CourseSection(String courseId, String courseName, int credits){
      super(courseId, courseName, credits);
   }

   public CourseSection(String courseId, String courseName, int credits, int sectionNumber){
      super(courseId, courseName, credits);
      this.sectionNumber = sectionNumber;
   }
}
