from typing import List, Optional
from Person import Person
from Notification import Notification

class NotificationSystem:
    def __init__(self, notifications: Optional[List['Notification']] = None):
        self.__notifications = notifications or []

    def print_user_notifications(self, user: 'Person' = None):
        from Notification import Notification  # Local import to avoid circular dependency
        user_notifications = []
        count = 0
        for notification in self.get_notifications():
            print(notification.get_message())
            receiver = notification.get_receiver()
            if receiver is not None and receiver.get_ssn() == user.get_ssn():
                count += 1
                user_notifications.append(notification.get_message())

        print("You have " + str(count) + " notifications: ")
        for notification in user_notifications:
            print(notification)


    def create_notification(self, sender: Person, receiver: Person, message: str) -> None:
        notification = Notification(sender, receiver, message)
        self.__notifications.append(notification)

    def get_notifications(self):
        return self.__notifications