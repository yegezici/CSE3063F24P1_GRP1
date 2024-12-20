from Notification import Notification
from typing import List, Optional
from Person import Person
class NotificationSystem:
    def __init__(self, notifications: Optional['Notification'] = None):
        self.notifications = notifications

    def print_user_notifications(self, user: 'Person' = None):
        for notification in self.notifications:
            if notification.receiver == user.get_id_field:
                print(notification.message)

    def create_notification(self, sender: 'Person' = None, receiver: 'Person' = None, message: str = None):
        self.notifications.append(Notification(sender, receiver, message))



        

