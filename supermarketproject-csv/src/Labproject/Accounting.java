package Labproject;

public class Accounting {
    private double totalRevenue;
    private double totalExpense;

    public Accounting(Cashier cashier) {
        for (int i = 0; i < cashier.payments.size(); i++) {
            totalRevenue += cashier.payments.get(i).getAmount();
            totalExpense += cashier.payments.get(i).getCost();
        }
        totalRevenue = Main.money(totalRevenue);
        totalExpense = Main.money(totalExpense);
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double calculateTotalProfit() {
        return Main.money(totalRevenue - totalExpense);
    }

    public String generalReport() {
        return "\n===== ALL SAVED SALES =====\nRevenue: $" + totalRevenue
            + "\nCost of goods sold: $" + totalExpense + "\nGross profit: $" + calculateTotalProfit();
    }
}
