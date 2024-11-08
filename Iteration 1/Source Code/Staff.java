import java.util.Date;

public class Staff extends Person {
    protected String ssn;

    public Staff() {
        super();
    }

    public Staff(String name, String surname) {
        super(name, surname);
    }

    public Staff(String name, String surname, Date birthdate, char gender) {
        super(name, surname, birthdate, gender);
    }


    public Staff(String ssn) {
        this.ssn = ssn;
    }
}
