import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class CourseRegistration {
    private static HashMap<String, Course> allCourses = new HashMap<>(); // Tüm kursları saklayacak hashmap

    public static void main(String[] args) {
        loadCourses(); // Kursları yükle
        boolean isLogged = true;
        Student currentStud = (Student) login();
        while (isLogged) {
            isLogged = showMenu(currentStud, isLogged);
        }
    }

    private static void loadCourses() {
        JSONParser parser = new JSONParser();
        String basePath = System.getProperty("user.dir");
        String filePath = Paths.get(basePath, "Iteration 1", "Source Code", "JsonFiles", "parameters.json").toString();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray coursesArray = (JSONArray) jsonObject.get("courses");

            for (Object obj : coursesArray) {
                JSONObject courseJson = (JSONObject) obj;
                String courseId = (String) courseJson.get("courseId");
                String courseName = (String) courseJson.get("name");
                int credits = ((Long) courseJson.get("credits")).intValue();

                Course course = new Course(courseId, courseName);
                allCourses.put(courseId, course); // courseId'yi anahtar olarak kullanarak ekle
            }
            System.out.println("Courses loaded successfully!");
        } catch (IOException | ParseException e) {
            System.out.println("Error loading courses from parameters.json.");
            e.printStackTrace();
        }
    }

    private static Course getCourseById(String courseId) {
        return allCourses.get(courseId);
    }

    private static Transcript createTranscript(String studentID) {
        JSONParser parser = new JSONParser();
        String basePath = System.getProperty("user.dir");
        String filePath = Paths.get(basePath, "Iteration 1", "Source Code", "JsonFiles", studentID + ".json").toString();
        
        ArrayList<Course> completedCourses = new ArrayList<>();
        ArrayList<Course> currentCourses = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            JSONArray coursesArray = (JSONArray) parser.parse(reader);
            for (Object courseObj : coursesArray) {
                JSONObject course = (JSONObject) courseObj;
                String courseId = (String) course.get("courseID");
                String courseName = (String) course.get("courseName");
                completedCourses.add(new Course(courseId, courseName));
            }
            return new Transcript(completedCourses, currentCourses);
        } catch (Exception e) {
            System.out.println("Error reading transcript for student ID: " + studentID);
            return null;
        }
    }

    private static Person checkIdandPassword(String enteredUserId, String enteredPassword) {
        Advisor advisor = new Advisor();
        JSONParser parser = new JSONParser();
        String basePath = System.getProperty("user.dir");
        String filePath = Paths.get(basePath, "Iteration 1", "Source Code", "JsonFiles", "parameters.json").toString();

        try (FileReader reader = new FileReader(filePath)) {
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
                   return new Student(name, surname, new Date(2002,9,18), 'm', transcript,studentID, new Advisor());
                }
            }
            System.out.println("Invalid User ID or Password.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return advisor;
    }

    private static boolean showMenu(Person currentUser, boolean isLogged) {
        System.out.println("1. Transcript\n2. Register for course\n3. Log out");
        Scanner scan = new Scanner(System.in);
        Student currentStud = (Student) currentUser;
        int choice = scan.nextInt();
        switch (choice) {
            case 1:
                currentStud.getTranscript().showCompletedCourses();
                break;
            case 2:
                break;
            case 3:
                System.out.println("You are successfully logged out.\n");
                return false;
            default:
                System.out.println("You entered an invalid choice. Please try again!");
                break;
        }
        return true;
    }

    private static Person login() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your User ID and Password");
        System.out.print("User ID: ");
        String enteredUserId = scan.nextLine();
        System.out.print("Password: ");
        String enteredPassword = scan.nextLine();
        return checkIdandPassword(enteredUserId, enteredPassword);
    }
}
