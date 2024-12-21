from Notification import Notification
from typing import List, Optional
from Person import Person

class NotificationSystem:
    def _init_(self, notifications: Optional[List['Notification']] = None):
        self.__notifications = notifications or []

    def print_user_notifications(self, user: 'Person' = None):
        user_notifications = []
        count = 0
        for notification in self.get_notifications():
            if notification.get_receiver() == user.get_id_field():
                count += 1
                user_notifications.append(notification.get_message())
        print("You have " + str(count) + " notifications: ")
        for notification in user_notifications:
            print(notification)

    def create_notification(self, sender: 'Person' = None, receiver: 'Person' = None, message: str = None):
        self.__notifications.append(Notification(sender, receiver, message))

    def get_notifications(self):
        return self.__notifications