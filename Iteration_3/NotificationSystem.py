
from Notification import Notification
from typing import List, Optional
from Person import Person
from SQLiteManagement import SQLiteManagement
class NotificationSystem:
    def __init__(self, notifications: Optional[List[Notification]] = None):
        self._notifications =  []

    def print_user_notifications(self, user: Person):
        user_notifications = []
        count = 0
        for notification in self._notifications:
            if notification.get_receiver() == user.get_id_field():
                count += 1
                user_notifications.append(notification.get_message())
        print("You have " + str(count) + " notifications: ")
        for notification in user_notifications:
            print(notification)

    def create_notification(self, sender: Person, receiver: Person, message: str) -> None:
        manager = SQLiteManagement()
        notification = Notification(sender, receiver, message)
        self._notifications.append(notification)
        manager.save_notification(notification)

    def get_notifications(self) -> List[Notification]:
        return self._notifications