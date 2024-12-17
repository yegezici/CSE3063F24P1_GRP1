from WeeklySchedule import WeeklySchedule

class StudentInterface:
    def __init__(self, student=None, courses=None):
        self.student = student
        self.courses = courses or []
    
    def show_menu(self):
        log_out = False
        choice = self.get_choice()
        if choice == 1:
            self.show_transcripts()
        elif choice == 2:
            self.register_course()
        elif choice == 3:
            self.print_student_weekly_schedule()
        elif choice == 4:
            print("You have successfully logged out\n")
            log_out = True
        else:
            print("Enter 1, 2, 3, or 4.")
        return log_out
    
    def get_choice(self):
        print("Select an operation:\n1. Transcript\n2. Register for course\n3. Weekly Schedule\n4. Log out")
        try:
            choice = int(input("Enter your choice: "))
            return choice
        except ValueError:
            print("Enter an integer value.")
            return 0

    def show_transcripts(self):
        transcript = self.student.get_transcript()
        transcript.show_completed_courses()
        print(f"\nGPA: {transcript.calculate_gpa()}")
        transcript.show_current_courses()
        transcript.show_waited_courses()

    def register_course(self):
        available_courses = self.show_registerable_courses()
        while True:
            total_number_of_courses = len(self.student.get_transcript().get_current_courses()) + \
                                      len(self.student.get_transcript().get_waited_courses())
            if total_number_of_courses >= 5:
                print("You have reached the maximum number of courses. Redirecting to the main menu.")
                break
            
            if not available_courses:
                print("There are no available courses.")
                break

            print("These are the courses available for registration:")
            self.print_list(available_courses)
            course_choice = input("Select a course or press '0' to exit: ")

            try:
                course_choice = int(course_choice)
                if course_choice == 0:
                    break
                if course_choice < 1 or course_choice > len(available_courses):
                    print("Please enter a valid choice.")
                    continue

                selected_course = available_courses[course_choice - 1]
                self.show_available_course_sections(selected_course)
                section_choice = int(input("Select a section: ")) - 1

                if section_choice < 0 or section_choice >= len(selected_course.get_course_sections()):
                    raise IndexError("Invalid section choice!")

                selected_section = selected_course.get_course_sections()[section_choice]
                self.student.register_course(selected_section)
                print(f"{selected_course.get_course_name()}. Section {selected_section.get_section_id()} "
                      f"is sent to your advisor for approval.")
                available_courses.pop(course_choice - 1)
            except ValueError:
                print("Invalid input! Please enter an integer.")
            except IndexError as e:
                print(e)
            except Exception as e:
                print(f"An error occurred: {e}")

    def show_registerable_courses(self):
        registerable_courses = []
        transcript = self.student.get_transcript()

        for course in self.courses:
            is_completed = self.check_course_exist_in_list(course, transcript.get_completed_courses())
            is_waited = self.check_course_exist_in_list(course, transcript.get_waited_courses())
            is_current = self.check_course_exist_in_list(course, transcript.get_current_courses())

            if not is_completed and not is_waited and not is_current and self.check_prerequisite(course):
                registerable_courses.append(course)

        # Remove courses from future semesters
        registerable_courses = [
            course for course in registerable_courses if course.get_semester() <= transcript.get_semester()
        ]
        return registerable_courses

    def check_prerequisite(self, course):
        prerequisite = course.get_prerequisite_course()
        if not prerequisite:
            return True

        for completed_course in self.student.get_transcript().get_completed_courses():
            if completed_course.get_course_id() == prerequisite.get_course_id():
                return True

        return False

    def check_course_exist_in_list(self, course, course_list):
        return any(course.get_course_id() == c.get_course_id() for c in course_list)

    def print_list(self, course_list):
        for i, course in enumerate(course_list, start=1):
            print(f"{i}. {course.get_course_id()} - {course.get_course_name()}")

    def show_available_course_sections(self, course):
        for i, section in enumerate(course.get_course_sections(), start=1):
            time_slot = section.get_time_slots()[0]
            print(f"{i}. Section {section.get_section_id()} - {time_slot.get_day()} "
                  f"{time_slot.get_time_interval()} {time_slot.get_classroom()}")

    def print_student_weekly_schedule(self):
        weekly_schedule = WeeklySchedule(self.student)
        weekly_schedule.print_student_weekly_schedule()
