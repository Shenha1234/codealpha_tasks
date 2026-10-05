package src.com.codealpha.stocktrading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {

    private Map<String, Integer> holdings;
    private Map<String, Double> averageBuyPrice;
    private List<Transaction> transactions;

    public Portfolio() {

        holdings = new HashMap<>();
        averageBuyPrice = new HashMap<>();
        transactions = new ArrayList<>();
    }

    // =========================
    // BUY STOCK
    // =========================

    public double buyStock(Stock stock, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than 0."
            );
        }

        String symbol = stock.getSymbol();

        double price = stock.getPrice();
        double totalCost = price * quantity;

        int oldQuantity =
                holdings.getOrDefault(symbol, 0);

        double oldAveragePrice =
                averageBuyPrice.getOrDefault(symbol, 0.0);

        double newAveragePrice =
                ((oldQuantity * oldAveragePrice)
                        + totalCost)
                        / (oldQuantity + quantity);

        holdings.put(
                symbol,
                oldQuantity + quantity
        );

        averageBuyPrice.put(
                symbol,
                newAveragePrice
        );

        transactions.add(
                new Transaction(
                        Transaction.TransactionType.BUY,
                        symbol,
                        quantity,
                        price
                )
        );

        return totalCost;
    }

    // =========================
    // SELL STOCK
    // =========================

    public double sellStock(Stock stock, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than 0."
            );
        }

        String symbol = stock.getSymbol();

        int availableQuantity =
                holdings.getOrDefault(symbol, 0);

        if (availableQuantity == 0) {
            throw new IllegalArgumentException(
                    "You do not own this stock."
            );
        }

        if (quantity > availableQuantity) {
            throw new IllegalArgumentException(
                    "You only own "
                            + availableQuantity
                            + " shares of "
                            + symbol
                            + "."
            );
        }

        double price = stock.getPrice();

        double totalAmount =
                price * quantity;

        int remainingQuantity =
                availableQuantity - quantity;

        if (remainingQuantity == 0) {

            holdings.remove(symbol);
            averageBuyPrice.remove(symbol);

        } else {

            holdings.put(
                    symbol,
                    remainingQuantity
            );
        }

        transactions.add(
                new Transaction(
                        Transaction.TransactionType.SELL,
                        symbol,
                        quantity,
                        price
                )
        );

        return totalAmount;
    }

    // =========================
    // DISPLAY HOLDINGS
    // =========================

    public void displayPortfolio(Market market) {

        System.out.println();
        System.out.println("========== YOUR HOLDINGS ==========");

        if (holdings.isEmpty()) {

            System.out.println(
                    "You do not own any stocks."
            );

            return;
        }

        System.out.printf(
                "%-8s %-10s %-15s %-15s %-15s%n",
                "Symbol",
                "Quantity",
                "Avg Cost",
                "Market Price",
                "Value"
        );

        System.out.println(
                "--------------------------------------------------------------"
        );

        for (Map.Entry<String, Integer> entry
                : holdings.entrySet()) {

            String symbol = entry.getKey();
            int quantity = entry.getValue();

            Stock stock =
                    market.getStock(symbol);

            if (stock == null) {
                continue;
            }

            double avgCost =
                    averageBuyPrice.get(symbol);

            double currentPrice =
                    stock.getPrice();

            double value =
                    quantity * currentPrice;

            System.out.printf(
                    "%-8s %-10d INR %-11.2f INR %-11.2f INR %-11.2f%n",
                    symbol,
                    quantity,
                    avgCost,
                    currentPrice,
                    value
            );
        }
    }

    // =========================
    // HOLDINGS VALUE
    // =========================

    public double getHoldingsValue(Market market) {

        double total = 0;

        for (Map.Entry<String, Integer> entry
                : holdings.entrySet()) {

            String symbol = entry.getKey();
            int quantity = entry.getValue();

            Stock stock =
                    market.getStock(symbol);

            if (stock != null) {

                total +=
                        stock.getPrice() * quantity;
            }
        }

        return total;
    }

    // =========================
    // PROFIT / LOSS
    // =========================

    public double getUnrealizedProfitLoss(
            Market market) {

        double profitLoss = 0;

        for (Map.Entry<String, Integer> entry
                : holdings.entrySet()) {

            String symbol = entry.getKey();

            int quantity = entry.getValue();

            Stock stock =
                    market.getStock(symbol);

            if (stock == null) {
                continue;
            }

            double averagePrice =
                    averageBuyPrice.get(symbol);

            double currentPrice =
                    stock.getPrice();

            profitLoss +=
                    (currentPrice - averagePrice)
                            * quantity;
        }

        return profitLoss;
    }

    // =========================
    // TRANSACTION HISTORY
    // =========================

    public void displayTransactions() {

        System.out.println();
        System.out.println("========== TRANSACTION HISTORY ==========");

        if (transactions.isEmpty()) {

            System.out.println(
                    "No transactions available."
            );

            return;
        }

        for (Transaction transaction
                : transactions) {

            System.out.println(transaction);
        }
    }

    public List<Transaction> getTransactions() {

        return transactions;
    }
}