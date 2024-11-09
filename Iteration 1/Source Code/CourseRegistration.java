import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class   CourseRegistration {
    public static void main(String[] args) {

        //Student student = new Student("Hasan","Erz",new Date(2002,9,18),'m',"150121031",new Advisor(),new Transcript());
        while (true) {
            // Login function
            Scanner scan = new Scanner(System.in);
            System.out.println("Please enter your User ID and Password");
            System.out.print("User ID: ");
            String enteredUserId = scan.nextLine();
            System.out.print("Password: ");
            String enteredPassword = scan.nextLine();
            Student currentStud = (Student) checkIdandPassword(enteredUserId,enteredPassword);

                //menu options with switch case
                System.out.println("1. Transcript\n2. Register for course\n3. Log out");
                int choice = scan.nextInt();
                switch (choice) {
                    case 1:
                        currentStud.getTranscript().showCompletedCourses();
                        break;
                    case 2:
                        break;
                    case 3:
                        System.out.println("You are successfully logged out.\n");
                        break;
                }


        }
    }
    private static Transcript createTranscript(String studentID) {
        JSONParser parser = new JSONParser();
        String filePath = "src/main/java/" + studentID + ".json";
        ArrayList<Course> completedCourses = new ArrayList<>();
        ArrayList<Course> currentCourse = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JSONArray coursesArray = (JSONArray) parser.parse(reader);
            for (Object courseObj : coursesArray) {
                JSONObject course = (JSONObject) courseObj;
                String courseId = (String) course.get("courseID");
                String courseName = (String) course.get("courseName");
                completedCourses.add(new Course(courseId, courseName));
            }
            return new Transcript(completedCourses, currentCourse);
        } catch (Exception e) {
            System.out.println("Error reading transcript for student ID: " + studentID);
            return null;
        }
    }
         private static Person checkIdandPassword(String enteredUserId, String enteredPassword){
             Advisor advisor = new Advisor();
             JSONParser parser = new JSONParser();
             try (FileReader reader = new FileReader("src/main/java/login.json")) {
                 // JSON dosyasını okuyun ve parse edin
                 JSONObject jsonObject = (JSONObject) parser.parse(reader);
                 JSONArray usersArray = (JSONArray) jsonObject.get("users");

                 for (Object userObj : usersArray) {
                     JSONObject user = (JSONObject) userObj;
                     String userId = (String) user.get("userID");
                     String password = (String) user.get("password");

                     if (userId.equals(enteredUserId) && password.equals(enteredPassword)) {
                         System.out.println("Login successful!");
                         String StudentID = (String) user.get("StudentID");
                         Transcript transcript;
                         transcript = createTranscript(StudentID);
                         return new Student("Hasan", "Erz", new Date(2002,9,18),'m',"150121031",new Advisor(),transcript);
                     }
                 }

                 System.out.println("Wrong username or password. Please try again.");
             } catch (IOException | ParseException e) {
                 e.printStackTrace();
             }
           return advisor;
         }
        }
