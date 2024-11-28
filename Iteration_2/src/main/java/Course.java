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
	public Course(String courseId, String courseName, int credits) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
    }
	
    //constructor
    public Course(String courseId, String courseName, int credits, String prerequisiteID) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.prerequisiteID=prerequisiteID;
        courseSections = new ArrayList<CourseSection>();
        
    }

//Retrieves the unique identifier of the course.
    public String getCourseId() {
        return courseId;
    }
//Retrieves the name of the course
    public String getCourseName() {
        return courseName;
    }
//Retrieves the lecturer responsible for teaching this course.
    public Lecturer getLecturer() {
        return lecturer;
    }
//If the student object is not null, it adds student objects to the course.
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
//Retrieves the prerequisite course ID required for enrollment in this course.
public String getPrerequisiteID() {
    return prerequisiteID;
}
//Retrieves the number of credits assigned to this course
    public int getCredits() {
        return credits;
    }

    public ArrayList<Student> getStudents() {
	    return students;
	}
	
	public void setStudents(ArrayList<Student> students) {
	    this.students = students;
	}

    public ArrayList<CourseSection> getCourseSections() {
        return courseSections;
    }

    public void setCourseSections(ArrayList<CourseSection> courseSections) {
        this.courseSections = courseSections;
    }






}