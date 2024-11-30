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
import java.util.List;

public class JsonManagement {

    private ArrayList<Course> courses;
    private ArrayList<Student> students;
    private static JsonManagement instance;
    private ArrayList<CourseSection> courseSections;

    private JsonManagement() {
        this.students = new ArrayList<Student>();
        this.courses = loadCourses();
        this.courseSections = loadCourseSections();
        setCourseSectionsOfCourses();
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

    

    private ArrayList<CourseSection> loadCourseSections() {
        String filePath = "iteration_2/src/main/java/courseSections.json"; // Adjust the file path as needed
        ArrayList<CourseSection> courseSectionsList = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject rootObject = (JSONObject) parser.parse(reader);
            JSONObject courseSections = (JSONObject) rootObject.get("courseSections");

            for (Object courseKey : courseSections.keySet()) {
                String courseId = (String) courseKey;
                JSONArray sectionsArray = (JSONArray) courseSections.get(courseId);

                for (Object sectionObj : sectionsArray) {
                    JSONObject section = (JSONObject) sectionObj;

                    int sectionID = ((Long) section.get("sectionId")).intValue();
                    String time = (String) section.get("time");
                    String classroom = (String) section.get("classroom");
                    int capacity = ((Long) section.get("capacity")).intValue();
                    TimeSlot timeSlot = new TimeSlot(time, classroom);
                    CourseSection courseSection = new CourseSection(Integer.toString(sectionID), capacity);
                    courseSection.getTimeSlots().add(timeSlot);
                    Course course = null;
                    for (Course c : courses) {
                        if (c.getCourseId().equals(courseId)) {
                            course = c;
                            break;
                        }
                    }
                    courseSection.setParentCourse(course);
                    courseSectionsList.add(courseSection);
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return courseSectionsList;
    }

    public void setCourseSectionsOfCourses() {
        for (Course course : courses) {
            for (CourseSection cs : courseSections) {
                if (cs.getParentCourse().getCourseId().equals(course.getCourseId())) {
                    course.getCourseSections().add(cs);
                }
            }
        }
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

    public StudentAffairsStaff getstudentAffairsStaffByID(String affairID) {
        JSONParser parser = new JSONParser();
        String filePath = "Iteration_2/src/main/java/parameters.json";

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);

            JSONArray affairsArray = (JSONArray) jsonData.get("studentAffairsStaffs");

            for (Object affairObj : affairsArray) {
                JSONObject affairJson = (JSONObject) affairObj;
                String id = (String) affairJson.get("userID");

                if (id.equals(affairID)) {
                    String name = (String) affairJson.get("name");
                    String surname = (String) affairJson.get("surname");

                    return new StudentAffairsStaff(id, name, surname);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Affair with ID: " + affairID + " not found.");
        return null;
    }

    public DepartmentScheduler getDepartmentSchedulerByID(String schedulerID) {
        JSONParser parser = new JSONParser();
        String filePath = "Iteration_2/src/main/java/parameters.json";

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);
            JSONArray schedulersArray = (JSONArray) jsonData.get("departmentSchedulers");

            for (Object schedulerObj : schedulersArray) {
                JSONObject schedulerJson = (JSONObject) schedulerObj;
                String id = (String) schedulerJson.get("userID");
                if (id.equals(schedulerID)) {
                    String name = (String) schedulerJson.get("name");
                    String surname = (String) schedulerJson.get("surname");

                    return new DepartmentScheduler(name, surname);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Scheduler with ID: " + schedulerID + " not found.");
        return null;
    }

    public Student checkStudentIfExists(String studentID) {
        for (Student student : students) {
            if (student.getID().equals(studentID)) {
                return student;
            }
        }
        return null;
    }

    protected Student getStudentByID(String studentID) {
        Student student = getStudentByIDWithoutAdvisor(studentID);
        setAdvisorForStudent(student);
        return student;
    }

    protected Student getStudentByIDWithoutAdvisor(String studentID) {
        Student existingStudent = checkStudentIfExists(studentID);
        if (existingStudent != null) {
            return existingStudent;
        }
        JSONParser parser = new JSONParser();
        String filePath = "Iteration_2/src/main/java/parameters.json";
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);
            JSONArray studentsArray = (JSONArray) jsonData.get("students");
            for (Object studentObj : studentsArray) {
                JSONObject studentJson = (JSONObject) studentObj;
                String jsonStudentID = (String) studentJson.get("userID");
                if (jsonStudentID.equals(studentID)) {
                    try {
                        String name = (String) studentJson.get("name");
                        String surname = (String) studentJson.get("surname");
                        String date = (String) studentJson.get("birthdate");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date birthdate = formatter.parse(date);
                        char gender = ((String) studentJson.get("gender")).charAt(0);
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

    public void setAdvisorForStudent(Student student) {
        JSONParser parser = new JSONParser();
        String filePath = "Iteration_2/src/main/java/parameters.json";
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);
            JSONArray studentsArray = (JSONArray) jsonData.get("students");
            for (Object studentObj : studentsArray) {
                JSONObject studentJson = (JSONObject) studentObj;
                String jsonStudentID = (String) studentJson.get("userID");
                if (jsonStudentID.equals(student.getID())) {
                    String advisorID = (String) studentJson.get("advisorID");
                    student.setAdvisor(getAdvisorByUserID(advisorID));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
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
                        Student student = getStudentByIDWithoutAdvisor(studentID);
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

    protected void fillCourseData(JSONObject courseJson, ArrayList<Course> courses, String courseListType) {
        JSONArray completedCourses = new JSONArray();
        for (Course course : courses) {
            JSONObject courseData = new JSONObject();
            courseData.put("courseID", course.getCourseId());
            courseData.put("courseName", course.getCourseName());
            courseData.put("credits", course.getCredits());
            completedCourses.add(courseData);
        }
        courseJson.put(courseListType, completedCourses);
    }

    protected void fillCourseSectionData(JSONObject sectionJson, ArrayList<CourseSection> sections, String sectionListType) {
        JSONArray sectionArray = new JSONArray();
        for (CourseSection section : sections) {
            JSONObject sectionData = new JSONObject();
            sectionData.put("sectionId", section.getSectionID());
            for(int i = 0; i < section.getTimeSlots().size(); i++){
                sectionData.put("time", section.getTimeSlots().get(i).getTimeInterval());
                sectionData.put("classroom", section.getTimeSlots().get(i).getClassroom());
            }
            sectionData.put("capacity", section.getCapacity());
            sectionArray.add(sectionData);
        }
        sectionJson.put(sectionListType, sectionArray);
    }
    
    protected void saveCoursesSections() {
        String filePath = "Iteration_2/src/main/java/courseSections.json";

        try (FileWriter writer = new FileWriter(filePath)) {
            // Create JSON data based on the Student object
            JSONObject courseSectionsData = new JSONObject();

            fillCourseSectionData(courseSectionsData, courseSections, "courseSections");

            // Write JSON to file
            writer.write(courseSectionsData.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing course section data to file.");
        }
    }

    protected void saveStudent(Student student) {
        String filePath = "Iteration_2/src/main/java/" + student.getID() + ".json";

        try (FileWriter writer = new FileWriter(filePath)) {
            // Create JSON data based on the Student object
            JSONObject studentData = new JSONObject();

            fillCourseData(studentData, student.getTranscript().getCompletedCourses(), "completedCourses");
            fillCourseData(studentData, student.getTranscript().getCurrentCourses(), "currentCourses");
            fillCourseData(studentData, student.getTranscript().getCompletedCourses(), "completedCourses");

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
                    int credits = ((Long) course.get("credits")).intValue();
                    String grade = (String) course.get("grade");

                    Course completedCourse = new Course(courseId, courseName, grade, credits);
                    completedCourses.add(completedCourse);
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
            if (currentStudent.getID().equals(students.get(k).getID())) {
                isSame = true;
                break;
            }
        }
        if (!isSame)
            students.add(currentStudent);

    }

}
