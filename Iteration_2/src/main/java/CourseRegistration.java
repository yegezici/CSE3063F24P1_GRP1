import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class CourseRegistration {
    ArrayList<Student> students;
    ArrayList<CourseSection> courseSections;
    ArrayList<Course> courses;
    ArrayList<String> classrooms;

    public CourseRegistration() {
        students = JsonManagement.getInstance().getStudents();
        courses = JsonManagement.getInstance().getCourses();
        courseSections = JsonManagement.getInstance().getCourseSections();
        classrooms = JsonManagement.getInstance().getClassrooms();

    }

    /*
     * Main method to start the course registration process.
     * It loads the courses, handles the login process,
     * and displays the menu to the user until they are logged in.
     */
    public void init() {

        while (true) {
            Person currentUser = login();
            if (currentUser == null)
                continue;
            if (currentUser instanceof Lecturer && !(currentUser instanceof Advisor))
                break;
            UserInterface userInterface = null;
            while (true) {
                if (currentUser instanceof Student)
                    userInterface = new StudentInterface((Student) currentUser, courses);
                // createArrayList((Student) currentUser);
                else if (currentUser instanceof Advisor)
                    userInterface = new AdvisorInterface((Advisor) currentUser);
                else if (currentUser instanceof StudentAffairsStaff)
                    userInterface = new StudentAffairsStaffInterface((StudentAffairsStaff) currentUser, courses, courseSections);
                else if(currentUser instanceof DepartmentScheduler)
                    userInterface = new DepartmentSchedulerInterface((DepartmentScheduler)(currentUser), courseSections);
                if (userInterface.showMenu()) {
                saveStudents();
                break;
                }
            }
        }

    }

    public void saveStudents() {
        for (Student student : students)

            JsonManagement.getInstance().saveStudent(student);

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
    private Person checkIdandPassword(String enteredUserId, String enteredPassword) {
        Person returnObject = null;
        if (enteredUserId.isEmpty() || enteredPassword.isEmpty()) {
            System.out.println("Please enter user id and password.");
            return null;
        } else if (enteredUserId.charAt(0) != 'o' && enteredUserId.charAt(0) != 'l' && enteredUserId.charAt(0) != 'a'
                && enteredUserId.charAt(0) != 'd') {
            System.out.println("If you are a student, you must add \"o\" as the first letter of your student number\n" +
                    "If you are a lecturer, you must add \"l\"");
            return null;
        } else {
            String filePath = "iteration_2/src/main/java/parameters.json";
            switch (enteredUserId.charAt(0)) {
                case 'o':
                    if (checkIdAndPasswordOfPerson(enteredUserId, enteredPassword, filePath, "students"))
                        returnObject = JsonManagement.getInstance().getStudentByID(enteredUserId.substring(1));
                    break;
                case 'l':
                    if (checkIdAndPasswordOfPerson(enteredUserId, enteredPassword, filePath, "advisors"))
                        returnObject = JsonManagement.getInstance().getAdvisorByUserID(enteredUserId.substring(1));
                    break;
                case 'a':
                    if (checkIdAndPasswordOfPerson(enteredUserId, enteredPassword, filePath, "studentAffairsStaffs"))
                        returnObject = JsonManagement.getInstance()
                                .getstudentAffairsStaffByID(enteredUserId.substring(1));
                    break;
                case 'd':
                    if (checkIdAndPasswordOfPerson(enteredUserId, enteredPassword, filePath, "departmentSchedulers"))
                        returnObject = JsonManagement.getInstance()
                                .getDepartmentSchedulerByID(enteredUserId.substring(1));
                    break;
                default:

                    break;
            }

        }
        if (returnObject == null)
            System.out.println("Wrong User Id or Password");
        return returnObject;
    }

    public boolean checkIdAndPasswordOfPerson(String enteredUserId, String enteredPassword, String filePath,
            String personType) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);
            JSONArray studentsArray = (JSONArray) jsonData.get(personType);
            for (Object studentObj : studentsArray) {
                JSONObject studentJson = (JSONObject) studentObj;
                String jsonStudentID = (String) studentJson.get("userID");
                if (jsonStudentID.equals(enteredUserId.substring(1))) {
                    String password = (String) studentJson.get("password");
                    if (!password.equals(enteredPassword)) {
                        System.out.println("Wrong password");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("There is an exception in checKIdAndPasswordOfPerson method.");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /*
     * Handles the login process where the user enters their User ID and Password.
     * The method prompts the user to either log in or exit the program.
     * If the user opts to log in, the entered credentials are validated by the
     * `checkIdandPassword` method.
     * If the credentials are correct, the corresponding `Person` object (either a
     * student or an advisor) is returned.
     * If the user chooses to exit, the program terminates.
     */
    private Person login() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome!\n1-   Login\nPress any key to exit");
        if (scan.nextLine().equals("1")) {
            Scanner getUserInfo = new Scanner(System.in);
            System.out.println("Please enter your User ID and Password");
            System.out.print("User ID: ");
            String enteredUserId = getUserInfo.nextLine();
            System.out.print("Password: ");
            String enteredPassword = getUserInfo.nextLine();
            return checkIdandPassword(enteredUserId, enteredPassword);

        } else {
            System.out.println("Program has been terminated successfully.");
            return new Lecturer();
        }
    }

}