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

        ArrayList<Course> courses = loadCourses();
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
 * Loads all available courses from a JSON file and returns them as an ArrayList of Course objects.
 * The method reads a JSON file, parses it, and extracts course details such as course ID, name, credits, and prerequisites.
 * It then creates Course objects and adds them to the list.
 * If the loading process is successful, it also calls the addPrerequisite method to handle course prerequisites.
 */
    private static ArrayList<Course> loadCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String filePath = "src/main/java/parameters.json";

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray coursesArray = (JSONArray) jsonObject.get("courses");

            for (Object obj : coursesArray) {
                JSONObject courseJson = (JSONObject) obj;
                String courseId = (String) courseJson.get("courseId");
                String courseName = (String) courseJson.get("name");
                int credits = ((Long) courseJson.get("credits")).intValue();
                String prerequisite = (String) courseJson.get("prerequisite");

                Course course = new Course(courseId, courseName, credits, prerequisite);
                courses.add(course);
            }
            addPrerequisite(courses);



            System.out.println("Courses loaded successfully!");
        } catch (IOException | ParseException e) {
            System.out.println("Error loading courses from parameters.json.");
            e.printStackTrace();
        }
        return courses;
    }
/**
 * Retrieves an Advisor object based on the provided userID by reading data from a JSON file.
 * The method searches for an advisor in the JSON data, extracts their personal details, and creates an Advisor object.
 * It also retrieves the list of students and courses assigned to the advisor.
 * If the advisor is found, it returns the Advisor object; otherwise, it returns null.
 */
    public static Advisor getAdvisorByUserID(String userID, ArrayList<Course> courses) {
        JSONParser parser = new JSONParser();
        String filePath = "src/main/java/parameters.json";
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);
            JSONArray advisorsArray = (JSONArray) jsonData.get("advisors");
            for (Object advisorObj : advisorsArray) {
                JSONObject advisorJson = (JSONObject) advisorObj;
                String advisorUserID = (String) advisorJson.get("userID");
                if (advisorUserID.equals(userID)) {
                    String name = (String) advisorJson.get("name");
                    String surname = (String) advisorJson.get("surname");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String date = (String) advisorJson.get("birthdate");
                    Date birthdate = formatter.parse(date);
                    char gender = ((String) advisorJson.get("gender")).charAt(0);
                    String ssn = (String) advisorJson.get("ssn");
                    // Öğrenci nesnelerini oluşturmak için studentID listesini al
                    JSONArray studentIDs = (JSONArray) advisorJson.get("students");
                    ArrayList<Student> students = new ArrayList<>();
                    for (Object studentIDObj : studentIDs) {
                        String studentID = (String) studentIDObj;
                        Student student = getStudentByID(studentID, courses);
                        if (student != null) {
                            students.add(student);
                        }
                    }
                    // Advisor'ın verdiği derslerin objelerini oluştur.
                    JSONArray courseIDs = (JSONArray) advisorJson.get("courses");
                    ArrayList<Course> coursesOfLecturer = new ArrayList<>();
                    for (Object courseIDObj : courseIDs) {
                        String courseID = (String) courseIDObj;
                        for (Course course : courses) {
                            if (course.getCourseId().equals(courseID)) {
                                coursesOfLecturer.add(course);
                            }
                        }
                    }
                    return new Advisor(name, surname, birthdate, gender, ssn, coursesOfLecturer, students);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Advisor with userID: " + userID + " not found.");
        return null;
    }

    /**
 * Retrieves a Student object based on the provided studentID by reading data from a JSON file.
 * The method searches for a student in the JSON data, extracts their personal details, 
 * and creates a Student object. It also retrieves the student's transcript and advisor ID.
 * If the student is found, it returns the Student object; otherwise, it returns null.
 */
    private static Student getStudentByID(String studentID, ArrayList<Course> courses) {
        JSONParser parser = new JSONParser();
        String filePath = "src/main/java/parameters.json";
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);
            JSONArray studentsArray = (JSONArray) jsonData.get("students");
            for (Object studentObj : studentsArray) {
                JSONObject studentJson = (JSONObject) studentObj;
                String jsonStudentID = (String) studentJson.get("studentID");
                if (jsonStudentID.equals(studentID)) {
                    try {
                        String userID = (String) studentJson.get("userID");
                        String password = (String) studentJson.get("password");
                        String name = (String) studentJson.get("name");
                        String surname = (String) studentJson.get("surname");
                        String date = (String) studentJson.get("birthdate");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date birthdate = formatter.parse(date);
                        char gender = ((String) studentJson.get("gender")).charAt(0);
                        String advisorID = (String) studentJson.get("advisorID");
                        Transcript transcript = createTranscript(studentID, courses);
                        return new Student(name, surname, birthdate, gender, transcript, studentID, advisorID);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Student object has not been initialized");
        return null;
    }
/**
 * Creates a Transcript object for a student based on the student's JSON file data.
 * The method parses the student's JSON file to retrieve the lists of completed, current, 
 * and waited courses, then creates and returns a Transcript object.
 * It matches course names from the student's data with the available courses to populate
 * the respective course lists.
 * If the file cannot be read or parsed, it returns null.
 */
    private static Transcript createTranscript(String studentID, ArrayList<Course> courses) {
        JSONParser parser = new JSONParser();
        String filePath = "src/main/java/" + studentID + ".json";

        ArrayList<Course> completedCourses = new ArrayList<>();
        ArrayList<Course> currentCourses = new ArrayList<>();
        ArrayList<Course> waitedCourses = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            // Parse the JSON as an object (not an array)
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // Access and parse "completedCourses" array
            JSONArray completedCoursesArray = (JSONArray) jsonObject.get("completedCourses");
            if (completedCoursesArray != null) {
                for (Object courseObj : completedCoursesArray) {
                    JSONObject course = (JSONObject) courseObj;
                    String courseId = (String) course.get("courseID");
                    String courseName = (String) course.get("courseName");
                    String prerequisiteCourse = (String) course.get("prerequisite");
                    for (int i = 0; i < courses.size(); i++) {
                        if (courses.get(i).getCourseName().equals(courseName))
                            completedCourses.add(courses.get(i));
                    }
                }
            }

            // Access and parse "currentCourses" array
            JSONArray currentCoursesArray = (JSONArray) jsonObject.get("currentCourses");
            if (currentCoursesArray != null) {
                for (Object courseObj : currentCoursesArray) {
                    JSONObject course = (JSONObject) courseObj;
                    String courseId = (String) course.get("courseID");
                    String courseName = (String) course.get("courseName");
                    for (int i = 0; i < courses.size(); i++) {
                        if (courses.get(i).getCourseName().equals(courseName))
                            currentCourses.add(courses.get(i));
                    }
                    // Add to currentCourses without grade

                }
            }
            JSONArray waitedCoursesArray = (JSONArray) jsonObject.get("waitedCourses");
            if (waitedCoursesArray != null) {

                for (Object courseObj : waitedCoursesArray) {
                    JSONObject course = (JSONObject) courseObj;
                    String courseId = (String) course.get("courseID");
                    String courseName = (String) course.get("courseName");
                    for (int i = 0; i < courses.size(); i++) {
                        if (courses.get(i).getCourseName().equals(courseName))
                            waitedCourses.add(courses.get(i));
                    }


                }
            }

            // Return the Transcript with completed and current courses
            return new Transcript(completedCourses, currentCourses, waitedCourses);

        } catch (Exception e) {
            System.out.println("Error reading or parsing the JSON file for student ID: " + studentID);
            e.printStackTrace();
            return null;
        }
    }

   /**
 * Adds a course to the student's "waitedCourses" list in the student's JSON file.
 * The method checks if the "waitedCourses" array exists in the student's data. If not, it initializes it.
 * It then creates a JSON object for the course, adds it to the "waitedCourses" array, and writes the updated data back to the file.
 */
    private static void addWaitedCourse(Student student, Course course) {
        JSONParser parser = new JSONParser();
        String filePath = "src/main/java/" + student.getStudentID() + ".json";

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
    private static void acceptCourseRequest(Student student, Course course) {
        String filePath = "src/main/java/" + student.getStudentID() + ".json";

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
    }
/**
 * Checks the entered user ID and password to authenticate a student or advisor.
 * Based on the first character of the entered user ID, the method determines whether the user is a student (starting with 'o') or a advisor (starting with 'l').
 * It then checks the credentials against the data in the "parameters.json" file.
 * If the credentials are correct, it returns the corresponding student or advisor object.
 * If the user ID or password is incorrect, appropriate error messages are displayed.
 */
    private static Person checkIdandPassword(String enteredUserId, String enteredPassword, ArrayList<Course> courses) {
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
            String filePath = "src/main/java/parameters.json";

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
                            returnObject = getStudentByID(enteredUserId.substring(1), courses);
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
                            String userID = (String) studentJson.get("userID");
                            String password = (String) studentJson.get("password");

                            if (!password.equals(enteredPassword)) {
                                System.out.println("Wrong password");
                                return null;
                            }
                            returnObject = getAdvisorByUserID(enteredUserId, courses);
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
 * Displays the advisor interface menu and allows the advisor to manage students' course requests.
 * The advisor can either select a student and approve or deny their waited courses, or log out of the system.
 * The menu presents options for viewing the list of students, approving course requests, and logging out.
 * If the advisor approves a course request, it is moved from the waited courses list to the current courses list for the student.
 * If the advisor denies the course, the course is removed from the waited courses list.
 */
    public static boolean advisorInterface(Advisor advisor, ArrayList<Course> courses) {
        boolean logOut = false;
        System.out.println("Select an operation:\n1-  Students Menu\n2-  Log Out");
        Scanner menu = new Scanner(System.in);
        switch (menu.nextInt()) {
                // Case 1: Handling the student menu to manage course requests
            case 1:
                Scanner scan = new Scanner(System.in);
                System.out.println("Students are shown below:");
                int numOfStudents = advisor.getStudents().size();
                for (int i = 0; i < numOfStudents; i++) {
                    System.out.println((i + 1) + "-    " + advisor.getStudents().get(i).getStudentID());
                }
                System.out.print("Which student do you select? :\nIf you want to turn back enter \"0\": ");
                int studentIndex = scan.nextInt() - 1;
                if (studentIndex == -1)
                    return false;
                Student currentStudent = advisor.getStudents().get(studentIndex);
                if (currentStudent == null) {
                    System.out.println("Please choose an available student.");
                } else {
                    // Case 1.1: Approving or denying waited courses for the selected student
                    if (currentStudent.getTranscript().getWaitedCourses().isEmpty())
                        System.out.println("This student does not wait to enroll in any course");
                    else {
                        while (true) {
                            if(currentStudent.getTranscript().getWaitedCourses().isEmpty()){
                                System.out.println("All waited courses have been approved. You will be directed to main menu.");
                                break;
                            }
                            currentStudent.getTranscript().showWaitedCourses();
                            int courseIndex = -1;
                            System.out.print("Which course do you want  to select?:\n ");
                            try {
                                courseIndex = scan.nextInt();
                                int size = currentStudent.getTranscript().getWaitedCourses().size();
                                if (courseIndex <= 0 || courseIndex > size) {
                                    System.out.println("Enter a value between 1 and " + size);
                                } else {
                                    Course course = currentStudent.getTranscript().getWaitedCourses().get(courseIndex - 1);
                                    System.out.print("Do you want to approve this course?(y/n): \nIf you want to turn back enter \"0\": ");
                                    String approve = scan.next();
                                    if (approve.equals("0"))
                                        break;
                                    if (approve.equals("y")) {
                                        acceptCourseRequest(currentStudent, course);
                                        if(currentStudent.getTranscript().getWaitedCourses().isEmpty()){
                                            System.out.println("No more courses to approve.");
                                        }
                                    } else if (approve.equals("n")) {
                                        currentStudent.getTranscript().deleteFromWaitedCourse(course);
                                    } else {
                                        System.out.println("Enter y or n.");
                                    }
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Enter an integer value");
                                break;
                            }


                        }
                    }
                }
                return logOut;
            case 2:
                System.out.println("You have successfully logged out\n");
                logOut = true;
                return logOut;
            default:
                return false;
        }
    }
/**
 * Displays the student interface menu, where the student can view their transcript, register for courses, or log out.
 * The menu provides options for showing completed, current, and waited courses, registering for new courses (with prerequisite checks), 
 * and logging out of the system. The registration process ensures that the student cannot enroll in courses that they have already completed, 
 * are already registered for, or are waiting for, and enforces prerequisites before allowing course registration.
 */
    public static boolean studentInterface(Student student, ArrayList<Course> courses) {
        Scanner scan = new Scanner(System.in);
        System.out.println("1. Transcript\n2. Register for course\n3. Log out");
        boolean logout = false;
        int choice = -1;
        try {
            choice = scan.nextInt();
        } catch (InputMismatchException e) {
        }
        switch (choice) {
                // Choice 1: Display the student's transcript, showing completed, current, and waited courses
            case 1:
                student.getTranscript().showCompletedCourses();
                student.getTranscript().showCurrentCourses();
                student.getTranscript().showWaitedCourses();
                break;
                // Choice 2: Handle the course registration process
            case 2:
                System.out.println("These are the courses for registering.");
                ArrayList<Course> selectingArray = new ArrayList<>();
                int index = 1;
                for (int j = 0; j < courses.size(); j++) {
                    boolean isCompleted = false;
                    boolean isWaited = false;
                    boolean isCurrent = false;
                    // Check if the course is in the completed courses
                    if (student.getTranscript().getCompletedCourses() != null) {
                        for (int k = 0; k < student.getTranscript().getCompletedCourses().size(); k++) {
                            if (courses.get(j).getCourseId().equals(student.getTranscript().getCompletedCourses().get(k).getCourseId())) {
                                isCompleted = true;
                                break;
                            }
                        }
                    }

                    // Check if the course is in the waited courses
                    if (student.getTranscript().getWaitedCourses() != null) {
                        for (int k = 0; k < student.getTranscript().getWaitedCourses().size(); k++) {
                            if (courses.get(j).getCourseId().equals(student.getTranscript().getWaitedCourses().get(k).getCourseId())) {
                                isWaited = true;
                                break;
                            }
                        }
                    }
                    if (student.getTranscript().getCurrentCourses() != null) {
                        for (int k = 0; k < student.getTranscript().getCurrentCourses().size(); k++) {
                            if (courses.get(j).getCourseId().equals(student.getTranscript().getCurrentCourses().get(k).getCourseId())) {
                                isCurrent = true;
                                break;
                            }
                        }
                    }


                        // Register the course if it is not in the completed courses
                        if (!isCompleted && !isWaited && !isCurrent) {

                            Course prerequisite = courses.get(j).getPrerequisiteCourse();
                            boolean prerequisiteMet = prerequisite == null; // No prerequisite means it's met

                            // If there is a prerequisite, check if it's completed
                            if (prerequisite != null) {
                                for (Course completed : student.getTranscript().getCompletedCourses()) {
                                    if (completed.getCourseId().equals(prerequisite.getCourseId())) {
                                        prerequisiteMet = true;
                                        break;
                                    }
                                }
                            }

                            // Add course if prerequisites are met
                            if (prerequisiteMet) {
                                selectingArray.add(courses.get(j));
                            }
                        }
                    }
                System.out.println("Select a course. If you want to exit press 0.");
                int capacity = student.getTranscript().getWaitedCourses().size() + student.getTranscript().getCurrentCourses().size();
                int takenCourseNumber = 0;
                printList(selectingArray);
                while (true) {
                    if (selectingArray.isEmpty()) {
                        System.out.println("There is no course to register");
                        break;
                    }

                    int courseChoice = (scan.nextInt());

                    if (courseChoice == 0) {
                        break;
                    }

                    if (courseChoice < 0 || courseChoice > selectingArray.size()) {
                        System.out.println("Please enter a valid choice.");
                        continue;
                    }
                    courseChoice += -1;
                    if (student.getTranscript().getCurrentCourses().size() + student.getTranscript().getWaitedCourses().size() == 5) {
                        System.out.println("You have reached maximum number of courses. You will be directed to main menu.");
                        break;
                    } else {
                        // Register the selected course and add it to the waited courses list
                        student.registerCourse(selectingArray.get(courseChoice));
                        addWaitedCourse(student, selectingArray.get(courseChoice));
                        System.out.println(selectingArray.get(courseChoice).getCourseName() + " " + "is succesfully registered.");
                        selectingArray.remove(courseChoice);
                        printList(selectingArray);
                        takenCourseNumber++;

                    }
                }
                break;
            case 3:
                System.out.println("You are successfully logged out.\n");
                logout = true;
                break;
            default:
                System.out.println("Enter an integer value between 1 and 3");
        }
        return logout;
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
/**
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
/**
 * Prints the list of courses with their indices, course IDs, and course names.
 * It iterates through the provided list of courses and displays each course's index,
 * ID, and name in a formatted manner to the console.
 */
    private static void printList(ArrayList<Course> printedList) {
        for (int i = 0; i < printedList.size(); i++) {
            System.out.println((i + 1) + "       " + printedList.get(i).getCourseId() + "   " + printedList.get(i).getCourseName());
        }
    }
/**
 * Adds prerequisite courses to each course in the list based on the prerequisite ID.
 * For each course, it checks if there is a prerequisite defined by its course ID.
 * If a prerequisite is found, it sets the corresponding prerequisite course object.
 */
    private static ArrayList<Course> addPrerequisite(ArrayList<Course> courses) {
        for (Course course : courses) {
            // If a prerequisite course name is defined, find the actual prerequisite course object
            if (course.getPrerequisiteID() != null) {
                for (Course potentialPrerequisite : courses) {
                    if (course.getPrerequisiteID().equals(potentialPrerequisite.getCourseId())) {
                        course.setPrerequisiteCourse(potentialPrerequisite);
                        break;
                    }
                }
            }
        }
        return courses;
    }
}
