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

public class JsonManagement {


    protected static Student getStudentByID(String studentID, ArrayList<Course> courses) {
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

    protected static void saveStudentDataToJson(Student student, Course course) {
    
        ArrayList<Course> waitedCourses = student.getTranscript().getWaitedCourses();
        ArrayList<Course> currentCourses = student.getTranscript().getCurrentCourses();
    
       
        Course courseToMove = null;
        for (Course waitedCourse : waitedCourses) {
            if (waitedCourse.getCourseId().equals(course.getCourseId())) {
                courseToMove = waitedCourse;
                break;
            }
        }
    
        
        if (courseToMove != null) {
            currentCourses.add(courseToMove);
            waitedCourses.remove(courseToMove);
        }

        JSONObject studentData = new JSONObject();


        JSONArray currentCoursesJson = new JSONArray();
        for (int i = 0; i < currentCourses.size(); i++) {
            Course currentCourse = currentCourses.get(i);
            
            JSONObject courseJson = new JSONObject();
            courseJson.put("courseID", student.getTranscript().getCurrentCourses().get(i).getCourseId());
            courseJson.put("courseName", student.getTranscript().getCurrentCourses().get(i).getCourseName());
            courseJson.put("credits", student.getTranscript().getCurrentCourses().get(i).getCredits());
            
        
            
            currentCoursesJson.add(courseJson);
        }
        studentData.put("currentCourses", currentCoursesJson);
    
        
        JSONArray waitedCoursesJson = new JSONArray();
        for (int i = 0; i < waitedCourses.size(); i++) {
            Course waitedCourse = waitedCourses.get(i);
            
            JSONObject courseJson = new JSONObject();
            courseJson.put("courseID", student.getTranscript().getWaitedCourses().get(i).getCourseId());
            courseJson.put("courseName", student.getTranscript().getWaitedCourses().get(i).getCourseName());
            courseJson.put("credits", student.getTranscript().getWaitedCourses().get(i).getCredits());
            
        
            
            waitedCoursesJson.add(courseJson);
        }
        studentData.put("waitedCourses", waitedCoursesJson);
    
        String filePath = "src/main/java/" + student.getStudentID() + ".json";
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(studentData.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
