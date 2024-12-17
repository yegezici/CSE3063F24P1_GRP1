from datetime import date

class Person:
    def __init__(self, name: str = None, surname: str = None, birthdate: date = None, gender: str = None, person_id: str = None):
        self.name = name
        self.surname = surname
        self.birthdate = birthdate
        self.gender = gender
        self.ID = person_id

    def get_name_field(self):
        return self.name

    def get_surname_field(self):
        return self.surname

    def get_birthdate_field(self):
        return self.birthdate

    def get_gender_field(self):
        return self.gender

    def get_id_field(self):
        return self.ID

    def get_name(self):
        raise NotImplementedError("Subclasses must implement this method.")

    def get_surname(self):
        raise NotImplementedError("Subclasses must implement this method.")

    def get_birthdate(self):
        raise NotImplementedError("Subclasses must implement this method.")

    def get_gender(self):
        raise NotImplementedError("Subclasses must implement this method.")

    def get_id(self):
        raise NotImplementedError("Subclasses must implement this method.")
