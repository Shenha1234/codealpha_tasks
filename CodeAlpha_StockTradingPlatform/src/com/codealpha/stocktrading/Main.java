package src.com.codealpha.stocktrading;

import java.util.Scanner;

public class Main {

    private static Scanner scanner =
            new Scanner(System.in);

    public static void main(String[] args) {

        // =========================
        // CREATE MARKET
        // =========================

        Market market = new Market();

        // Sample market data

        market.addStock(
                new Stock(
                        "AAPL",
                        "Apple",
                        180.00
                )
        );

        market.addStock(
                new Stock(
                        "GOOGL",
                        "Google",
                        150.00
                )
        );

        market.addStock(
                new Stock(
                        "MSFT",
                        "Microsoft",
                        420.00
                )
        );

        market.addStock(
                new Stock(
                        "AMZN",
                        "Amazon",
                        175.00
                )
        );

        market.addStock(
                new Stock(
                        "TSLA",
                        "Tesla",
                        250.00
                )
        );

        // =========================
        // CREATE USER
        // =========================

        User user =
                new User(
                        1001,
                        "CodeAlpha Intern",
                        100000.00
                );

        // =========================
        // APPLICATION START
        // =========================

        System.out.println();
        System.out.println(
                "============================================"
        );

        System.out.println(
                "       CODEALPHA STOCK TRADING PLATFORM"
        );

        System.out.println(
                "============================================"
        );

        System.out.println(
                "Welcome, " + user.getName() + "!"
        );

        // =========================
        // MAIN MENU
        // =========================

        boolean running = true;

        while (running) {

            displayMenu();

            int choice =
                    readInt(
                            "Enter your choice: "
                    );

            try {

                switch (choice) {

                    case 1:
                        market.displayMarket();
                        break;

                    case 2:
                        buyStock(user, market);
                        break;

                    case 3:
                        sellStock(user, market);
                        break;

                    case 4:
                        user.getPortfolio()
                                .displayPortfolio(market);
                        break;

                    case 5:
                        user.displayUserInfo(market);
                        break;

                    case 6:
                        user.getPortfolio()
                                .displayTransactions();
                        break;

                    case 7:
                        updateStockPrice(market);
                        break;

                    case 8:
                        showPerformance(user, market);
                        break;

                    case 9:

                        System.out.println();
                        System.out.println(
                                "Thank you for using "
                                + "Stock Trading Platform!"
                        );

                        running = false;
                        break;

                    default:

                        System.out.println(
                                "Invalid choice. "
                                + "Please choose 1-9."
                        );
                }

            } catch (IllegalArgumentException e) {

                System.out.println();
                System.out.println(
                        "ERROR: " + e.getMessage()
                );
            }
        }

        scanner.close();
    }

    // ==========================================
    // MENU
    // ==========================================

    private static void displayMenu() {

        System.out.println();
        System.out.println(
                "============== MAIN MENU =============="
        );

        System.out.println(
                "1. Display Market Data"
        );

        System.out.println(
                "2. Buy Stock"
        );

        System.out.println(
                "3. Sell Stock"
        );

        System.out.println(
                "4. View Portfolio"
        );

        System.out.println(
                "5. View Account & Performance"
        );

        System.out.println(
                "6. View Transaction History"
        );

        System.out.println(
                "7. Update Stock Price"
        );

        System.out.println(
                "8. View Portfolio Performance"
        );

        System.out.println(
                "9. Exit"
        );

        System.out.println(
                "========================================"
        );
    }

    // ==========================================
    // BUY STOCK
    // ==========================================

    private static void buyStock(
            User user,
            Market market) {

        market.displayMarket();

        System.out.print(
                "\nEnter stock symbol: "
        );

        String symbol =
                scanner.nextLine()
                        .trim()
                        .toUpperCase();

        Stock stock =
                market.getStock(symbol);

        if (stock == null) {

            throw new IllegalArgumentException(
                    "Stock not found."
            );
        }

        int quantity =
                readInt(
                        "Enter quantity: "
                );

        user.buyStock(
                stock,
                quantity
        );

        System.out.println();

        System.out.println(
                "Stock purchased successfully!"
        );

        System.out.println(
                "Stock   : " + symbol
        );

        System.out.println(
                "Quantity: " + quantity
        );

        System.out.printf(
                "Price   : INR %.2f%n",
                stock.getPrice()
        );
    }

    // ==========================================
    // SELL STOCK
    // ==========================================

    private static void sellStock(
            User user,
            Market market) {

        user.getPortfolio()
                .displayPortfolio(market);

        System.out.print(
                "\nEnter stock symbol: "
        );

        String symbol =
                scanner.nextLine()
                        .trim()
                        .toUpperCase();

        Stock stock =
                market.getStock(symbol);

        if (stock == null) {

            throw new IllegalArgumentException(
                    "Stock not found."
            );
        }

        int quantity =
                readInt(
                        "Enter quantity to sell: "
                );

        user.sellStock(
                stock,
                quantity
        );

        System.out.println();

        System.out.println(
                "Stock sold successfully!"
        );

        System.out.println(
                "Stock   : " + symbol
        );

        System.out.println(
                "Quantity: " + quantity
        );

        System.out.printf(
                "Price   : INR %.2f%n",
                stock.getPrice()
        );
    }

    // ==========================================
    // UPDATE STOCK PRICE
    // ==========================================

    private static void updateStockPrice(
            Market market) {

        market.displayMarket();

        System.out.print(
                "\nEnter stock symbol: "
        );

        String symbol =
                scanner.nextLine()
                        .trim()
                        .toUpperCase();

        double newPrice =
                readDouble(
                        "Enter new price: "
                );

        market.updateStockPrice(
                symbol,
                newPrice
        );

        System.out.println();

        System.out.println(
                "Stock price updated successfully!"
        );
    }

    // ==========================================
    // PERFORMANCE
    // ==========================================

    private static void showPerformance(
            User user,
            Market market) {

        double cash =
                user.getCashBalance();

        double stockValue =
                user.getPortfolio()
                        .getHoldingsValue(market);

        double totalValue =
                cash + stockValue;

        double initialInvestment =
                user.getInitialCash();

        double profitLoss =
                totalValue - initialInvestment;

        double returnPercentage =
                (profitLoss / initialInvestment)
                        * 100;

        double unrealizedPL =
                user.getPortfolio()
                        .getUnrealizedProfitLoss(market);

        System.out.println();

        System.out.println(
                "========== PORTFOLIO PERFORMANCE =========="
        );

        System.out.printf(
                "Initial Investment : INR %.2f%n",
                initialInvestment
        );

        System.out.printf(
                "Cash Balance       : INR %.2f%n",
                cash
        );

        System.out.printf(
                "Current Stock Value: INR %.2f%n",
                stockValue
        );

        System.out.printf(
                "Total Portfolio    : INR %.2f%n",
                totalValue
        );

        System.out.printf(
                "Profit / Loss      : INR %.2f%n",
                profitLoss
        );

        System.out.printf(
                "Unrealized P/L     : INR %.2f%n",
                unrealizedPL
        );

        System.out.printf(
                "Return             : %.2f%%%n",
                returnPercentage
        );

        System.out.println(
                "============================================="
        );
    }

    // ==========================================
    // READ INTEGER
    // ==========================================

    private static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                String input =
                        scanner.nextLine().trim();

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid integer."
                );
            }
        }
    }

    // ==========================================
    // READ DOUBLE
    // ==========================================

    private static double readDouble(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                String input =
                        scanner.nextLine().trim();

                return Double.parseDouble(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}