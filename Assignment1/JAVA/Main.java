package Assignment1.JAVA;

// SOLID Principles
// S - Single Responsibility	
// O - Open/Closed	
// L - Liskov Substitution	
// I - Interface Segregation	
// D - Dependency Inversion

abstract class BankAccount {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;

    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);

    public double getBalance() {
        return balance;
    }

    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.printf("Balance: $%.2f%n", balance);
    }
}

// Interface Segregation Principle:
// as this is a new functionality so we are creating it as seperate entity
interface Interest {
    void calculateInterest();
}

// Single Responsibility Principle:
// Each class has one responsibility — handling saving/current account type.
class SavingsAccount extends BankAccount implements Interest {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double initialBalance, double interestRate) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.printf("Withdrew $%.2f. New balance: $%.2f%n", amount, balance);
        } else {
            System.out.println("Insufficient funds or invalid withdrawal amount.");
        }
    }

    @Override
    public void calculateInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.printf("Interest of $%.2f applied. New balance: $%.2f%n", interest, balance);
    }
}

// Liskov Substitution Principle:
// CurrentAccount can be used anywhere BankAccount is expected. cannot use saving as we are having interest as extra parameter in it
class CurrentAccount extends BankAccount {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, String accountHolder, double initialBalance, double overdraftLimit) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("Deposited $%.2f. New balance: $%.2f%n", amount, balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance + overdraftLimit) {
            balance -= amount;
            System.out.printf("Withdrew $%.2f. New balance: $%.2f%n", amount, balance);
        } else {
            System.out.println("Insufficient funds or invalid withdrawal amount.");
        }
    }
}


public class Main {
    public static void main(String[] args) {
        SavingsAccount savings = new SavingsAccount("SA123", "Alice", 1000, 0.02);
        savings.displayAccountInfo();
        savings.deposit(500);
        savings.withdraw(200);
        savings.calculateInterest();
        savings.displayAccountInfo();

        System.out.println();

        CurrentAccount current = new CurrentAccount("CA123", "Bob", 2000, 500);
        current.displayAccountInfo();
        current.deposit(300);
        current.withdraw(2500);
        current.displayAccountInfo();
    }
}
