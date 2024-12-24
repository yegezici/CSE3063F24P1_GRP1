class TimeSlot:
    def __init__(self, day=None, time_interval=None, classroom=None):
        self.__day = day
        self.__time_interval = time_interval
        self.__classroom = classroom

    # Assigns a time slot to the course section.
    def assign_time_slot(self, course_section):
        if course_section and hasattr(course_section, 'get_time_slots'):
            course_section.get_time_slots().append(self)

    def get_day(self):
        return self.__day

    def get_time_interval(self):
        return self.__time_interval

    def get_classroom(self):
        return self.__classroom

    def set_time_interval(self, time_interval):
        self.__time_interval = time_interval

    def set_classroom(self, classroom):
        self.__classroom = classroom

    def set_day(self, day):
        self.__day = day
