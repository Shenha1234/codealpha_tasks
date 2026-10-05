package src.com.codealpha.stocktrading;


public class User {

    private int userId;
    private String name;

    private double cashBalance;
    private double initialCash;

    private Portfolio portfolio;

    public User(
            int userId,
            String name,
            double initialCash) {

        if (initialCash <= 0) {
            throw new IllegalArgumentException(
                    "Initial cash must be greater than 0."
            );
        }

        this.userId = userId;
        this.name = name;
        this.cashBalance = initialCash;
        this.initialCash = initialCash;

        this.portfolio = new Portfolio();
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public double getInitialCash() {
        return initialCash;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    // =========================
    // BUY STOCK
    // =========================

    public void buyStock(
            Stock stock,
            int quantity) {

        double totalCost =
                stock.getPrice() * quantity;

        if (totalCost > cashBalance) {

            throw new IllegalArgumentException(
                    "Insufficient cash balance."
            );
        }

        portfolio.buyStock(
                stock,
                quantity
        );

        cashBalance -= totalCost;
    }

    // =========================
    // SELL STOCK
    // =========================

    public void sellStock(
            Stock stock,
            int quantity) {

        double amount =
                portfolio.sellStock(
                        stock,
                        quantity
                );

        cashBalance += amount;
    }

    // =========================
    // DISPLAY USER INFO
    // =========================

    public void displayUserInfo(Market market) {

        double holdingsValue =
                portfolio.getHoldingsValue(market);

        double totalPortfolioValue =
                cashBalance + holdingsValue;

        double profitLoss =
                totalPortfolioValue - initialCash;

        double returnPercentage =
                (profitLoss / initialCash) * 100;

        System.out.println();
        System.out.println("========== USER ACCOUNT ==========");

        System.out.println(
                "User ID       : " + userId
        );

        System.out.println(
                "Name          : " + name
        );

        System.out.printf(
                "Cash Balance  : INR %.2f%n",
                cashBalance
        );

        System.out.printf(
                "Stock Value   : INR %.2f%n",
                holdingsValue
        );

        System.out.printf(
                "Total Value   : INR %.2f%n",
                totalPortfolioValue
        );

        System.out.printf(
                "Profit / Loss : INR %.2f%n",
                profitLoss
        );

        System.out.printf(
                "Return        : %.2f%%%n",
                returnPercentage
        );
    }
}