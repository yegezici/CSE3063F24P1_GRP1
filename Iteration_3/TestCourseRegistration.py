import unittest
from unittest.mock import MagicMock
from CourseRegistration import CourseRegistration  # Burada doğru modül yolunu kullanın

class TestCourseRegistration(unittest.TestCase):

    def setUp(self):
        # Mock objeleri oluşturuyoruz
        self.manager_mock = MagicMock()
        
        # CourseRegistration nesnesini oluşturuyoruz
        self.course_registration = CourseRegistration()
        self.course_registration.manager = self.manager_mock

    def test_login_valid_user(self):
        # Geçerli bir kullanıcı ID ve şifreyi test ediyoruz
        entered_user_id = "12345"
        entered_password = "password"
        mock_person = MagicMock()  # Geçerli bir kullanıcı mock'ı
        self.manager_mock.check_user.return_value = mock_person  # check_user başarılı sonuç döndürüyor
        
        # login fonksiyonu, doğru ID ve şifreyle çalıştırılıyor
        result = self.course_registration.check_id_and_password(entered_user_id, entered_password)
        
        # check_user metodu doğru parametrelerle çağrıldı mı kontrol ediliyor
        self.manager_mock.check_user.assert_called_with(entered_user_id, entered_password)
        self.assertEqual(result, mock_person)  # Sonuç mock_person olmalı

    def test_check_id_and_password_invalid_user(self):
        # Hatalı bir kullanıcı ID ve şifreyi test ediyoruz
        entered_user_id = "invalid_id"
        entered_password = "wrong_password"
        self.manager_mock.check_user.return_value = None  # Geçersiz kullanıcı için None döndürülüyor
        
        # login fonksiyonu, hatalı ID ve şifreyle çalıştırılıyor
        result = self.course_registration.check_id_and_password(entered_user_id, entered_password)
        
        # check_user metodu doğru parametrelerle çağrıldı mı kontrol ediliyor
        self.manager_mock.check_user.assert_called_with(entered_user_id, entered_password)
        self.assertIsNone(result)  # Sonuç None olmalı

if __name__ == "__main__":
    unittest.main(verbosity=2)
