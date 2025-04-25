public class Stock {
    private String symbol;
    private String companyName;
    private double currentPrice;
    private int sharesAvailable;

    public Stock(String symbol, String companyName, double currentPrice, int sharesAvailable) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.sharesAvailable = sharesAvailable;
    }

    public String getSymbol() { return symbol; }
    public String getCompanyName() { return companyName; }
    public double getCurrentPrice() { return currentPrice; }
    public int getSharesAvailable() { return sharesAvailable; }
    
    public void updatePrice(double newPrice) {
        this.currentPrice = newPrice;
    }
    
    public void updateShares(int shares) {
        this.sharesAvailable += shares;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - $%.2f per share, %d shares available", 
            companyName, symbol, currentPrice, sharesAvailable);
    }
}