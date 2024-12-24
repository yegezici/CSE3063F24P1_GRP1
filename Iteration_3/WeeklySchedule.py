from Logging_Config import logger
class WeeklySchedule:
    def __init__(self, student):
        self.__student = student

    def print_student_weekly_schedule(self):
        days = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
        current_courses = self.__student.get_transcript().get_current_courses()
        print(f"{len(current_courses)} courses taken this term.")

        for day in days:
            day_printed = False
            current_sections = self.__student.get_transcript().get_current_sections()

            for section in current_sections:
                time_slots = section.get_time_slots()

                if time_slots:
                    for time_slot in time_slots:
                        time_interval = time_slot.get_time_interval()

                        if time_interval and time_slot.get_day() == day:
                            if not day_printed:
                                print(f"{day}:")
                                day_printed = True

                            course_name = section.get_parent_course().get_course_name()
                            print(f"  {course_name}  {time_slot.get_classroom()}  {time_interval}")
                else:
                    logger.warning(f"No time slots available for section {section.get_section_id()}")
