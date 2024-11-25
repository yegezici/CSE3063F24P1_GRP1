import java.util.Date;

public class Staff extends Person {
    protected String ssn;
//constructors
    public Staff() {
        super();
    }

    public Staff(String name, String surname) {
        super(name, surname);
    }

    public Staff(String name, String surname, Date birthdate, char gender, String ssn) {
        super(name, surname, birthdate, gender);
        this.ssn = ssn;
    }
//Retrieves the Social Security Number (SSN) of the staff member
    public String getSsn(){
        return ssn;
    }
}
