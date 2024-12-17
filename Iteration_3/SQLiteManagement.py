import sqlite3

class SqliteManager:
    def __init__(self):
        self.conn = sqlite3.connect('Iteration_3/database/test.db')
        self.cursor = self.conn.cursor()

    def print_table(self, table_name: str):        
        try:
            self.cursor.execute(f"SELECT * FROM {table_name}")
            rows = self.cursor.fetchall()
            for row in rows:
              print(row)
        except sqlite3.Error as e:
            print("SQLite error:", e)
    
    def find_user(self, username: str, password: str) -> bool:
        try:
            self.cursor.execute(f"SELECT * FROM users u WHERE u.UserID = '{username}' AND u.password = '{password}'")
            rows = self.cursor.fetchall()
            return len(rows) > 0
        except sqlite3.Error as e:
            print("SQLite error:", e)
            return False
        
    #def save_student(self, student):
        
        




manager = SqliteManager()
print(manager.find_user("o150121032", "password2"))
