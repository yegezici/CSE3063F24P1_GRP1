import java.util.Date;

public abstract class Person {
    protected  String name;
    protected  String surname;
    protected  Date birthdate;
    protected  char gender;

    public Person(){

    }
    public Person(String name, String surname){
        this.name = name;
        this.surname = surname;
    }
    public Person(String name, String surname, Date birthdate, char gender){
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public String getName(){
        return name;

    };

    public String getSurname(){
        return surname;
    };

    public Date getBirthdate() {
        return birthdate;
    }

    public char getGender() {
        return gender;
    }
}
