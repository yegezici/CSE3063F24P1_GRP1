import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JsonManagement {

    private ArrayList<Course> courses;
    private ArrayList<Student> students;
    private static JsonManagement instance;
    private ArrayList<CourseSection> courseSections;
    private ArrayList<String> classrooms;
    private ArrayList<String> prerequisites;

    private JsonManagement() {
        this.prerequisites = new ArrayList<>();
        this.students = new ArrayList<Student>();
        this.courses = loadCourses();
        addPrerequisite();
        this.courseSections = loadCourseSections();
        setCourseSectionsOfCourses();
        this.classrooms = readClassrooms();
        findStudents();
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

    public ArrayList<CourseSection> getCourseSections() {
        return courseSections;
    }

    public ArrayList<String> getClassrooms() {
        return classrooms;
    }
    public void findStudents(){
        String[] studentIDs = {
                "150121031",
                "150121032",
                "150121033",
                "150121034",
                "150121035",
                "150122036",
                "150122037",
                "150122038",
                "150122039",
                "150122040",
                "150122041",
                "150122042"
        };

        for(int i = 0; i < 12; i++) {
            getStudentByID(studentIDs[i]);
        }
        for(CourseSection courseSection: courseSections){
            for(Student student : students){
                if(student.getTranscript().getCurrentSections().contains(courseSection)){
                    courseSection.addStudentToSection(student);
                }
            }
        }
    }

    private ArrayList<CourseSection> loadCourseSections() {
        String filePath = "iteration_2/src/main/java/courseSections.json";
        ArrayList<CourseSection> courseSectionsList = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            JSONObject rootObject = (JSONObject) parser.parse(reader);

            for (Object courseKey : rootObject.keySet()) {
                String courseId = (String) courseKey;
                JSONArray sectionsArray = (JSONArray) rootObject.get(courseId);

                for (Object sectionObj : sectionsArray) {
                    JSONObject section = (JSONObject) sectionObj;

                    int sectionID = ((Long) section.get("sectionID")).intValue();
                    String time = (String) section.get("time");
                    String classroom = (String) section.get("classroom");
                    int capacity = ((Long) section.get("capacity")).intValue();
                    CourseSection courseSection = null;
                    if (time.isEmpty()) {
                        courseSection = new CourseSection(Integer.toString(sectionID), capacity);
                    } else {
                        String[] timeParts = time.split(" ", 2);
                        String day = timeParts[0];
                        String timeInterval = timeParts[1];
                        TimeSlot timeSlot = new TimeSlot(day, timeInterval, classroom);
                        courseSection = new CourseSection(Integer.toString(sectionID), capacity);
                        courseSection.getTimeSlots().add(timeSlot);
                    }
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
            if (course != null)
                for (CourseSection cs : courseSections) {
                    if (cs.getParentCourse().getCourseId().equals(course.getCourseId())) {
                        course.getCourseSections().add(cs);
                    }
                }
        }
    }

    private ArrayList<Course> loadCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        JSONParser parser = new JSONParser();
        String filePath = "iteration_2/src/main/java/courseList.json";
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray mandatoryArray = (JSONArray) jsonObject.get("mandatory");
            JSONArray TEArray = (JSONArray) jsonObject.get("technicalElective");
            JSONArray NTEArray = (JSONArray) jsonObject.get("nonTechnicalElective");

            for (Object obj : mandatoryArray) {
                JSONObject courseJson = (JSONObject) obj;
                String courseId = (String) courseJson.get("courseID");
                String courseName = (String) courseJson.get("name");
                int credits = ((Long) courseJson.get("credits")).intValue();
                String prerequisite = (String) courseJson.get("prerequisite");
                int semester = Integer.parseInt((String) courseJson.get("year"));
                prerequisites.add(prerequisite + "-" + courseId);
                Course course = new MandatoryCourse(courseId, courseName, credits, semester);
                courses.add(course);
            }
            for (Object obj : TEArray) {
                JSONObject courseJson = (JSONObject) obj;
                String courseId = (String) courseJson.get("courseID");
                String courseName = (String) courseJson.get("name");
                int credits = ((Long) courseJson.get("credits")).intValue();
                String prerequisite = (String) courseJson.get("prerequisite");
                int semester = Integer.parseInt((String) courseJson.get("year"));
                prerequisites.add(prerequisite + "-" + courseId);
                Course course = new TechnicalElectiveCourse(courseId, courseName, credits, semester);
                courses.add(course);
            }
            for (Object obj : NTEArray) {
                JSONObject courseJson = (JSONObject) obj;
                String courseId = (String) courseJson.get("courseID");
                String courseName = (String) courseJson.get("name");
                int credits = ((Long) courseJson.get("credits")).intValue();
                String prerequisite = (String) courseJson.get("prerequisite");
                prerequisites.add(prerequisite + "-" + courseId);
                Course course = new NonTechnicalElectiveCourse(courseId, courseName, credits);
                courses.add(course);
            }

            System.out.println("Courses loaded successfully!");
        } catch (IOException | ParseException e) {
            System.out.println("Error loading courses from parameters.json.");
            e.printStackTrace();
        }
        return courses;
    }

    private void addPrerequisite() {
        for (String course : prerequisites) {
            String[] parts = course.split("-");
            String prerequisiteId = parts[0];
            String courseId = parts[1];
            for (Course c : courses)
                if (courseId.equals(c.getCourseId()))
                    for (Course p : courses)
                        if (prerequisiteId.equals(p.getCourseId()))
                            c.setPrerequisiteCourse(p);

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

                    return new DepartmentScheduler(name, surname, courseSections, classrooms);
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
        if (student != null)
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
            System.out.println("Student object has not been initialized");
        }
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

    protected void saveCourseSectionsOfData(JSONObject courseJson, ArrayList<CourseSection> courseSections,
            String sectionType) {
        JSONArray courseSectionsData = new JSONArray();
        for (CourseSection courseSection : courseSections) {
            JSONObject sectionData = new JSONObject();
            sectionData.put("courseID", courseSection.getParentCourse().getCourseId());
            sectionData.put("sectionID", courseSection.getSectionID());
            courseSectionsData.add(sectionData);
        }
        courseJson.put(sectionType, courseSectionsData);
    }

    protected void fillCourseData(JSONObject courseJson, ArrayList<Course> courses, String courseListType) {
        JSONArray completedCourses = new JSONArray();
        for (Course course : courses) {
            JSONObject courseData = new JSONObject();
            courseData.put("courseID", course.getCourseId());
            courseData.put("courseName", course.getCourseName());
            courseData.put("credits", course.getCredits());
            if (courseListType.equals("completedCourses"))
                courseData.put("grade", course.getGrade());
            completedCourses.add(courseData);
        }
        courseJson.put(courseListType, completedCourses);
    }

    public void saveStudent(Student student) {
        String filePath = "Iteration_2/src/main/java/" + student.getID() + ".json";

        try (FileWriter writer = new FileWriter(filePath)) {
            JSONObject studentData = new JSONObject();
            studentData.put("year", student.getTranscript().getSemester());
            fillCourseData(studentData, student.getTranscript().getCompletedCourses(), "completedCourses");
            fillCourseData(studentData, student.getTranscript().getCurrentCourses(), "currentCourses");
            fillCourseData(studentData, student.getTranscript().getWaitedCourses(), "waitedCourses");
            saveCourseSectionsOfData(studentData, student.getTranscript().getCurrentSections(), "currentSections");
            saveCourseSectionsOfData(studentData, student.getTranscript().getWaitedSections(), "waitedSections");
            writer.write(studentData.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing student data to file.");
        }
    }

    private ArrayList<CourseSection> readSectionsForStudents(String filePath, String sectionType) {
        ArrayList<CourseSection> newCourseSections = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // Access and parse "completedCourses" array
            JSONArray sectionArray = (JSONArray) jsonObject.get(sectionType);
            if (sectionArray != null) {
                for (Object courseObj : sectionArray) {
                    JSONObject course = (JSONObject) courseObj;

                    String parentCourseId = (String) course.get("courseID");
                    String sectionId = (String) course.get("sectionID");
                    int courseSectionsSize = courseSections.size();
                    for (int i = 0; i < courseSectionsSize; i++) {
                        if (courseSections.get(i).getParentCourse().getCourseId().equals(parentCourseId)
                                && courseSections.get(i).getSectionID().equals(sectionId)) {
                            newCourseSections.add(courseSections.get(i));
                            break;
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("Error reading or parsing the JSON file for " + filePath);
            e.printStackTrace();
            return null;
        }
        return newCourseSections;
    }

    public ArrayList<Course> readCoursesForStudents(String filePath, String courseListType) {
        ArrayList<Course> courseList = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // Access and parse "completedCourses" array
            JSONArray courseArray = (JSONArray) jsonObject.get(courseListType);
            if (courseArray != null) {
                for (Object courseObj : courseArray) {
                    JSONObject course = (JSONObject) courseObj;
                    String courseId = (String) course.get("courseID");
                    String courseName = (String) course.get("courseName");
                    int credits = ((Long) course.get("credits")).intValue();
                    if (courseListType.equals("completedCourses")) {
                        String grade = (String) course.get("grade");
                        Course newCourse = null;
                        String courseType = "";
                        for (Course c : courses) {
                            if (c.getCourseId().equals(courseId)) {
                                courseType = c.getCourseType();
                            }
                        }
                        if (courseType.equals("Mandatory")) {
                            newCourse = new MandatoryCourse(courseId, courseName, grade, credits);
                            courseList.add(newCourse);
                        } else if (courseType.equals("Technical Elective")) {
                            newCourse = new TechnicalElectiveCourse(courseId, courseName, grade, credits);
                            courseList.add(newCourse);
                        } else if (courseType.equals("Non-Technical Elective")) {
                            newCourse = new NonTechnicalElectiveCourse(courseId, courseName, grade, credits);
                            courseList.add(newCourse);
                        }

                    } else {
                        Course newCourse = null;
                        String courseType = "";
                        for (Course c : courses) {
                            if (c.getCourseId().equals(courseId)) {
                                courseType = c.getCourseType();
                            }
                        }
                        if (courseType.equals("Mandatory")) {
                            newCourse = new MandatoryCourse(courseId, courseName, credits);
                            courseList.add(newCourse);
                        } else if (courseType.equals("Technical Elective")) {
                            newCourse = new TechnicalElectiveCourse(courseId, courseName, credits);
                            courseList.add(newCourse);
                        } else if (courseType.equals("Non-Technical Elective")) {
                            newCourse = new NonTechnicalElectiveCourse(courseId, courseName, credits);
                            courseList.add(newCourse);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading or parsing the JSON file for" + filePath);
            e.printStackTrace();
            return null;
        }
        return courseList;

    }

    private Transcript createTranscript(String studentID) {
        JSONParser parser = new JSONParser();
        String filePath = "Iteration_2/src/main/java/" + studentID + ".json";

        try (FileReader reader = new FileReader(filePath)) {
            // Parse the JSON as an object (not an array)
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            int semester = ((Long) jsonObject.get("year")).intValue();
            ArrayList<Course> completedCourses = readCoursesForStudents(filePath, "completedCourses");
            ArrayList<Course> currentCourses = readCoursesForStudents(filePath, "currentCourses");
            ArrayList<Course> waitedCourses = readCoursesForStudents(filePath, "waitedCourses");
            ArrayList<CourseSection> currentSections = readSectionsForStudents(filePath, "currentSections");
            ArrayList<CourseSection> waitedSections = readSectionsForStudents(filePath, "waitedSections");
            int waitedCoursesSize = waitedCourses.size();
            int waitedSectionsSize = waitedSections.size();
            for (int i = 0; i < waitedCoursesSize; i++) {
                for (int j = 0; j < waitedSectionsSize; j++) {
                    if (waitedCourses.get(i).getCourseId()
                            .equals(waitedSections.get(j).getParentCourse().getCourseId())) {
                        waitedCourses.get(i).getCourseSections().add(waitedSections.get(j));
                    }
                }
            }
            // Return the Transcript with completed and current courses
            Transcript returnTranscript = new Transcript(completedCourses, currentCourses, waitedCourses,
                    currentSections, waitedSections);
            returnTranscript.setSemester(semester);
            return returnTranscript;
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

    public ArrayList<String> readClassrooms() {
        JSONParser parser = new JSONParser();
        String filePath = "Iteration_2/src/main/java/parameters.json";
        ArrayList<String> classrooms = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath)) {
            JSONObject jsonData = (JSONObject) parser.parse(reader);
            JSONArray classroomsArray = (JSONArray) jsonData.get("classrooms");
            for (Object classroomObj : classroomsArray) {
                JSONObject classroomJson = (JSONObject) classroomObj;
                classrooms.add((String) classroomJson.get("name"));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return classrooms;
    }
    /*
     * protected void fillCourseData(JSONObject courseJson, ArrayList<Course>
     * courses, String courseListType) {
     * JSONArray completedCourses = new JSONArray();
     * for (Course course : courses) {
     * JSONObject courseData = new JSONObject();
     * courseData.put("courseID", course.getCourseId());
     * courseData.put("courseName", course.getCourseName());
     * courseData.put("credits", course.getCredits());
     * if (courseListType.equals("completedCourses"))
     * courseData.put("grade", course.getGrade());
     * completedCourses.add(courseData);
     * }
     * courseJson.put(courseListType, completedCourses);
     * }
     */

    private JSONObject fillNewCoursData(Course course) {
        JSONObject newCourses = new JSONObject();
        newCourses.put("courseID", course.getCourseId());
        newCourses.put("name", course.getCourseName());
        newCourses.put("credits", course.getCredits());
        if (course.getPrerequisiteCourse() != null)
            newCourses.put("prerequisite", course.getPrerequisiteCourse().getCourseId());
        if (!(course instanceof NonTechnicalElectiveCourse))
            newCourses.put("year", Integer.toString(course.getSemester()));
        return newCourses;
    }

    public void writeCourseToJson() {
        String filePath = "Iteration_2/src/main/java/courseList.json";
        try (FileWriter writer = new FileWriter(filePath)) {
            JSONObject root = new JSONObject();
            JSONArray mandatoryCourses = new JSONArray();
            JSONArray technicalElectiveCourses = new JSONArray();
            JSONArray nonTechnicalElectiveCourses = new JSONArray();
            for (Course course : courses) {
                if (course instanceof MandatoryCourse)
                    mandatoryCourses.add(fillNewCoursData(course));
                else if (course instanceof TechnicalElectiveCourse)
                    technicalElectiveCourses.add(fillNewCoursData(course));
                else if (course instanceof NonTechnicalElectiveCourse)
                    nonTechnicalElectiveCourses.add(fillNewCoursData(course));
            }
            root.put("nonTechnicalElective", nonTechnicalElectiveCourses);
            root.put("mandatory", mandatoryCourses);
            root.put("technicalElective", technicalElectiveCourses);
            writer.write(root.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing student data to file.");
        }
    }

    public void writeSectionsToJson() {
        String filePath = "Iteration_2/src/main/java/courseSections.json";
        try (FileWriter writer = new FileWriter(filePath)) {
            JSONObject root = new JSONObject();
            for (Course course : courses) {
                JSONArray sections = new JSONArray();
                for (CourseSection courseSection : course.getCourseSections()) {
                    JSONObject sectionData = new JSONObject();
                    int sectionID = Integer.parseInt(courseSection.getSectionID());
                    sectionData.put("sectionID", sectionID);
                    String time = "";
                    String classroom = "";
                    if (courseSection.getTimeSlots().size() > 0) {
                        time = courseSection.getTimeSlots().get(0).getDay() + " "
                                + courseSection.getTimeSlots().get(0).getTimeInterval();
                        classroom = courseSection.getTimeSlots().get(0).getClassroom();
                    }
                    sectionData.put("time", time);
                    sectionData.put("classroom",classroom);
                    sectionData.put("capacity", courseSection.getCapacity());
                    sections.add(sectionData);
                }
                root.put(course.getCourseId(), sections);
            }
            writer.write(root.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing student data to file.");
        }
    }

}
