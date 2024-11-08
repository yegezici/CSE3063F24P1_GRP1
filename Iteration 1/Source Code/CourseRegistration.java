import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class CourseRegistration {
    public static void main(String[] args) {
        while (true) {
            // Login function
            if (login()) {
                System.out.println("Hello Hasan Erz. Check your transcript and register a course that is available for you.");
                break;
            }
        }
    }

    private static boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your User ID and Password");
        System.out.print("User ID: ");
        String enteredUserId = scanner.nextLine();
        System.out.print("Password: ");
        String enteredPassword = scanner.nextLine();

        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("C:\\Users\\AB\\eclipse-workspace\\login.json")) {
            // JSON dosyasını okuyun ve parse edin
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray usersArray = (JSONArray) jsonObject.get("users");

            for (Object userObj : usersArray) {
                JSONObject user = (JSONObject) userObj;
                String userId = (String) user.get("userID");
                String password = (String) user.get("password");
                
                if (userId.equals(enteredUserId) && password.equals(enteredPassword)) {
                    System.out.println("Login successful!");
                    return true;
                }
            }

            System.out.println("Wrong username or password. Please try again.");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
    
  /*  public Student createStudent(String filePath) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            
            JSONObject studentJson = (JSONObject) jsonParser.parse(reader);

            
            String name = (String) studentJson.get("name");
            String surname = (String) studentJson.get("surname");
            String studentID = (String) studentJson.get("studentID");
            char gender = ((String) studentJson.get("gender")).charAt(0);
            Date birthdate = new Date((Long) studentJson.get("birthdate")); 
            
            
            Transcript transcript = new Transcript(); 
            Advisor advisor = new Advisor(); 

          
            Student student = new Student(name, surname, birthdate, gender, transcript, advisor, studentID);
            return student;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null; 
        }
    } */
}
