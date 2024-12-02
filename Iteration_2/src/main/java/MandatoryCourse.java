public class MandatoryCourse extends Course {

    public MandatoryCourse(String courseId, String courseName, int credits, Course prerequisiteCourse, int semester) {
        super(courseId, courseName, credits, prerequisiteCourse, semester);
    }
    public MandatoryCourse(String courseId, String courseName, String grade, int credits) {
        super(courseId, courseName, grade, credits);
    }
    public MandatoryCourse(String courseId, String courseName, int credits) {
        super(courseId, courseName, credits);
    }

    @Override
    public String getCourseType() {
        return "Mandatory";
    }
}
