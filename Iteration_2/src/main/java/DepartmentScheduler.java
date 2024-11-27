import java.util.Date;

public class DepartmentScheduler extends Staff{
    private String[][] courseSections;
    private String[] allClassrooms;

    public DepartmentScheduler(){
        super();
    }

    public DepartmentScheduler(String name, String surname){
        super(name, surname);
    }
    //Constructor for Department Scheduler role.
    public DepartmentScheduler(String name, String surname, Date birthdate, char gender, String ssn,
                               String courseSections[][], String allClassrooms[]){
        super(name, surname, birthdate, gender, ssn);
        this.courseSections = courseSections;
        this.allClassrooms = allClassrooms;
    }

    //Assign time slot for spesific section.
    public void assignTimeSlotToSection(){

    }

    //Assign the lecturer to spesific section.
    public void assignLecturerToSection(){
        
    }

    //Change capacity of spesific section.  
    public void manageCapacity(){
        
    }

    //Make changes in waitlist.
    public void manageWaitlist(){
        
    }

}
