public class Controller {
    public static void main(String[] args) {
        CourseRegistration courseReg = new CourseRegistration();
        courseReg.init(); 
       System.out.println(courseReg.students.get(0).getName()); 
    }
}
