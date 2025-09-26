from abc import ABC, abstractmethod

# Dependency Inversion Principle
## high-level modules should not depend on low-level modules. Both should depend on abstractions.
## BankAccount class is high-level module
## SavingsAccount and CurrentAccount are low-level modules
## Both depend on the abstraction provided by BankAccount
## This allows us to add new types of accounts without modifying existing code


# open for extension, closed for modification
class BankAccount(ABC):
    # Abstract Base Class for Bank Accounts 
    @abstractmethod
    def deposit(self, amount):
        pass

    @abstractmethod
    def withdraw(self, amount):
        pass

        
    def get_balance(self):
        return self.balance

    def display_account_info(self):
        print(f"Account Number: {self.account_number}")
        print(f"Account Holder: {self.account_holder}")
        print(f"Balance: ${self.balance:.2f}")


# open for extension, closed for modification
# interface segregation principle
class Interest(ABC):
    @abstractmethod
    def calculate_interest(self):
        pass



# single responsibility principle
#inheritance
class SavingsAccount(BankAccount, Interest):
    def __init__(self, account_number, account_holder, initial_balance=0, interest_rate=0.02):
        self.account_number = account_number
        self.account_holder = account_holder
        self.balance = initial_balance
        self.interest_rate = interest_rate

    def deposit(self, amount):
        if amount > 0:
            self.balance += amount
            print(f"Deposited ${amount:.2f}. New balance: ${self.balance:.2f}")
        else:
            print("Deposit amount must be positive.")

    def withdraw(self, amount):
        if 0 < amount <= self.balance:
            self.balance -= amount
            print(f"Withdrew ${amount:.2f}. New balance: ${self.balance:.2f}")
        else:
            print("Insufficient funds or invalid withdrawal amount.")

    def calculate_interest(self):
        interest = self.balance * self.interest_rate
        self.balance += interest
        print(f"Interest of ${interest:.2f} applied. New balance: ${self.balance:.2f}")
    

# inheritance

# single responsibility principle
# Liskov substitution principle -> not implementing interest method
class CurrentAccount(BankAccount):
    def __init__(self, account_number, account_holder, initial_balance=0, overdraft_limit=500):
        self.account_number = account_number
        self.account_holder = account_holder
        self.balance = initial_balance
        self.overdraft_limit = overdraft_limit

    def deposit(self, amount):
        if amount > 0:
            self.balance += amount
            print(f"Deposited ${amount:.2f}. New balance: ${self.balance:.2f}")
        else:
            print("Deposit amount must be positive.")

    def withdraw(self, amount):
        if 0 < amount <= self.balance + self.overdraft_limit:
            self.balance -= amount
            print(f"Withdrew ${amount:.2f}. New balance: ${self.balance:.2f}")
        else:
            print("Insufficient funds or invalid withdrawal amount.")

if __name__ == "__main__":
    savings = SavingsAccount("SA123", "Alice", 1000)
    savings.display_account_info()
    savings.deposit(500)
    savings.withdraw(200)
    savings.calculate_interest()
    savings.display_account_info()

    current = CurrentAccount("CA123", "Bob", 2000)
    current.display_account_info()
    current.deposit(300)
    current.withdraw(2500)
    current.display_account_info()