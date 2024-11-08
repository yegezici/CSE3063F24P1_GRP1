import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
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
        try (FileReader reader = new FileReader("C://Users//aliar//Desktop//OOP-Project-1//CSE3063F24P1_GRP1//Iteration 1//Source Code//login.json")) {
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
}
