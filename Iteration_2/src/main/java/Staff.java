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
    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public Date getBirthdate() {
        return this.birthdate;
    }

    @Override
    public char getGender() {
        return this.gender;
    } 
}
