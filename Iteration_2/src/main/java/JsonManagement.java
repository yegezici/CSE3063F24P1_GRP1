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

    public JsonManagement(){

    }

    protected ArrayList<Course> loadCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String filePath = "iteration_2/src/main/java/parameters.json";

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

    protected Student getStudentByID(String studentID, ArrayList<Course> courses) {
        JSONParser parser = new JSONParser();
        String filePath = "Iteration_2/src/main/java/parameters.json";
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);
            JSONArray studentsArray = (JSONArray) jsonData.get("students");
            for (Object studentObj : studentsArray) {
                JSONObject studentJson = (JSONObject) studentObj;
                String jsonStudentID = (String) studentJson.get("studentID");
                if (jsonStudentID.equals(studentID)) {
                    try {
                        String name = (String) studentJson.get("name");
                        String surname = (String) studentJson.get("surname");
                        String date = (String) studentJson.get("birthdate");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date birthdate = formatter.parse(date);
                        char gender = ((String) studentJson.get("gender")).charAt(0);
                        String advisorID = (String) studentJson.get("advisorID");
                        Transcript transcript = createTranscript(studentID, courses);
                        System.out.println("burada donuyor");
                        return new Student(name, surname, birthdate, gender, transcript, studentID);
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

    public Advisor getAdvisorByUserID(String userID, ArrayList<Course> courses) {
        JSONParser parser = new JSONParser();
        String filePath = "Iteration_2/src/main/java/parameters.json";
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

    protected void saveStudentDataToJson(Student student, Course course) {
    
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
    
        String filePath = "Iteration_2/src/main/java/" + student.getStudentID() + ".json";
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(studentData.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Transcript createTranscript(String studentID, ArrayList<Course> courses) {
        JSONParser parser = new JSONParser();
        String filePath = "Iteration_2/src/main/java/" + studentID + ".json";

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

    
}