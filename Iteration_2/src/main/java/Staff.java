import java.util.Date;

public class Staff extends Person {
    private String ssn;
//constructors
    public Staff() {
        super();
    }

    public Staff(String name, String surname) {
        super(name, surname);
    }

    public Staff(String name, String surname, Date birthdate, char gender, String ssn) {
        super(name, surname, birthdate, gender, ssn);
        
    }
//Retrieves the Social Security Number (SSN) of the staff member
    @Override
    public String getID(){
        return ssn;
    }
    @Override
    public String getName(){
        return super.getNameField();
    }

    @Override
    public String getSurname() {
        return super.getSurnameField();
    }

    @Override
    public Date getBirthdate() {
        return super.getBirthdateField();
    }

    @Override
    public char getGender() {
        return super.getGenderField();
    } 
}
