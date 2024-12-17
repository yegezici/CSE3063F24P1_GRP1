from CourseRegistration import CourseRegistration
class Controller:
    @staticmethod
    def main():
        course_reg = CourseRegistration()
        course_reg.init()


if __name__ == "__main__":
    Controller.main()
