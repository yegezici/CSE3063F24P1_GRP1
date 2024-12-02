import java.util.ArrayList;
import java.util.Date;

public class StudentAffairsStaff extends Staff {
    private String affairID;


    public StudentAffairsStaff() {
        super();
    }

    public StudentAffairsStaff(String affairID, String name, String surname) {
        super(name, surname);
        this.affairID = affairID;
    }

   
    public StudentAffairsStaff(String name, String surname, Date birthdate, char gender, String ssn) {
        super(name, surname, birthdate, gender, ssn);
        
    }


    public Course createCourse(String courseName, String courseID, String courseType, int credits, int numberOfSections){
         Course course = null ;
        if(courseType.equals("m")){
            course = new MandatoryCourse(courseName, courseID, credits);
        } else if (courseType.equals("te"))
            course = new TechnicalElectiveCourse(courseID, courseName, credits);
        else if (courseType.equals("nte"))
            course = new NonTechnicalElectiveCourse(courseID, courseName, credits);
        
        course.setCourseSections(createCourseSection(numberOfSections));
       

   
        return course;
    }
   
    public ArrayList<CourseSection> createCourseSection(int numberOfSections, Course parentCourse){
        ArrayList<CourseSection> sections = new ArrayList<>();
        for(int i = 0; i < numberOfSections; i++){
            CourseSection courseSection = new CourseSection();
            courseSection.setParentCourse(parentCourse);
            courseSection.setSectionID(Integer.toString(i + 1));
            sections.add(courseSection);
        }
        return sections;
    }

    @Override
    public String getID() {
        return affairID;
    }


    public void setId(String id) {
        this.affairID = affairID;
    }

   
    @Override
    public String getName() {
        return name;
    }

  
    @Override
    public String getSurname() {
        return surname;
    }

    
    @Override
    public Date getBirthdate() {
        return birthdate;
    }

  
    @Override
    public char getGender() {
        return gender;
    }


}