class TimeSlot:
    def __init__(self, day=None, time_interval=None, classroom=None):
        self.day = day
        self.time_interval = time_interval
        self.classroom = classroom

    def assign_time_slot(self, course_section):
        if course_section and hasattr(course_section, 'get_time_slots'):
            course_section.get_time_slots().append(self)

    def get_day(self):
        return self.day

    def get_time_interval(self):
        return self.time_interval

    def get_classroom(self):
        return self.classroom

    def set_time_interval(self, time_interval):
        self.time_interval = time_interval

    def set_classroom(self, classroom):
        self.classroom = classroom

    def set_day(self, day):
        self.day = day
