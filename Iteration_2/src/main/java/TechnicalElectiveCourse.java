public class TechnicalElectiveCourse extends Course {

    public TechnicalElectiveCourse(String courseId, String courseName, int credits, Course prerequisiteCourse, int semester) {
        super(courseId, courseName, credits, prerequisiteCourse, semester);
    }
    public TechnicalElectiveCourse(String courseId, String courseName, String grade, int credits) {
        super(courseId, courseName, grade, credits);
    }
    public TechnicalElectiveCourse(String courseId, String courseName, int credits) {
        super(courseId, courseName, credits);
    }

    @Override
    public String getCourseType() {
        return "Technical Elective";
    }
}
