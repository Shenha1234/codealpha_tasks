package src.com.codealpha.stocktrading;


import java.util.LinkedHashMap;
import java.util.Map;

public class Market {

    private Map<String, Stock> stocks;

    public Market() {

        stocks = new LinkedHashMap<>();
    }

    // =========================
    // ADD STOCK
    // =========================

    public void addStock(Stock stock) {

        stocks.put(
                stock.getSymbol().toUpperCase(),
                stock
        );
    }

    // =========================
    // GET STOCK
    // =========================

    public Stock getStock(String symbol) {

        if (symbol == null) {
            return null;
        }

        return stocks.get(
                symbol.toUpperCase()
        );
    }

    // =========================
    // DISPLAY MARKET
    // =========================

    public void displayMarket() {

        System.out.println();
        System.out.println("========== STOCK MARKET ==========");

        System.out.printf(
                "%-8s %-20s %s%n",
                "Symbol",
                "Company",
                "Price"
        );

        System.out.println(
                "---------------------------------------------"
        );

        for (Stock stock : stocks.values()) {

            stock.displayStock();
        }
    }

    // =========================
    // UPDATE PRICE
    // =========================

    public void updateStockPrice(
            String symbol,
            double newPrice) {

        Stock stock =
                getStock(symbol);

        if (stock == null) {

            throw new IllegalArgumentException(
                    "Stock not found."
            );
        }

        stock.updatePrice(newPrice);
    }
}