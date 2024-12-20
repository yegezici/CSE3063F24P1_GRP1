from Person import Person
class Notification:
    def __init__(self, sender: 'Person' = None, receiver: 'Person' = None, message: str = None):
        self.sender = sender
        self.receiver = receiver
        self.message = message

    def get_receiver(self):
        return self.receiver

    def get_sender(self):
        return self.sender

    def get_message(self):
        return self.message

