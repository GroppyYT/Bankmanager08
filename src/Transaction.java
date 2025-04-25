public class Transaction {
    private String type;
    private double amount;
    private double balanceAfter;
    private String timestamp;

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    @Override
    public String toString() {
        return String.format("%s - %s: $%.2f (Balance: $%.2f)", 
            timestamp, type, amount, balanceAfter);
    }
}