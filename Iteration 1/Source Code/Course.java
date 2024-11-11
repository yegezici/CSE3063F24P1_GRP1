import java.util.ArrayList;

public class Course {
    private String courseId;
    private String courseName;
    private Lecturer lecturer;
    private ArrayList<Student> students;
    private ArrayList<CourseSection> courseSections;
    private Course prerequisiteCourse;
    private String prerequisiteID;
    private int credits;

    public Course() {
    }

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }
    
    public Course(String courseId, String courseName, int credits, String prerequisiteID) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.prerequisiteID=prerequisiteID;
    }


    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void addStudent(Student student) {
        try {
            if (student != null)
                students.add(student);
            else
                System.out.println("The student object is null");
        } catch (NullPointerException e) {
            System.out.println("The students list has not been initialized.");
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }
    }

    public Course getPrerequisiteCourse() {
        return prerequisiteCourse;
    }

    public void setPrerequisiteCourse(Course prerequisiteCourse) {
        this.prerequisiteCourse = prerequisiteCourse;
    }

    public String getPrerequisiteID() {
        return prerequisiteID;
    }

    public int getCredits() {
        return credits;
    }
}
