import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseRegistration {
    public static void main(String[] args) {

        ArrayList<Course> courses = loadCourses("C://Users//aliar//Desktop//OOP-Project-1//CSE3063F24P1_GRP1//Iteration 1//Source Code//JsonFiles//parameters.json");

        while (true) {
            // Login function
            if (login()) {
                
                //System.out.println("Hello Hasan Erz. Check your transcript and register a course that is available for you.");
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
        try (FileReader reader = new FileReader("C://Users//aliar//Desktop//OOP-Project-1//CSE3063F24P1_GRP1//Iteration 1//Source Code//JsonFiles//parameters.json")) {
            // JSON dosyasını okuyun ve parse edin
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray studentsArray = (JSONArray) jsonObject.get("students");

            for (Object userObj : studentsArray) {
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

    public static ArrayList<Course> loadCourses(String fileName) {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            // JSON dosyasını okuma
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(fileName);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
    
            // Dersleri çekme
            JSONArray coursesArray = (JSONArray) jsonObject.get("courses");
    
            for (Object obj : coursesArray) {
                JSONObject courseObj = (JSONObject) obj;
                String courseId = (String) courseObj.get("courseId");
                String name = (String) courseObj.get("name");
                Long credits = (Long) courseObj.get("credits"); // JSON'dan gelen veriyi Long olarak alırız
    
                // Course nesnesini oluşturup listeye ekleriz
                courses.add(new Course(courseId, name, credits.intValue()));
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }
}

