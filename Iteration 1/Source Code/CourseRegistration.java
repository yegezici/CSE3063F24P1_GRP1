
import java.util.Date;
import java.util.Scanner;

public class CourseRegistration {
   // Student student = new Student("Ali", "Kocyigit",new Date(2002, 9, 18),'M',new  )

    public static void main(String[] args) {
        String pass = "abc123";
        String userid = "o150121031";
        while (true) {
            Scanner scan = new Scanner(System.in);
            //Login usage
            login(userid, pass);
            if (userid.charAt(0) == 'o')
                System.out.println("Hello Hasan Erz.");
                System.out.println("1. Transcript\n2. Register for course\n3.Log out");
                int choice = scan.nextInt();
                switch (choice){
                    case 1: break;
                    case 2: break;
                    case 3:
                        System.out.println("You are successfully logged out.\n");
                        login(userid,pass);
                }




        }
    }
//login function
     private static void login(String userid, String password){
        while(true){
         Scanner idscan = new Scanner(System.in);
         Scanner passwordscan = new Scanner(System.in);
         System.out.println("Please enter your User ID and Password");
         System.out.println("User ID: ");
         String id = idscan.nextLine();
         System.out.println("Password: ");
         String pass = passwordscan.nextLine();
         if(!(userid.equals(id)) && !(pass.equals(password))){
             System.out.println("Wrong username or password. Please try again");
         } else
             break;

     }}

