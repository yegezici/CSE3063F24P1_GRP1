import java.util.Date;

public abstract class Person {
    protected  String ID;
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
    public Person(String name, String surname, Date birthdate, char gender, String ID){
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.ID = ID;
    }
//Retrieves the name of the person
    public abstract String getName();
//Retrieves the surname of the person
    public abstract String getSurname();
//Retrieves the birth date of the person
    public abstract Date getBirthdate();
//Retrieves the gender of the person
    public abstract char getGender();
//Retrieves the personID of the person
    public abstract String getID();

}
