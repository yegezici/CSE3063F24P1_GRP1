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
        Scanner scan = new Scanner(System.in);
        boolean isLogged = true;
        Person currentUser = login();

        while (true) {
            showMenu(currentUser,true, scan);
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
             try (FileReader reader = new FileReader("src/main/java/parameters.json")) {
                 JSONObject jsonObject = (JSONObject) parser.parse(reader);
                 JSONArray studentsArray = (JSONArray) jsonObject.get("students");

                 for (Object studentObj : studentsArray) {
                     JSONObject student = (JSONObject) studentObj;
                     String userId = (String) student.get("userID");
                     String password = (String) student.get("password");
                     String name = (String) student.get("name");
                     String surname = (String) student.get("surname");
                     String studentID = (String) student.get("studentID");


                     if (userId.equals(enteredUserId) && password.equals(enteredPassword)) {
                         System.out.println("Login successful!");
                         System.out.println("Welcome, " + name + " " + surname);

                         Transcript transcript = createTranscript(studentID);
                         return new Student(name, surname, new Date(2002,9,18),'m',transcript,new Advisor(),studentID);
                     }
                 }
                 System.out.println("Invalid User ID or Password.");
             } catch (Exception e) {
                 e.printStackTrace();
             }

           return advisor;
         }
       private static Person login(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your User ID and Password");
        System.out.print("User ID: ");
        String enteredUserId = scan.nextLine();
        System.out.print("Password: ");
        String enteredPassword = scan.nextLine();
        return  checkIdandPassword(enteredUserId,enteredPassword);

    }
    private static boolean showMenu(Person currentUser, boolean isLogged, Scanner scan) {
        if (currentUser instanceof Student) {
            studentInterface(scan, (Student) currentUser);
        }else{
            login();
        }

        return true;
    }
    public static void studentInterface(Scanner scan, Student student){
        System.out.println("1. Transcript\n2. Register for course\n3. Log out");
        int choice = scan.nextInt();
        switch (choice) {
            case 1:
                student.getTranscript().showCompletedCourses();
                break;
            case 2:
                break;
            case 3:
                System.out.println("You are successfully logged out.\n");
                break;
        }
    }
        }
