import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class Portfolio {
    HashMap<String, Integer> holdings = new HashMap<>();
    double balance = 10000; // starting balance

    void buy(String symbol, int qty, double price) {
        double cost = qty * price;
        if (cost > balance) {
            System.out.println("Not enough balance!");
            return;
        }
        holdings.put(symbol, holdings.getOrDefault(symbol, 0) + qty);
        balance -= cost;
        System.out.println("Bought " + qty + " of " + symbol);
    }

    void sell(String symbol, int qty, double price) {
        int owned = holdings.getOrDefault(symbol, 0);
        if (qty > owned) {
            System.out.println("Not enough shares!");
            return;
        }
        holdings.put(symbol, owned - qty);
        balance += qty * price;
        System.out.println("Sold " + qty + " of " + symbol);
    }

    void showPortfolio(Map<String, Stock> market) {
        double totalValue = balance;
        System.out.println("\n--- Portfolio ---");
        System.out.println("Balance: " + balance);

        for (String sym : holdings.keySet()) {
            int qty = holdings.get(sym);
            double val = qty * market.get(sym).price;
            totalValue += val;
            System.out.println(sym + " → " + qty + " shares (Value: " + val + ")");
        }
        System.out.println("Total Value: " + totalValue);
    }
}

public class StockTradingPlatform {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", 180));
        market.put("GOOG", new Stock("GOOG", 2700));
        market.put("TSLA", new Stock("TSLA", 900));

        Portfolio portfolio = new Portfolio();

        while (true) {
            System.out.println("\n1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.println("\n--- Market Data ---");
                    for (Stock s : market.values())
                        System.out.println(s.symbol + " : $" + s.price);
                    break;

                case 2:
                    System.out.print("Enter symbol: ");
                    String buySym = sc.next();
                    if (!market.containsKey(buySym)) {
                        System.out.println("Invalid symbol!");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int bQty = sc.nextInt();
                    portfolio.buy(buySym, bQty, market.get(buySym).price);
                    break;

                case 3:
                    System.out.print("Enter symbol: ");
                    String sellSym = sc.next();
                    if (!market.containsKey(sellSym)) {
                        System.out.println("Invalid symbol!");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int sQty = sc.nextInt();
                    portfolio.sell(sellSym, sQty, market.get(sellSym).price);
                    break;

                case 4:
                    portfolio.showPortfolio(market);
                    break;

                case 5:
                    System.out.println("Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}