from NotificationSystem import NotificationSystem
from UserInterface import UserInterface

class AdvisorInterface(UserInterface):
    def __init__(self, advisor : 'Advisor', notification_system: NotificationSystem):
        self.advisor = advisor
        self.scanner = None
        self.notification_system = notification_system

    def show_menu(self):
        log_out = False
        choice = self.get_choice()
        if choice == 1:
            log_out = self.show_students_menu()
        elif choice == 2:
            print("You have successfully logged out\n")
            log_out = True
        else:
            print("Enter 1 or 2.")
        return log_out

    def get_choice(self):
        print("1- Students Menu\n2- Log Out\nSelect an operation: ", end="")
        try:
            choice = int(input())
            return choice
        except ValueError:
            print("Enter an integer value.")
            return 0

    def show_students_menu(self):
        log_out = False
        students = self.advisor.get_students()
        number_of_students = len(students)

        for i, student in enumerate(students):
            print(f"{i + 1}- {student.get_id()}")

        try:
            student_index = int(input("Which student do you select? :\nIf you want to log out, enter \"0\": ")) - 1
        except ValueError:
            print("Enter an integer value.")
            return False

        if student_index == -1:
            return False
        elif student_index >= number_of_students or student_index < 0:
            print(f"Please write a number between 1 and {number_of_students}.")
            return False

        current_student = students[student_index]
        if current_student is None:
            print("Student is null.")

        self.student_operations(current_student)
        return log_out

    def approve_course(self, student, course_section):
        try:
            self.advisor.approve_course(student, course_section)
            self.notification_system.create_notification(sender=self, receiver=student, message="Your registeration to " + course_section.get_section_id() + " has been approved.")
            if not student.get_transcript().get_waited_courses():
                print("No more courses to approve.")
        except Exception as e:
            print(str(e))

    def reject_course(self, student, course_section):
        try:
            self.advisor.reject_course(student, course_section)
            self.notification_system.create_notification(sender=self, receiver=student, message="Your registeration to " + course_section.get_section_id() + " has been rejected.")
        except Exception as e:
            print(str(e))

    def course_operations(self, student, course_index):
        try:
            log_out = False
            # Get Course Section
            parent_course = student.get_transcript().get_waited_courses()[course_index - 1]
            course_section = None

            for section in student.get_transcript().get_waited_sections():
                if section.get_parent_course().get_course_id() == parent_course.get_course_id():
                    course_section = section

            # Check for section conflict
            if not self.advisor.check_section_conflict(student, course_section):
                print("There is a conflict between sections. The course cannot be approved! It is automatically rejected.")
                self.reject_course(student, course_section)
                return log_out

            # User confirmation
            print("Do you want to approve this course? (y/n): \nIf you want to turn back enter \"0\": ", end="")
            approve = input().strip()

            if approve not in ("0", "y", "n"):
                print("Enter \"y\", \"n\" or \"0\".")
            elif approve == "0":
                log_out = True
            elif approve == "y":
                self.approve_course(student, course_section)
            else:
                self.reject_course(student, course_section)

            return log_out

        except Exception as e:
            print(str(e))

    def student_operations(self, student):
        try:
            while True:
                if not student.get_transcript().get_waited_courses():
                    print("All waited courses have been approved. You will be directed to main menu.")
                    break

                student.get_transcript().show_waited_courses()
                try:
                    course_index = int(input("Which course do you want to select?: \nIf you want to turn back enter \"0\": "))
                    if course_index == 0:
                        break
                    size = len(student.get_transcript().get_waited_courses())
                    if course_index <= 0 or course_index > size:
                        print(f"Enter a value between 1 and {size}")
                    else:
                        if self.course_operations(student, course_index):
                            break
                except ValueError:
                    print("Enter an integer value.")
                    break

        except Exception as e:
            print(str(e))