import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JsonManagement {

    private ArrayList<Course> courses;
    private ArrayList<Student> students;
    private static JsonManagement instance;

    private JsonManagement() {
        this.students = new ArrayList<Student>();
        this.courses = loadCourses();
        
    }

    public static JsonManagement getInstance() {
        if (instance == null) {
            instance = new JsonManagement();
        }
        return instance;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
    public ArrayList<Student> getStudents() {
        return students;
    }


    public void saveStudents() {
        for (Student student : students)
            saveStudent(student);

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

    private void addPrerequisite(ArrayList<Course> courses) {
        for (Course course : courses) {
            // If a prerequisite course name is defined, find the actual prerequisite course
            // object
            if (course.getPrerequisiteID() != null) {
                for (Course potentialPrerequisite : courses) {
                    if (course.getPrerequisiteID().equals(potentialPrerequisite.getCourseId())) {
                        course.setPrerequisiteCourse(potentialPrerequisite);
                        break;
                    }
                }
            }
        }


    }

    public Student checkStudentIfExists(String studentID) {
        for (Student student : students) {
            if (student.getStudentID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }

    public Affair getAffairByID(String affairID) {
        JSONParser parser = new JSONParser();
        String filePath = "Iteration_2/src/main/java/parameters.json";
    
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);
            
           
            JSONArray affairsArray = (JSONArray) jsonData.get("affairs");
            
            
            for (Object affairObj : affairsArray) {
                JSONObject affairJson = (JSONObject) affairObj;
                String id = (String) affairJson.get("userID"); 
                
                if (id.equals(affairID)) { 
                    String name = (String) affairJson.get("name");
                    String surname = (String) affairJson.get("surname");
                    
                    
                    return new Affair(id, name, surname);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    
        System.out.println("Affair with ID: " + affairID + " not found.");
        return null;
    }

    protected Student getStudentByID(String studentID) {
        Student existingStudent = checkStudentIfExists(studentID);
        if(existingStudent != null){
            return existingStudent;
        }
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
                        Transcript transcript = createTranscript(studentID);
                        Student student = new Student(name, surname, birthdate, gender, transcript, studentID);
                        createArrayList(student);
                        return student;
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

    public Advisor getAdvisorByUserID(String userID) {
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

    protected void saveStudent(Student student) {
        String filePath = "Iteration_2/src/main/java/" + student.getStudentID() + ".json"; // Use student's ID to
                                                                                           // determine the file path

        try (FileWriter writer = new FileWriter(filePath)) {
            // Create JSON data based on the Student object
            JSONObject studentData = new JSONObject();

            // Add completedCourses
            JSONArray completedCourses = new JSONArray();
            for (Course course : student.getTranscript().getCompletedCourses()) {
                JSONObject courseData = new JSONObject();
                courseData.put("courseID", course.getCourseId());
                courseData.put("courseName", course.getCourseName());
                courseData.put("credits", course.getCredits());
                completedCourses.add(courseData);
            }
            studentData.put("completedCourses", completedCourses);

            // Add currentCourses
            JSONArray currentCourses = new JSONArray();
            for (Course course : student.getTranscript().getCurrentCourses()) {
                JSONObject courseData = new JSONObject();
                courseData.put("courseID", course.getCourseId());
                courseData.put("courseName", course.getCourseName());
                courseData.put("credits", course.getCredits());
                currentCourses.add(courseData);
            }
            studentData.put("currentCourses", currentCourses);

            // Add waitedCourses
            JSONArray waitedCourses = new JSONArray();
            for (Course course : student.getTranscript().getWaitedCourses()) {
                JSONObject courseData = new JSONObject();
                courseData.put("courseID", course.getCourseId());
                courseData.put("courseName", course.getCourseName());
                courseData.put("credits", course.getCredits());
                waitedCourses.add(courseData);
            }
            studentData.put("waitedCourses", waitedCourses);

            // Write JSON to file
            writer.write(studentData.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing student data to file.");
        }
    }

    private Transcript createTranscript(String studentID) {
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

    private void createArrayList(Student currentStudent) {
        int size = students.size();
        boolean isSame = false;
        for (int k = 0; k < size; k++) {
            if (currentStudent.getStudentID().equals(students.get(k).getStudentID())) {
                isSame = true;
                break;
            }
        }
        if (!isSame)
            students.add(currentStudent);

    }



}

