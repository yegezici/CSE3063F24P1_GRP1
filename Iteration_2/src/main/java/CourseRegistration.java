import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CourseRegistration {
    /*
     * Main method to start the course registration process. 
     * It loads the courses, handles the login process, 
     * and displays the menu to the user until they are logged in.
     */
    public static void main(String[] args) {
        JsonManagement jsonManager = new JsonManagement();
        ArrayList<Course> courses = jsonManager.loadCourses();
        boolean isLogged = true;

        while (true) {
            Person currentUser = login(courses);
            isLogged = showMenu(currentUser, isLogged, courses);
            while (!isLogged) {
                isLogged = showMenu(currentUser, isLogged, courses);
            }
        }
    }
   /**
 * Adds a course to the student's "waitedCourses" list in the student's JSON file.
 * The method checks if the "waitedCourses" array exists in the student's data. If not, it initializes it.
 * It then creates a JSON object for the course, adds it to the "waitedCourses" array, and writes the updated data back to the file.
 */
    protected static void addWaitedCourse(Student student, Course course) {
        JSONParser parser = new JSONParser();
        String filePath = "iteration_2/src/main/java/" + student.getStudentID() + ".json";

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject studentData = (JSONObject) parser.parse(reader);

            // Check if "waitedCourses" exists and is initialized
            JSONArray waitedCourses = (JSONArray) studentData.get("waitedCourses");
            if (waitedCourses == null) {
                waitedCourses = new JSONArray(); // Initialize as an empty array if null
                studentData.put("waitedCourses", waitedCourses);
            }

            // Create a new JSON object for the course and add it to "waitedCourses"
            JSONObject newCourse = new JSONObject();
            newCourse.put("courseID", course.getCourseId());
            newCourse.put("courseName", course.getCourseName());
            waitedCourses.add(newCourse);

            // Write the updated data back to the file
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(studentData.toJSONString());
                writer.flush();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
 * Accepts a course request for a student by moving the specified course from the "waitedCourses" list to the "currentCourses" list.
 * The method searches for the course in the "waitedCourses" array, removes it from there, and adds it to the "currentCourses" array.
 * It also updates the student's transcript to reflect the changes.
 * After making the changes, the updated data is written back to the student's JSON file.
 */
   /* protected static void acceptCourseRequest(Student student, Course course) {
        String filePath = "iteration_2/src/main/java/" + student.getStudentID() + ".json";

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject studentData = (JSONObject) parser.parse(reader);
            JSONArray waitedCourses = (JSONArray) studentData.get("waitedCourses");
            JSONArray currentCourses = (JSONArray) studentData.get("currentCourses");

           
            JSONObject courseToMove = null;
            for (Object obj : waitedCourses) {
                JSONObject waitedCourse = (JSONObject) obj;
                if (waitedCourse.get("courseID").equals(course.getCourseId())) {
                    courseToMove = waitedCourse;
                    break;
                }
            }
            if (courseToMove != null) {
                
                currentCourses.add(courseToMove);
                
                waitedCourses.remove(courseToMove);
                student.getTranscript().getWaitedCourses().remove(course);
            }

            
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(studentData.toJSONString());
                writer.flush();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }*/
/**
 * Checks the entered user ID and password to authenticate a student or advisor.
 * Based on the first character of the entered user ID, the method determines whether the user is a student (starting with 'o') or a advisor (starting with 'l').
 * It then checks the credentials against the data in the "parameters.json" file.
 * If the credentials are correct, it returns the corresponding student or advisor object.
 * If the user ID or password is incorrect, appropriate error messages are displayed.
 */
    private static Person checkIdandPassword(String enteredUserId, String enteredPassword, ArrayList<Course> courses) {
        Person returnObject = null;
        JsonManagement jsonManager = new JsonManagement();
        if (enteredUserId.isEmpty() || enteredPassword.isEmpty()) {
            System.out.println("Please enter user id and password.");
            return null;
        } else if (enteredUserId.charAt(0) != 'o' && enteredUserId.charAt(0) != 'l') {
            System.out.println("If you are a student, you must add \"o\" as the first letter of your student number\n" +
                    "If you are a lecturer, you must add \"l\"");
            return null;
        } else {
            JSONParser parser = new JSONParser();
            String filePath = "iteration_2/src/main/java/parameters.json";

            if (enteredUserId.charAt(0) == 'o') {
                try (FileReader reader = new FileReader(filePath)) {
                    JSONObject jsonData = (JSONObject) parser.parse(reader);
                    JSONArray studentsArray = (JSONArray) jsonData.get("students");
                    for (Object studentObj : studentsArray) {
                        JSONObject studentJson = (JSONObject) studentObj;
                        String jsonStudentID = (String) studentJson.get("studentID");
                        if (jsonStudentID.equals(enteredUserId.substring(1))) {
                            String userID = (String) studentJson.get("userID");
                            String password = (String) studentJson.get("password");
                            if (!password.equals(enteredPassword)) {
                                System.out.println("Wrong password");
                                return null;
                            }
                            returnObject = jsonManager.getStudentByID(enteredUserId.substring(1), courses);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else {

                try (FileReader reader = new FileReader(filePath)) {
                    JSONObject jsonData = (JSONObject) parser.parse(reader);
                    JSONArray studentsArray = (JSONArray) jsonData.get("advisors");
                    for (Object studentObj : studentsArray) {
                        JSONObject studentJson = (JSONObject) studentObj;
                        String jsonStudentID = (String) studentJson.get("userID");
                        if (jsonStudentID.equals(enteredUserId)) {
                            String password = (String) studentJson.get("password");

                            if (!password.equals(enteredPassword)) {
                                System.out.println("Wrong password");
                                return null;
                            }
                            returnObject = jsonManager.getAdvisorByUserID(enteredUserId, courses);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        if (returnObject == null)
            System.out.println("Wrong User Id or Password");
        return returnObject;
    }
  
/**
 * Handles the login process where the user enters their User ID and Password.
 * The method prompts the user to either log in or exit the program.
 * If the user opts to log in, the entered credentials are validated by the `checkIdandPassword` method.
 * If the credentials are correct, the corresponding `Person` object (either a student or an advisor) is returned.
 * If the user chooses to exit, the program terminates.
 */
    private static Person login(ArrayList<Course> courses) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome!\n1-   Login\nPress any key to exit");
        if (scan.nextLine().equals("1")) {
            Scanner getUserInfo = new Scanner(System.in);
            System.out.println("Please enter your User ID and Password");
            System.out.print("User ID: ");
            String enteredUserId = getUserInfo.nextLine();
            System.out.print("Password: ");
            String enteredPassword = getUserInfo.nextLine();
            return checkIdandPassword(enteredUserId, enteredPassword, courses);

        } else {
            System.out.println("Program has been terminated successfully.");
            System.exit(0);
            return null;
        }
    }
/*
 * Displays the menu options to the current user (either a Student or an Advisor) based on their role.
 * It calls the respective interface method (`studentInterface` or `advisorInterface`) depending on the user's type.
 * The method ensures the correct interface is shown and allows the user to log out or perform various operations.
 */
    private static boolean showMenu(Person currentUser, boolean isLogged, ArrayList<Course> courses) {
        boolean loggedOut = false;
        if (currentUser instanceof Student) {
            loggedOut = studentInterface((Student) currentUser, courses);
        } else if (currentUser instanceof Advisor) {
            loggedOut = advisorInterface((Advisor) currentUser, courses);
        } else {
            loggedOut = true;
        }
        return loggedOut;
    }

}
