import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DepartmentScheduler extends Staff {
    private ArrayList<CourseSection> courseSections;
    private ArrayList<String> allClassrooms;
    private ArrayList<String> allTimeIntervals;

    public DepartmentScheduler() {
        super();
        this.allTimeIntervals = initTimeIntervals();
    }
    public DepartmentScheduler(String name, String surname, ArrayList<CourseSection> courseSections, ArrayList<String> allClassrooms) {
        super(name, surname);
        this.allTimeIntervals = initTimeIntervals();
        this.courseSections = courseSections;
        this.allClassrooms = allClassrooms;
    }

    // Constructor for Department Scheduler role.
    public DepartmentScheduler(String name, String surname, Date birthdate, char gender, String ssn,
            ArrayList<CourseSection> courseSections, ArrayList<String> allClassrooms) {
        super(name, surname, birthdate, gender, ssn);
        this.courseSections = courseSections;
        this.allClassrooms = allClassrooms;
        this.allTimeIntervals = initTimeIntervals();
    }

    public ArrayList<String> initTimeIntervals() {
        ArrayList<String> allTimeIntervals = new ArrayList<>();
        allTimeIntervals.add("8:30-9:20");
        allTimeIntervals.add("9:30-10:20");
        allTimeIntervals.add("10:30-11:20");
        allTimeIntervals.add("11:30-12:20");
        allTimeIntervals.add("12:30-13:20");
        allTimeIntervals.add("13:30-14:20");
        allTimeIntervals.add("14:30-15:20");
        allTimeIntervals.add("15:30-16:20");
        return allTimeIntervals;
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
            if (handleLecturerConflict(lecturer, courseSection))
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

    // Make changes in waitlist. Remove student from the waitlist of the Course
    // Section.
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
                int sizeNew = student.getTranscript().getWaitedCourses().size();
                for(int j = 0; j < sizeNew; j++){
                    if(student.getTranscript().getWaitedCourses().get(j).getCourseId().equals(courseSection.getParentCourse().getCourseId())){
                        student.getTranscript().deleteFromWaitedCourse(student.getTranscript().getWaitedCourses().get(j));
                    }
                }
                student.getTranscript().addCurrentSection(courseSection);
                student.getTranscript().deleteFromWaitedSections(courseSection);

                // Remove student from the waitlist.
                studentsToSection.remove(0);
            }

            courseSection.setWaitList(studentsToSection);
        } catch (Exception e) {
            System.err.println("Error managing waitlist: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // TİMESLOTU NULL OLAN BİR SECTION SEÇERSE;
    // -ÖNCE SAAT SOR. SONRA CLASSROOM
    // UPDATE SECTION SEÇERSE; (O SECTION'IN ÖNCE SAAT KISMINI NULL YAP Kİ O DA
    // SEÇENEK LİSTESİNDE GÖZÜKSÜN)

    // DepartmentSchedulerInterface önce CourseSection listesinden seçim yapılacak.
    // Seçilen ders yeni oluşturulduysa("TimeSlot kısmı null ise veya capacity
    // girilmemişse diye kontrol edilebilir")
    // -handleTimeConflict methodunu çağır, (bu method seçilebilecek zaman
    // aralıklarını döndürüyor)
    // -handleClassroomConflict methodunu çağır, (bu method seçilen zamana göre
    // seçilebilecek sınıfları döndürüyor)
    // -Kullanıcının seçtiği time interval'lar ile classroomu kullanarak TimeSlot
    // objesi oluştur ve courseSection.setTimeSlot ile eşleştir.

    // Check time conflict for CourseSections whichs is in the same semester.
    // Semester Courses are needed.
    public ArrayList<String> handleTimeConflict(ArrayList<CourseSection> semesterCourses, String day) {
        // Saatleri seçmesi istenecek. Birden fazla saat seçilebilir. Bu method aynı
        // yıldaki diğer tüm sectionların saatine bakmalı ve saat çakışmalarını
        // engellemeli
        ArrayList<String> availableTimeIntervals = new ArrayList<>(allTimeIntervals);

        try {
            // semesterCourses null ise hata fırlat
            if (semesterCourses == null) {
                throw new IllegalArgumentException("Semester courses list cannot be null.");
            }
    
            // SemesterCourses içindeki TimeSlots'ları kontrol et
            for (int i = 0; i < semesterCourses.size(); i++) {
                ArrayList<TimeSlot> timeSlots = semesterCourses.get(i).getTimeSlots();
    
                // Gün ve zaman aralığı kontrolü
                for (int k = 0; k < timeSlots.size(); k++) {
                    String timeInterval = timeSlots.get(k).getTimeInterval();
                    String sectionDay = timeSlots.get(k).getDay();
                    if (sectionDay.equals(day)) {
                        availableTimeIntervals.remove(timeInterval);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1); // Hata durumunda programı sonlandır
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    
        return availableTimeIntervals;
    }
    

    // Returns x'th semester courses as ArrayList.
    public ArrayList<CourseSection> semesterXCourses(int x) {
        ArrayList<CourseSection> semesterXCourses = new ArrayList<>();

        for (int i = 0; i < courseSections.size(); i++) {
            if (courseSections.get(i).getParentCourse().getSemester() == x) {
                semesterXCourses.add(courseSections.get(i));
            }
        }

        return semesterXCourses;
    }

    // Check classroom conflict for CourseSection.
    public ArrayList<String> handleClassroomConflict(String day, String timeInterval) {
       // Return classrooms which can be selected. So, decide whether the classroom is selected before or not.
        ArrayList<String> availableClassrooms = new ArrayList<>(allClassrooms);

        for (int i = 0; i < courseSections.size(); i++) {
            ArrayList<TimeSlot> timeSlots = courseSections.get(i).getTimeSlots();
            for (int k = 0; k < timeSlots.size(); k++) {
                TimeSlot slot = timeSlots.get(k);
                // Gün ve zaman aralığını kontrol et
                if (slot.getDay().equals(day) && slot.getTimeInterval().equals(timeInterval)) {
                    String whichClassroom = findWhichClassroom(slot.getClassroom());
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
    public boolean handleLecturerConflict(Lecturer lecturer, CourseSection courseSection) {
        boolean availability = true;
        int size = courseSections.size();
        for (int i = 0; i < size; i++) {
            if (courseSections.get(i).getLecturer() == lecturer) {
                for (int j = 0; i < courseSections.get(i).getTimeSlots().size(); j++) {
                    for (int k = 0; k < courseSection.getTimeSlots().size(); k++) {
                        if (courseSections.get(i).getTimeSlots().get(j).getTimeInterval() == courseSection
                                .getTimeSlots().get(k).getTimeInterval()) {
                            availability = false;
                        }
                    }
                }
            }
        }
        return availability;
    }
    // Returns a list of available days based on current course schedules and time intervals.
    public ArrayList<String> getAvailableDays(ArrayList<CourseSection> semesterCourses) {
        ArrayList<String> allDays = new ArrayList<>();
        allDays.add("Monday");
        allDays.add("Tuesday");
        allDays.add("Wednesday");
        allDays.add("Thursday");
        allDays.add("Friday");

        try {
            if (semesterCourses == null) {
                throw new IllegalArgumentException("Semester courses list cannot be null.");
            }

            // Tüm günler ve saat aralıklarını kontrol etmek için bir harita oluştur.
            HashMap<String, ArrayList<String>> dayToTimeIntervals = new HashMap<>();
            for (String day : allDays) {
                dayToTimeIntervals.put(day, new ArrayList<>(allTimeIntervals));
            }

            // Dolu olan zaman aralıklarını günlerden çıkar.
            for (CourseSection section : semesterCourses) {
                for (TimeSlot timeSlot : section.getTimeSlots()) {
                    String day = timeSlot.getDay();
                    String timeInterval = timeSlot.getTimeInterval();

                    // Eğer gün varsa ve zaman aralığı doluysa, onu kaldır.
                    if (dayToTimeIntervals.containsKey(day)) {
                        dayToTimeIntervals.get(day).remove(timeInterval);
                    }
                }
            }

            // Eğer bir günün tüm zaman aralıkları doluysa, o günü listeden çıkar.
            allDays.removeIf(day -> dayToTimeIntervals.get(day).isEmpty());
        } catch (Exception e) {
            System.err.println("Error while finding available days: " + e.getMessage());
        }

        return allDays;
    }

}
