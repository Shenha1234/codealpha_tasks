package src.com.codealpha.stocktrading;

public class Stock {

    private String symbol;
    private String companyName;
    private double price;

    public Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    public void updatePrice(double newPrice) {

        if (newPrice <= 0) {
            throw new IllegalArgumentException(
                    "Stock price must be greater than 0."
            );
        }

        this.price = newPrice;
    }

    public void displayStock() {

        System.out.printf(
                "%-8s %-20s INR %.2f%n",
                symbol,
                companyName,
                price
        );
    }
}