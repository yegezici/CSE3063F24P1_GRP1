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
    //constructor
    public Person(String name, String surname, Date birthdate, char gender){
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
    }
//Retrieves the name of the person
    public String getName(){
        return name;

    };
//Retrieves the surname of the person
    public String getSurname(){
        return surname;
    };
//Retrieves the birth date of the person
    public Date getBirthdate() {
        return birthdate;
    }
//Retrieves the gender of the person
    public char getGender() {
        return gender;
    }
}
