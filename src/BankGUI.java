import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankGUI extends JFrame {
    private Bank bank;
    private JTextField accountField, nameField, amountField, pinField;
    private JComboBox<String> accountTypeCombo;
    private JTextArea outputArea;
    private String currentAccount;

    public BankGUI() {
        bank = new Bank();
        setTitle("Bank Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panels
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 5, 5));

        // Create input fields
        accountField = new JTextField(15);
        nameField = new JTextField(15);
        amountField = new JTextField(15);
        pinField = new JTextField(15);
        accountTypeCombo = new JComboBox<>(new String[]{"SAVINGS", "CHECKING"});
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        // Add components to input panel
        inputPanel.add(new JLabel("Account Number:"));
        inputPanel.add(accountField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("PIN:"));
        inputPanel.add(pinField);
        inputPanel.add(new JLabel("Account Type:"));
        inputPanel.add(accountTypeCombo);

        // Create and add buttons
        JButton createButton = new JButton("Create Account");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton balanceButton = new JButton("Check Balance");
        JButton historyButton = new JButton("Transaction History");
        JButton interestButton = new JButton("Calculate Interest");
        JButton showStocksButton = new JButton("Show Stocks");
        JButton buyStockButton = new JButton("Buy Stock");
        JButton sellStockButton = new JButton("Sell Stock");
        JButton portfolioButton = new JButton("View Portfolio");

        // Add buttons to panel
        buttonPanel.add(createButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(balanceButton);
        buttonPanel.add(historyButton);
        buttonPanel.add(interestButton);
        buttonPanel.add(showStocksButton);
        buttonPanel.add(buyStockButton);
        buttonPanel.add(sellStockButton);
        buttonPanel.add(portfolioButton);

        // Add action listeners
        createButton.addActionListener(e -> createAccount());
        depositButton.addActionListener(e -> deposit());
        withdrawButton.addActionListener(e -> withdraw());
        balanceButton.addActionListener(e -> checkBalance());
        historyButton.addActionListener(e -> showHistory());
        interestButton.addActionListener(e -> calculateInterest());
        showStocksButton.addActionListener(e -> bank.showAvailableStocks());
        buyStockButton.addActionListener(e -> buyStock());
        sellStockButton.addActionListener(e -> sellStock());
        portfolioButton.addActionListener(e -> viewPortfolio());

        // Layout the main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);
    }

    // Move these methods outside the constructor
    private void buyStock() {
        if (validateAccess()) {
            String symbol = JOptionPane.showInputDialog("Enter stock symbol:");
            String sharesStr = JOptionPane.showInputDialog("Enter number of shares:");
            try {
                int shares = Integer.parseInt(sharesStr);
                bank.buyStock(accountField.getText(), symbol, shares);
            } catch (NumberFormatException ex) {
                showMessage("Invalid number of shares!");
            }
        }
    }

    private void sellStock() {
        if (validateAccess()) {
            String symbol = JOptionPane.showInputDialog("Enter stock symbol:");
            String sharesStr = JOptionPane.showInputDialog("Enter number of shares:");
            try {
                int shares = Integer.parseInt(sharesStr);
                bank.sellStock(accountField.getText(), symbol, shares);
            } catch (NumberFormatException ex) {
                showMessage("Invalid number of shares!");
            }
        }
    }

    private void viewPortfolio() {
        if (validateAccess()) {
            Account account = bank.findAccount(accountField.getText());
            if (account != null) {
                account.printInvestments();
            }
        }
    }

    private void createAccount() {
        try {
            String accNumber = accountField.getText();
            String name = nameField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String pin = pinField.getText();
            String type = (String) accountTypeCombo.getSelectedItem();

            if (accNumber.isEmpty() || name.isEmpty() || pin.isEmpty()) {
                showMessage("All fields are required!");
                return;
            }

            bank.createAccount(accNumber, name, amount, type, pin);
            showMessage("Account created successfully!");
            clearFields();
        } catch (NumberFormatException ex) {
            showMessage("Please enter a valid amount!");
        }
    }

    private void deposit() {
        performTransaction("deposit");
    }

    private void withdraw() {
        performTransaction("withdraw");
    }

    private void checkBalance() {
        if (validateAccess()) {
            bank.checkBalance(accountField.getText());
        }
    }

    private void showHistory() {
        if (validateAccess()) {
            bank.showTransactionHistory(accountField.getText());
        }
    }

    private void calculateInterest() {
        if (validateAccess()) {
            bank.calculateMonthlyInterest(accountField.getText());
        }
    }

    private void performTransaction(String type) {
        try {
            if (validateAccess()) {
                double amount = Double.parseDouble(amountField.getText());
                if (type.equals("deposit")) {
                    bank.deposit(accountField.getText(), amount);
                } else {
                    bank.withdraw(accountField.getText(), amount);
                }
            }
        } catch (NumberFormatException ex) {
            showMessage("Please enter a valid amount!");
        }
    }

    private boolean validateAccess() {
        String accNumber = accountField.getText();
        String pin = pinField.getText();
        
        if (accNumber.isEmpty() || pin.isEmpty()) {
            showMessage("Account number and PIN are required!");
            return false;
        }

        if (!bank.validatePin(accNumber, pin)) {
            showMessage("Invalid account number or PIN!");
            return false;
        }
        return true;
    }

    private void showMessage(String message) {
        outputArea.append(message + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    private void clearFields() {
        accountField.setText("");
        nameField.setText("");
        amountField.setText("");
        pinField.setText("");
    }
}