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

    // JSON dosyasını aç, waitedCourses kısmını bul ve yeni dersi ekle
    // Öğrencinin dosyasını bulup waitedCourses kısmına courseID ve courseName ile ekle
    private static void addWaitedCourse(Student student, Course course) {
        String basePath = System.getProperty("user.dir");
        String filePath = Paths.get(basePath, "Iteration 1", "Source Code", "JsonFiles", student.getStudentID() + ".json").toString();
        
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject studentData = (JSONObject) parser.parse(reader);
            JSONArray waitedCourses = (JSONArray) studentData.get("waitedCourses");

            // Yeni ders bilgilerini içeren JSON nesnesi oluştur
            JSONObject newCourse = new JSONObject();
            newCourse.put("courseID", course.getCourseId());
            newCourse.put("courseName", course.getCourseName());

            // waitedCourses'a yeni dersi ekle
            waitedCourses.add(newCourse);

            // Güncellenmiş dosyayı tekrar yaz
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
        String filePath = Paths.get(basePath, "Iteration 1", "Source Code", "JsonFiles", student.getStudentID() + ".json").toString();

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
        Advisor advisor = new Advisor();
        JSONParser parser = new JSONParser();
        String basePath = System.getProperty("user.dir");
        String filePath = "src/paramaters.json";

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

            } else if (isUserExist) {
                if (userId.charAt(0) == 'o') {
                    System.out.println("Login successful!");
                    System.out.println("Welcome, " + name + " " + surname);
                    String StudentID = (String) user.get("StudentID");
                    
                    Transcript transcript;
                    transcript = createTranscript(StudentID);
                    return new Student(name, surname, new Date(2002,9,18), 'm', transcript,studentID, new Advisor());                } else if (userId.charAt(0) == 'l') {
                    return advisor;
                }
            } else {
                System.out.println("Wrong username or password. Please try again.");
            }


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return advisor;
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
            Course course = currentStudent.getTranscript().getWaitedCourse().get(courseIndex-1);
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

      public static void studentInterface(Student student){
        Scanner scan = new Scanner(System.in);
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

    private static Person login(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your User ID and Password");
        System.out.print("User ID: ");
        String enteredUserId = scan.nextLine();
        System.out.print("Password: ");
        String enteredPassword = scan.nextLine();
        return  checkIdandPassword(enteredUserId,enteredPassword);

    }
    private static boolean showMenu(Person currentUser, boolean isLogged) {
        if (currentUser instanceof Student) {
            studentInterface((Student) currentUser);
        }else if(currentUser instanceof Advisor){
            advisorInterface((Advisor) currentUser);
        }else {
            login();
        }

        return true;
    }
}
