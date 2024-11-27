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
