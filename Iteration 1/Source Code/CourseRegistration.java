import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class CourseRegistration {


    public static void main(String[] args) {

        ArrayList<Course> courses = loadCourses(); // Kursları yükle
        boolean isLogged = true;


        while (true) {
            Person currentUser= login();
            isLogged = showMenu(currentUser, isLogged, courses);
            if(isLogged) {
                login();
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

    public static Advisor getAdvisorByUserID(String userID) {
        JSONParser parser = new JSONParser();
        String basePath = System.getProperty("user.dir");
        String filePath = Paths.get(basePath, "Iteration 1", "Source Code", "JsonFiles", "parameters.json").toString();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);
            JSONArray advisorsArray = (JSONArray) jsonData.get("advisors");

            for (Object advisorObj : advisorsArray) {
                JSONObject advisorJson = (JSONObject) advisorObj;
                String advisorUserID = (String) advisorJson.get("userID");

                if (advisorUserID.equals(userID)) {
                    String name = (String) advisorJson.get("name");
                    String surname = (String) advisorJson.get("surname");
                    Date birthdate = (Date) advisorJson.get("birthdate");
                    char gender = (char) advisorJson.get("gender");
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

                    for(Object courseIDObj : courseIDs){
                        String courseID = (String) courseIDObj;
                        for(Course course : courses){
                            if(course.getCourseId().equals(courseID)){
                                coursesOfLecturer.add(course);
                            }
                        }
                    }

                    return new Advisor(name, surname, birthdate, gender, ssn, coursesOfLecturer ,students);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        
        System.out.println("Advisor with userID: " + userID + " not found.");
        return null;
    }

    // JSON dosyasından öğrenci bilgilerini alıp Student nesnesi oluşturan yardımcı metod
    private static Student getStudentByID(String studentID) {
        JSONParser parser = new JSONParser();
        String basePath = System.getProperty("user.dir");
        String filePath = Paths.get(basePath, "Iteration 1", "Source Code", "JsonFiles", "parameters.json").toString();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);
            JSONArray studentsArray = (JSONArray) jsonData.get("students");

            for (Object studentObj : studentsArray) {
                JSONObject studentJson = (JSONObject) studentObj;
                String jsonStudentID = (String) studentJson.get("studentID");

                if (jsonStudentID.equals(studentID)) {
                    String userID = (String) studentJson.get("userID");
                    String password = (String) studentJson.get("password");
                    String name = (String) studentJson.get("name");
                    String surname = (String) studentJson.get("surname");
                    Date birthdate = (Date) studentJson.get("birthdate");
                    char gender = (char) studentJson.get("gender");
                    String advisorID = (String) studentJson.get("advisorID");
                    Transcript transcript = createTranscript(studentID);

                    return new Student(name, surname, birthdate, gender, transcript, studentID, advisorID);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static Transcript createTranscript(String studentID) {
        JSONParser parser = new JSONParser();
        String filePath = "src/"+ studentID+".json";

        ArrayList<Course> completedCourses = new ArrayList<>();
        ArrayList<Course> currentCourses = new ArrayList<>();

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

            // Return the Transcript with completed and current courses
            return new Transcript(completedCourses, currentCourses);

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
        String filePath = "src/parameters.json";

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
        String basePath = System.getProperty("user.dir");
        String filePath = Paths.get(basePath, "parameters.json").toString();

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

    private static Person checkIdandPassword(String enteredUserId, String enteredPassword) {
        JSONParser parser = new JSONParser();
        String filePath = "src/parameters.json";

        try (FileReader reader = new FileReader(filePath)) {
            // JSON dosyasını okuyun ve parse edin
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray usersArray = (JSONArray) jsonObject.get("students");

            String userId = null;
            String password = null;
            String name = null;
            String surname = null;
            String studentID = null;
            JSONObject user = null;
            boolean isUserExist = false;

            for (Object userObj : usersArray) {
                user = (JSONObject) userObj;
                userId = (String) user.get("userID");
                password = (String) user.get("password");
                name = (String) user.get("name");
                surname = (String) user.get("surname");
                studentID = (String) user.get("studentID");
                isUserExist = userId.equals(enteredUserId) && password.equals(enteredPassword);
                if (isUserExist)
                    break;
            }

            if (userId.charAt(0) != 'o' && userId.charAt(0) != 'l') {
                System.out.println("If you are a student, you must add \"o\" as the first letter of your student number\n" +
                        "If you are a lecturer, you must add \"l\"");

            } 
            else if (isUserExist) {
                if (userId.charAt(0) == 'o') {
                    System.out.println("Login successful!");
                    System.out.println("Welcome, " + name + " " + surname);
                    String StudentID = (String) user.get("studentID");

                    Transcript transcript;
                    transcript = createTranscript(StudentID);
                    return new Student(name, surname, new Date(2002,9,18), 'm', transcript,studentID, new Advisor());                } else if (userId.charAt(0) == 'l') {
                }
            } 
            else {
                System.out.println("Wrong username or password. Please try again.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void advisorInterface(Advisor advisor){
        Scanner scan = new Scanner(System.in);
        System.out.println("Students are shown below:");
        int numOfStudents = advisor.getStudents().size();
        for(int i = 0; i < numOfStudents; i++){
            System.out.println((i+1)+"." + advisor.getStudents().get(i).getStudentID());
        }
        System.out.print("Which student do you select? :");
        int studentIndex = scan.nextInt() - 1;
        Student currentStudent = advisor.getStudents().get(studentIndex);
        if(currentStudent == null){
            System.out.println("This student does not wait to enroll in any course");
        }else {
            currentStudent.getTranscript().showWaitedCourses();
            System.out.print("Which course do you want  to select?: ");
            int courseIndex = scan.nextInt();
            Course course = currentStudent.getTranscript().getWaitedCourses().get(courseIndex-1);
            System.out.println("Do you want to approve this course?(Y/N): ");
            String approve = scan.next();
            if(approve.equals("Y")){
                advisor.approveCourse(currentStudent, course);
            }else if(approve.equals("N")){
                currentStudent.getTranscript().deleteFromWaitedCourse(course);
            }else{
                System.out.println("Enter Y or N.");
            }
        }
    }

     public static void studentInterface (Student student){
            Scanner scan = new Scanner(System.in);
            System.out.println("1. Transcript\n2. Register for course\n3. Log out");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    student.getTranscript().showCompletedCourses();
                    student.getTranscript().showWaitedCourses();
                    break;
                case 2:
                    System.out.println("These are the courses for registering.");
                    ArrayList<Course> selectingArray = new ArrayList<>();
                    int index= 1;
                    for (int j = 0; j < courses.size(); j++) {
                        boolean isCompleted = false;
                        boolean isWaited = false;
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
                        // Register the course if it is not in the completed courses
                        if (!isCompleted && !isWaited) {
                            selectingArray.add(courses.get(j));
                            System.out.println(index++ + "-     " + courses.get(j).getCourseId() + "    "+ courses.get(j).getCourseName());
                          
                        }
                    }
                    System.out.println("Select a course. If you want to exit press 9.");
                    int capacity = 5;
                    int takenCourseNumber = 0;
                
                    while (capacity > takenCourseNumber){
                        int courseChoice = (scan.nextInt());
                        if(courseChoice == 9 ){
                            break;}

                        if(courseChoice < 1 || courseChoice > selectingArray.size()){
                            System.out.println("Please enter a valid choice.");
                            continue;
                        }
                        if(student.getTranscript().getWaitedCourses().contains(selectingArray.get(courseChoice))){
                            System.out.println("You selected same course twice!");
                            continue;
                        }
                        student.registerCourse(selectingArray.get(courseChoice));
                        addWaitedCourse(student,selectingArray.get(courseChoice));
                        System.out.println(selectingArray.get(courseChoice).getCourseName() + " " + "is succesfully registered." );
                        selectingArray.remove(courseChoice);
                        takenCourseNumber++;

                    }
                  
                    break;
                case 3:
                    System.out.println("You are successfully logged out.\n");
                    break;
            }
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
    private static boolean showMenu(Person currentUser, boolean isLogged, ArrayList<Course> courses) {
        boolean loggedOut = false;
        if (currentUser instanceof Student) {
             loggedOut = studentInterface((Student) currentUser, courses);
        }else if(currentUser instanceof Advisor){
            advisorInterface((Advisor) currentUser);
        }else {
            //login();
        }
        return loggedOut;
    }
}
