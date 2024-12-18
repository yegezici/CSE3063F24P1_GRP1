import logging
import os

# Log dosyasının tutulacağı klasör
log_folder = "logs"
os.makedirs(log_folder, exist_ok=True)  # Klasör yoksa oluştur

# Log dosyasının yolu
log_file = os.path.join(log_folder, "course_registration.log")

# Logging yapılandırması
logging.basicConfig(
    level=logging.INFO,  # Log seviyesini belirle (INFO, DEBUG, WARNING, ERROR, CRITICAL)
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s",  # Log formatı
    handlers=[
        logging.FileHandler(log_file),  # Logları dosyaya yaz
        logging.StreamHandler()  # Aynı zamanda konsola yaz
    ]
)

# Logger nesnesi oluştur
logger = logging.getLogger("CourseRegistrationSystem")
