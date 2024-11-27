import java.util.Date;

public class Affair extends Person {
    private String affairID;

    
    public Affair() {
        super();
    }

  
    public Affair(String affairID, String name, String surname) {
        super(name, surname);
        this.affairID = affairID;
        
    }

   
    public Affair(String id, String name, String surname, Date birthdate, char gender, String affairID) {
        super(name, surname, birthdate, gender, affairID);
        
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
