import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account implements Serializable {
    // Add class fields
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String accountType;
    private String pin;
    private List<Transaction> transactionHistory;
    private double interestRate;
    private Map<String, Integer> investments;

    public Account(String accountNumber, String accountHolderName, double initialBalance, 
                  String accountType, String pin) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.accountType = accountType;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
        this.interestRate = accountType.equals("SAVINGS") ? 0.045 : 0.01;
        this.investments = new HashMap<>();
        
        addTransaction("INITIAL_DEPOSIT", initialBalance);
    }

    private void addTransaction(String type, double amount) {
        transactionHistory.add(new Transaction(type, amount, balance));
    }

    public boolean validatePin(String inputPin) {
        return pin.equals(inputPin);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("DEPOSIT", amount);
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addTransaction("WITHDRAWAL", amount);
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance");
        }
    }

    public void calculateInterest() {
        double interest = balance * (interestRate / 12); // Monthly interest
        balance += interest;
        addTransaction("INTEREST", interest);
        System.out.printf("Interest added: $%.2f%n", interest);
    }

    public void printTransactionHistory() {
        System.out.println("\nTransaction History for Account: " + accountNumber);
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }

    // Existing getters
    public String getAccountNumber() { return accountNumber; }
    public String getAccountHolderName() { return accountHolderName; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }

    public void buyStock(Stock stock, int shares) {
        double totalCost = stock.getCurrentPrice() * shares;
        if (totalCost <= balance && shares <= stock.getSharesAvailable()) {
            balance -= totalCost;
            investments.merge(stock.getSymbol(), shares, Integer::sum);
            addTransaction("STOCK_PURCHASE", totalCost);
            System.out.printf("Bought %d shares of %s for $%.2f%n", 
                shares, stock.getSymbol(), totalCost);
        } else {
            System.out.println("Insufficient funds or shares not available");
        }
    }

    public void sellStock(Stock stock, int shares) {
        int ownedShares = investments.getOrDefault(stock.getSymbol(), 0);
        if (ownedShares >= shares) {
            double totalValue = stock.getCurrentPrice() * shares;
            balance += totalValue;
            investments.put(stock.getSymbol(), ownedShares - shares);
            addTransaction("STOCK_SALE", totalValue);
            System.out.printf("Sold %d shares of %s for $%.2f%n", 
                shares, stock.getSymbol(), totalValue);
        } else {
            System.out.println("Insufficient shares owned");
        }
    }

    public void printInvestments() {
        System.out.println("\nInvestment Portfolio:");
        investments.forEach((symbol, shares) -> 
            System.out.printf("%s: %d shares%n", symbol, shares));
    }
}