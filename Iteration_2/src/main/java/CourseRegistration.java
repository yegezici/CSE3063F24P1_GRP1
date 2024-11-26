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
    ArrayList<Student> students;
    JsonManagement jsonManager;
    public CourseRegistration() {
      jsonManager = new JsonManagement();
      students = jsonManager.students;
    }

    /*
     * Main method to start the course registration process.
     * It loads the courses, handles the login process,
     * and displays the menu to the user until they are logged in.
     */
    public void init() {
        ArrayList<Course> courses = jsonManager.courses;
        boolean isLogged = true;

        while (true) {
            Person currentUser = login(courses);
            if(currentUser == null)
                continue;
            if(currentUser instanceof Lecturer && !(currentUser instanceof Advisor))
                break;
            UserInterface userInterface = null;
            while (isLogged) {
                if (currentUser instanceof Student)
                    userInterface = new StudentInterface((Student) currentUser, courses);
                    //createArrayList((Student) currentUser);
                else 
                    userInterface = new AdvisorInterface((Advisor) currentUser);
                if (userInterface.showMenu()) {
                    saveStudents();
                    break;
                }
            }
        }
        
    }

    public void saveStudents(){
        for (Student student : students)
            jsonManager.saveStudent(student);
    }

    /**
     * Checks the entered user ID and password to authenticate a student or advisor.
     * Based on the first character of the entered user ID, the method determines
     * whether the user is a student (starting with 'o') or a advisor (starting with
     * 'l').
     * It then checks the credentials against the data in the "parameters.json"
     * file.
     * If the credentials are correct, it returns the corresponding student or
     * advisor object.
     * If the user ID or password is incorrect, appropriate error messages are
     * displayed.
     */
    private  Person checkIdandPassword(String enteredUserId, String enteredPassword, ArrayList<Course> courses) {
        Person returnObject = null;
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
                            String password = (String) studentJson.get("password");
                            if (!password.equals(enteredPassword)) {
                                System.out.println("Wrong password");
                                return null;
                            }
                            returnObject = jsonManager.getStudentByID(enteredUserId.substring(1));
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
                            returnObject = jsonManager.getAdvisorByUserID(enteredUserId);
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
     * If the user opts to log in, the entered credentials are validated by the
     * `checkIdandPassword` method.
     * If the credentials are correct, the corresponding `Person` object (either a
     * student or an advisor) is returned.
     * If the user chooses to exit, the program terminates.
     */
    private  Person login(ArrayList<Course> courses) {
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
            return new Lecturer();
        }
    }
    private void createArrayList(Student currentStudent){
        int size = students.size();
        boolean isSame = false;
        for(int k = 0; k < size ; k++){
            if(currentStudent.getStudentID().equals(students.get(k).getStudentID())){
                isSame = true;
                break;
            }

        }
        if(!(isSame)){
            students.add(currentStudent);
        }
    }

}