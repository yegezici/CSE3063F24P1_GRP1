import java.util.ArrayList;
import java.util.Date;

public class DepartmentScheduler extends Staff {
    private ArrayList<CourseSection> courseSections;
    private ArrayList<String> allClassrooms;
    private ArrayList<String> allTimeIntervals;

    public DepartmentScheduler() {
        super();
    }

    public DepartmentScheduler(String name, String surname) {
        super(name, surname);
    }

    // Constructor for Department Scheduler role.
    public DepartmentScheduler(String name, String surname, Date birthdate, char gender, String ssn,
                               ArrayList<CourseSection> courseSections, ArrayList<String> allClassrooms) {
        super(name, surname, birthdate, gender, ssn);
        this.courseSections = courseSections;
        this.allClassrooms = allClassrooms;
        allTimeIntervals.add("8:30-9:20");
        allTimeIntervals.add("9:30-10:20");
        allTimeIntervals.add("10:30-11:20");
        allTimeIntervals.add("11:30-12:20");
        allTimeIntervals.add("13:00-13:50");
        allTimeIntervals.add("14:00-14:50");
        allTimeIntervals.add("15:00-15:50");
        allTimeIntervals.add("16:00-17:00");
    }

    // Assign time slot for specific section.
    public void assignTimeSlotToSection(CourseSection courseSection, TimeSlot timeSlot) {
        try {
            if (courseSection == null || timeSlot == null) {
                throw new IllegalArgumentException("CourseSection or TimeSlot cannot be null.");
            }
            timeSlot.assignTimeSlot(courseSection);
        } catch (Exception e) {
            System.err.println("Error assigning time slot to section: " + e.getMessage());
        }
    }

    // Assign the lecturer to specific section.
    public void assignLecturerToSection(CourseSection courseSection, Lecturer lecturer) {
        try {
            if (courseSection == null || lecturer == null) {
                throw new IllegalArgumentException("CourseSection or Lecturer cannot be null.");
            }
            if(handleLecturerConflict(lecturer, courseSection))
                courseSection.setLecturer(lecturer);
        } catch (Exception e) {
            System.err.println("Error assigning lecturer to section: " + e.getMessage());
        }
    }

    // Change capacity of specific section.
    public void manageCapacity(CourseSection courseSection, int newCapacity) {
        try {
            if (courseSection == null) {
                throw new IllegalArgumentException("CourseSection cannot be null.");
            }
            if (newCapacity < courseSection.getCapacity()) {
                throw new IllegalArgumentException("New capacity cannot be smaller than the old capacity.");
            }

            int oldCapacity = courseSection.getCapacity();
            courseSection.setCapacity(newCapacity);

            // Manage waitlist based on new capacity.
            manageWaitlist(courseSection, newCapacity - oldCapacity);
        } catch (Exception e) {
            System.err.println("Error managing capacity: " + e.getMessage());
        }
    }

    // Make changes in waitlist. Remove student from the waitlist of the Course Section.
    public void manageWaitlist(CourseSection courseSection, int size) {
        try {
            if (courseSection == null) {
                throw new IllegalArgumentException("CourseSection cannot be null.");
            }
            ArrayList<Student> studentsToSection = courseSection.getWaitList();

            for (int i = 0; i < size && !studentsToSection.isEmpty(); i++) {
                Student student = studentsToSection.get(0);

                if (student.getTranscript() == null) {
                    throw new NullPointerException("Student's transcript cannot be null.");
                }
                
                // Add course to student's transcript.
                student.getTranscript().addCurrentCourse(courseSection.getParentCourse());

                // Remove student from the waitlist.
                studentsToSection.remove(0);
            }

            courseSection.setWaitList(studentsToSection);
        } catch (Exception e) {
            System.err.println("Error managing waitlist: " + e.getMessage());
        }
    }

    //TİMESLOTU NULL OLAN BİR SECTION SEÇERSE;
    //    -ÖNCE SAAT SOR. SONRA CLASSROOM
    //UPDATE SECTION SEÇERSE; (O SECTION'IN ÖNCE SAAT KISMINI NULL YAP Kİ O DA SEÇENEK LİSTESİNDE GÖZÜKSÜN)

    //DepartmentSchedulerInterface önce CourseSection listesinden seçim yapılacak.
    //Seçilen ders yeni oluşturulduysa("TimeSlot kısmı null ise veya capacity girilmemişse diye kontrol edilebilir")
    //   -handleTimeConflict methodunu çağır, (bu method seçilebilecek zaman aralıklarını döndürüyor)
    //   -handleClassroomConflict methodunu çağır, (bu method seçilen zamana göre seçilebilecek sınıfları döndürüyor)
    //   -Kullanıcının seçtiği time interval'lar ile classroomu kullanarak TimeSlot objesi oluştur ve courseSection.setTimeSlot ile eşleştir.


    // Check time conflict for CourseSections whichs is in the same semester.
    // Semester Courses are needed.
    public ArrayList<String> handleTimeConflict(ArrayList<CourseSection> semesterCourses){
        //Saatleri seçmesi istenecek. Birden fazla saat seçilebilir. Bu method aynı yıldaki diğer tüm sectionların saatine bakmalı ve saat çakışmalarını engellemeli
        ArrayList<String> availableTimeIntervals = allTimeIntervals;

        for(int i = 0; i < semesterCourses.size(); i++){
            ArrayList<TimeSlot> timeSlots = semesterCourses.get(i).getTimeSlots();
            for(int k = 0; k < timeSlots.size(); k++){
                String timeInterval = timeSlots.get(k).getTimeInterval();
                availableTimeIntervals.remove(timeInterval);
            }
        }

        return availableTimeIntervals;
    }

    // Returns x'th semester courses as ArrayList.
    public ArrayList<CourseSection> semesterXCourses(int x){
        ArrayList<CourseSection> semesterXCourses = new ArrayList<>();

        for(int i = 0; i < courseSections.size(); i++){
            if(courseSections.get(i).getParentCourse().getSemester() == x){
                semesterXCourses.add(courseSections.get(i));
            }
        }
        
        return semesterXCourses; 
    }

    // Check classroom conflict for CourseSection.
    public ArrayList<String> handleClassroomConflict(String timeInterval){
        // Return classrooms which can be selected. So, decide whether the classroom is selected before or not.
        ArrayList<String> availableClassrooms = allClassrooms;
        
        for(int i = 0; i < courseSections.size(); i++){
            ArrayList<TimeSlot> timeSlots = courseSections.get(i).getTimeSlots();
            for(int k = 0; k < timeSlots.size(); k++){
                if(timeSlots.get(k).getTimeInterval() == timeInterval){
                    String whichClassroom = findWhichClassroom(timeSlots.get(k).getClassroom());
                    availableClassrooms.remove(whichClassroom);
                }
            }
        }
        return availableClassrooms;
    }

    public String findWhichClassroom(String classroom) {
        String classroomFound = null;
        try {
            for (int i = 0; i < allClassrooms.size(); i++) {
                if (allClassrooms.get(i).equals(classroom)) {
                    classroomFound = classroom;
                    return classroomFound;
                }
            }
            // Eğer döngüde hiçbir eşleşme bulunmazsa, null kontrolü yapılır
            if (classroomFound == null) {
                throw new Exception("Classroom not found in the Classroom List");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1); // Programdan çıkış
        }
        return classroomFound; // Kod buraya hiç ulaşmaz ama syntax gereği yazıldı
    }
    

    // Check lecturer conflict for CourseSection.
    public boolean handleLecturerConflict(Lecturer lecturer, CourseSection courseSection){
        boolean availability = true;
        int size = courseSections.size();
        for(int i = 0; i < size; i++){
            if(courseSections.get(i).getLecturer() == lecturer){
                for(int j = 0; i < courseSections.get(i).getTimeSlots().size(); j++){
                    for(int k = 0; k < courseSection.getTimeSlots().size(); k++){
                        if(courseSections.get(i).getTimeSlots().get(j).getTimeInterval() == courseSection.getTimeSlots().get(k).getTimeInterval()){
                            availability = false;
                        }
                    }
                }
            }
        }
        return availability;
    }
}
