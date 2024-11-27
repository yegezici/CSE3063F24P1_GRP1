import java.util.Date;

public class DepartmentScheduler extends Staff{
    private String[][] courseSections;
    private String[] allClassrooms;

    public DepartmentScheduler(){
        super();
    }

    public DepartmentScheduler(String name, String surname){
        super(name, surname);
    }

    //Constructor for Department Scheduler role.
    public DepartmentScheduler(String name, String surname, Date birthdate, char gender, String ssn,
                               String courseSections[][], String allClassrooms[]){
        super(name, surname, birthdate, gender, ssn);
        this.courseSections = courseSections;
        this.allClassrooms = allClassrooms;
    }

    //Assign time slot for spesific section.
    public void assignTimeSlotToSection(CourseSection courseSection, TimeSlot timeSlot){
        //TimeSlot'un assign fonksiyonunu kullanarak courseSection'a TimeSlot ekle.
        timeSlot.assignTimeSlot(courseSection);
    }

    //Assign the lecturer to spesific section.
    public void assignLecturerToSection(CourseSection courseSection, Lecturer lecturer){
        courseSection.setLecturer(lecturer);
    }

    //Change capacity of spesific section.  
    public void manageCapacity(CourseSection courseSection, int newCapacity){
        int oldCapacity = courseSection.getCapacity();
        courseSection.setCapacity(newCapacity);

        //try-catch eklenmeli. NewCapacity OldCapacity'den büyük olmalı.
        manageWaitlist(courseSection, newCapacity - oldCapacity);
    }

    //Make changes in waitlist.
    public void manageWaitlist(CourseSection courseSection, int size){
        //Waitlist'te bekleyen öğrenciler çağırılır.


        //Size kadar öğrenci waitlist'ten çıkarılır.


        //Öğrencilerin currentCourses kısmına bu course'u ekle.

    }

}
