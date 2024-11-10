import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CourseRegistration {


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

    private static ArrayList<Course> loadCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String filePath = "src/parameters.json";

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray coursesArray = (JSONArray) jsonObject.get("courses");

            for (Object obj : coursesArray) {
                JSONObject courseJson = (JSONObject) obj;
                String courseId = (String) courseJson.get("courseId");
                String courseName = (String) courseJson.get("name");
                int credits = ((Long) courseJson.get("credits")).intValue();

                Course course = new Course(courseId, courseName);

                courses.add(course);
            }
            System.out.println("Courses loaded successfully!");
        } catch (IOException | ParseException e) {
            System.out.println("Error loading courses from parameters.json.");
            e.printStackTrace();
        }
        return courses;
    }

    public static Advisor getAdvisorByUserID(String userID, ArrayList<Course> courses) {
        JSONParser parser = new JSONParser();
        String filePath = "src/parameters.json";
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
                        Student student = getStudentByID(studentID);
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

    // JSON dosyasından öğrenci bilgilerini alıp Student nesnesi oluşturan yardımcı metod
    private static Student getStudentByID(String studentID) {
        JSONParser parser = new JSONParser();
        String filePath = "src/parameters.json";
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
                        Transcript transcript = createTranscript(studentID);
                        return new Student(name, surname, birthdate, gender, transcript, studentID, advisorID);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Student object has not been initialized");
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Transcript createTranscript(String studentID) {
        JSONParser parser = new JSONParser();
        String filePath = "src/" + studentID + ".json";

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

                    // Add to completedCourses without grade
                    completedCourses.add(new Course(courseId, courseName));
                }
            }

            // Access and parse "currentCourses" array
            JSONArray currentCoursesArray = (JSONArray) jsonObject.get("currentCourses");
            if (currentCoursesArray != null) {
                for (Object courseObj : currentCoursesArray) {
                    JSONObject course = (JSONObject) courseObj;
                    String courseId = (String) course.get("courseID");
                    String courseName = (String) course.get("courseName");

                    // Add to currentCourses without grade
                    currentCourses.add(new Course(courseId, courseName));
                }
            }
            JSONArray waitedCoursesArray = (JSONArray) jsonObject.get("waitedCourses");
            if (waitedCoursesArray != null) {

                for (Object courseObj : waitedCoursesArray) {
                    JSONObject course = (JSONObject) courseObj;
                    String courseId = (String) course.get("courseID");
                    String courseName = (String) course.get("courseName");

                    // Add to currentCourses without grade
                    waitedCourses.add(new Course(courseId, courseName));
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

    // JSON dosyasını aç, waitedCourses kısmını bul ve yeni dersi ekle
    // Öğrencinin dosyasını bulup waitedCourses kısmına courseID ve courseName ile ekle
    private static void addWaitedCourse(Student student, Course course) {
        JSONParser parser = new JSONParser();
        String filePath = "src/" + student.getStudentID() + ".json";

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

    // JSON dosyasını aç, courseID'yi waitedCourses'dan bul ve currentCourses kısmına ekle
    // Ardından, waitedCourses kısmından ilgili courseID'yi sil
    private static void acceptCourseRequest(Student student, Course course) {
        String filePath = "src/" + student.getStudentID()+".json";

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject studentData = (JSONObject) parser.parse(reader);
            JSONArray waitedCourses = (JSONArray) studentData.get("waitedCourses");
            JSONArray currentCourses = (JSONArray) studentData.get("currentCourses");

            // waitedCourses'dan ilgili dersi bul ve kaldır
            JSONObject courseToMove = null;
            for (Object obj : waitedCourses) {
                JSONObject waitedCourse = (JSONObject) obj;
                if (waitedCourse.get("courseID").equals(course.getCourseId())) {
                    courseToMove = waitedCourse;
                    break;
                }
            }
            if (courseToMove != null) {
                // currentCourses kısmına dersi ekle
                currentCourses.add(courseToMove);
                // waitedCourses'dan dersi sil
                waitedCourses.remove(courseToMove);
            }

            // Güncellenmiş dosyayı tekrar yaz
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(studentData.toJSONString());
                writer.flush();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

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
            String filePath = "src/parameters.json";

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
                            if(!password.equals(enteredPassword)){
                                System.out.println("Wrong password");
                                return null;
                            }
                            returnObject = getStudentByID(enteredUserId.substring(1));
                        }
                    }
                }catch (Exception e){
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

                            if(!password.equals(enteredPassword)){
                                System.out.println("Wrong password");
                                return null;
                            }
                            returnObject = getAdvisorByUserID(enteredUserId, courses);
                        }
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
        if (returnObject == null)
            System.out.println("Wrong User Id or Password");
        return returnObject;
    }

    public static boolean advisorInterface(Advisor advisor, ArrayList<Course> courses) {
        boolean logOut = false;
        System.out.println("Select an operation:\n1-  Students Menu\n2-  Log Out");
        Scanner menu = new Scanner(System.in);
        switch (menu.nextInt()) {
            case 1:
                Scanner scan = new Scanner(System.in);
                System.out.println("Students are shown below:\n");
                int numOfStudents = advisor.getStudents().size();
                for (int i = 0; i < numOfStudents; i++) {
                    System.out.println((i + 1) + "-    " + advisor.getStudents().get(i).getStudentID());
                }
                System.out.print("\nWhich student do you select? :");
                int studentIndex = scan.nextInt() - 1;
                Student currentStudent = advisor.getStudents().get(studentIndex);
                if (currentStudent == null) {
                    System.out.println("Please choose an available student.\n");
                } else {
                    if (currentStudent.getTranscript().getWaitedCourses().isEmpty())
                        System.out.println("This student does not wait to enroll in any course\n");
                    else {
                        currentStudent.getTranscript().showWaitedCourses();
                        System.out.print("Which course do you want  to select?: ");
                        int courseIndex = scan.nextInt();
                        Course course = currentStudent.getTranscript().getWaitedCourses().get(courseIndex - 1);
                        System.out.println("Do you want to approve this course?(Y/N): ");
                        String approve = scan.next();
                        if (approve.equals("Y")) {
                           acceptCourseRequest(currentStudent,course);
                        } else if (approve.equals("N")) {
                            currentStudent.getTranscript().deleteFromWaitedCourse(course);
                        } else {
                            System.out.println("Enter Y or N.");
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

   public static boolean studentInterface(Student student, ArrayList<Course> courses) {
        Scanner scan = new Scanner(System.in);
        System.out.println("1. Transcript\n2. Register for course\n3. Log out");
        boolean logout = false;
        int choice = scan.nextInt();
        switch (choice) {
            case 1:
                student.getTranscript().showCompletedCourses();
                student.getTranscript().showCurrentCourses();
                student.getTranscript().showWaitedCourses();
                break;
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
                        selectingArray.add(courses.get(j));


                    }
                }
                System.out.println("Select a course. If you want to exit press 9.");
                int capacity = 5;
                int takenCourseNumber = 0;
                printList(selectingArray);
                while (true) {
                    if (selectingArray.isEmpty()) {
                        System.out.println("There is no course to register");
                        break;
                    }

                    int courseChoice = (scan.nextInt());

                    if (courseChoice == -1) {
                        break;
                    }

                    if (courseChoice < 1 || courseChoice > selectingArray.size()) {
                        System.out.println("Please enter a valid choice.");
                        continue;
                    }
                    courseChoice += -1;
                    if (takenCourseNumber == capacity) {
                        System.out.println("You cannot register for a course more than 5.");
                        break;
                    } else {
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
        }
        return logout;
    }


    private static Person login(ArrayList<Course> courses) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your User ID and Password");
        System.out.print("User ID: ");
        String enteredUserId = scan.nextLine();
        System.out.print("Password: ");
        String enteredPassword = scan.nextLine();
        return checkIdandPassword(enteredUserId, enteredPassword, courses);

    }

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

    private static void printList(ArrayList<Course> printedList) {
        for (int i = 0; i < printedList.size(); i++) {
            System.out.println((i + 1) + "       " + printedList.get(i).getCourseId() + "   " + printedList.get(i).getCourseName());
        }
    }

}
