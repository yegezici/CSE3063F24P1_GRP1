import logging
import os


log_folder = "Iteration_3/logs"
os.makedirs(log_folder, exist_ok=True)  

log_file = os.path.join(log_folder, "course_registration.log")


logging.basicConfig(
    level=logging.INFO,  # (INFO, DEBUG, WARNING, ERROR, CRITICAL)
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s", 
    handlers=[
        logging.FileHandler(log_file),  
    ]
)


logger = logging.getLogger("CourseRegistrationSystem")
