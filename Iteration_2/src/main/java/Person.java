import java.util.Date;

public abstract class Person {
    private String name;
    private String surname;
    private Date birthdate;
    private char gender;
    private String ID;

    public Person() {}

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Person(String name, String surname, Date birthdate, char gender, String ID) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.ID = ID;
    }

    public String getNameField() {
        return name;
    }

    public String getSurnameField() {
        return surname;
    }

    public Date getBirthdateField() {
        return birthdate;
    }

    public char getGenderField() {
        return gender;
    }

    public String getIDField() {
        return ID;
    }

    public abstract String getName();

    public abstract String getSurname();

    public abstract Date getBirthdate();

    public abstract char getGender();

    public abstract String getID();
}
