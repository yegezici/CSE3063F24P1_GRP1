public class NonTechnicalElectiveCourse extends Course {

    public NonTechnicalElectiveCourse(String courseId, String courseName, int credits, Course prerequisiteCourse) {
        super(courseId, courseName, credits, prerequisiteCourse);
    }
    public NonTechnicalElectiveCourse(String courseId, String courseName, String grade, int credits) {
        super(courseId, courseName, grade, credits);
    }
    public NonTechnicalElectiveCourse(String courseId, String courseName, int credits) {
        super(courseId, courseName, credits);
    }

    @Override
    public String getCourseType() {
        return "Non-Technical Elective";
    }
}
