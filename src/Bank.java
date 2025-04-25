import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Bank implements Serializable {
    private List<Account> accounts;
    private List<Stock> availableStocks;
    private static final String DATA_FILE = "bank_data.ser";

    public Bank() {
        loadData();
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        if (availableStocks == null) {
            initializeStocks();
        }
    }

    private void initializeStocks() {
        availableStocks = new ArrayList<>();
        availableStocks.add(new Stock("AAPL", "Apple Inc.", 150.0, 1000));
        availableStocks.add(new Stock("GOOGL", "Alphabet Inc.", 2800.0, 500));
        availableStocks.add(new Stock("MSFT", "Microsoft Corporation", 300.0, 800));
        availableStocks.add(new Stock("AMZN", "Amazon.com Inc.", 3300.0, 300));
    }

    public void createAccount(String accountNumber, String accountHolderName, 
                            double initialBalance, String accountType, String pin) {
        Account account = new Account(accountNumber, accountHolderName, 
                                    initialBalance, accountType, pin);
        accounts.add(account);
        System.out.println("Account created successfully!");
        saveData();
    }

    public boolean validatePin(String accountNumber, String pin) {
        Account account = findAccount(accountNumber);
        return account != null && account.validatePin(pin);
    }

    public void showTransactionHistory(String accountNumber) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.printTransactionHistory();
        } else {
            System.out.println("Account not found");
        }
    }

    public void calculateMonthlyInterest(String accountNumber) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.calculateInterest();
            saveData();
        } else {
            System.out.println("Account not found");
        }
    }

    public Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public void deposit(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            saveData();
        } else {
            System.out.println("Account not found");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            saveData();
        } else {
            System.out.println("Account not found");
        }
    }

    public void checkBalance(String accountNumber) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.println("Account Holder: " + account.getAccountHolderName());
            System.out.println("Balance: $" + account.getBalance());
        } else {
            System.out.println("Account not found");
        }
    }

    public void buyStock(String accountNumber, String symbol, int shares) {
        Account account = findAccount(accountNumber);
        Stock stock = findStock(symbol);
        if (account != null && stock != null) {
            account.buyStock(stock, shares);
            saveData();
        }
    }

    public void sellStock(String accountNumber, String symbol, int shares) {
        Account account = findAccount(accountNumber);
        Stock stock = findStock(symbol);
        if (account != null && stock != null) {
            account.sellStock(stock, shares);
            saveData();
        }
    }

    public void showAvailableStocks() {
        System.out.println("\nAvailable Stocks:");
        for (Stock stock : availableStocks) {
            System.out.println(stock);
        }
    }

    public Stock findStock(String symbol) {
        for (Stock stock : availableStocks) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;
            }
        }
        return null;
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(DATA_FILE))) {
            oos.writeObject(this);
            System.out.println("Data saved successfully");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(DATA_FILE))) {
            Bank savedBank = (Bank) ois.readObject();
            this.accounts = savedBank.accounts;
            this.availableStocks = savedBank.availableStocks;
            System.out.println("Data loaded successfully");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found or error loading data");
        }
    }
}