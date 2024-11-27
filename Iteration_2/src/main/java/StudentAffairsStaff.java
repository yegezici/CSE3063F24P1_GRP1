import java.util.ArrayList;

public class StudentAffairsStaff  {

    public StudentAffairsStaff(){

    }

    public Course createCourse(){

    }
    public void removeCourses(){
        
    }
    public ArrayList<CourseSection> createCourseSection(int numberOfSections){
        ArrayList<CourseSection> sections = new ArrayList<>();
        for(int i = 0; i < numberOfSections; i++){
            sections.add(new CourseSection());
        }
        return sections;
    }


}
