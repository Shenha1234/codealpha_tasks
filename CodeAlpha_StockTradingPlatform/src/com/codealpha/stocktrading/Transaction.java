package src.com.codealpha.stocktrading;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    public enum TransactionType {
        BUY,
        SELL
    }

    private TransactionType type;
    private String stockSymbol;
    private int quantity;
    private double price;
    private LocalDateTime timestamp;

    public Transaction(
            TransactionType type,
            String stockSymbol,
            int quantity,
            double price) {

        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = LocalDateTime.now();
    }

    public TransactionType getType() {
        return type;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getTotalAmount() {
        return quantity * price;
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return String.format(
                "%-5s | %-8s | Qty: %-5d | Price: INR %.2f | Total: INR %.2f | %s",
                type,
                stockSymbol,
                quantity,
                price,
                getTotalAmount(),
                timestamp.format(formatter)
        );
    }
}