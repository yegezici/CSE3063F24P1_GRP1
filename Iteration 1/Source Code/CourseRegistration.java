import java.util.Scanner;

public class CourseRegistration {
    //Student student = new Student("Ali", "Kocyigit", )
    public static void main(String[] args) {
        String pass = "abc123";
        String userid = "o150121031";
        while (true) {
            //Login function
            login(userid, pass);
            if (userid.charAt(0) == 'o')
                System.out.println("Hello Hasan Erz. Check your transcript and register a course that are available for you.");


        }
    }

     private static void login(String userid, String password){
         Scanner idscan = new Scanner(System.in);
         Scanner passwordscan = new Scanner(System.in);
         System.out.println("Please enter your User ID and Password");
         System.out.println("User ID: ");
         String id = idscan.nextLine();
         System.out.println("Password: ");
         String pass = passwordscan.nextLine();
         if(!(userid.equals(id)) && !(pass.equals(password)))
             System.out.println("Wrong username or password. Please try again");
     }





}
