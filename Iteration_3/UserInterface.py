from abc import ABC, abstractmethod

class UserInterface(ABC):

    @abstractmethod
    def show_menu(self) -> bool:
        pass

    @abstractmethod
    def get_choice(self) -> int:
        pass
