import java.util.Date;

public abstract class Person {
     protected  String name;
     protected  String surname;
     protected  Date birthdate;
     protected  char Gender;


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
          return Gender;
     }
}
