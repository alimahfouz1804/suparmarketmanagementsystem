package Labproject;

public class Payment {
    private String paymentid;
    private String customerId;
    private String customerName;
    private String receiptId;
    private String date;
    private double amount;
    private double cost;
    private String paymentMethod;

    public Payment(Customer customer, Receipt receipt, double cost) {
        paymentid = Inventory.nextId("PAY", "payments.csv");
        customerId = customer.getCustomerId();
        customerName = customer.getName();
        receiptId = receipt.getReceiptReference();
        date = receipt.getDateAndTime();
        amount = receipt.getTotal();
        this.cost = Main.money(cost);
    }

    public Payment(String[] data) {
        paymentid = data[0];
        customerId = data[1];
        customerName = data[2];
        receiptId = data[3];
        date = data[4];
        amount = Double.parseDouble(data[5]);
        cost = Double.parseDouble(data[6]);
        paymentMethod = data[7];
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public double getCost() {
        return cost;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public int chooseMethod() {
        int method;
        do {
            method = Main.readInt("Payment: 1=Cash, 2=Card, 3=Cancel: ", 1);
            if (method > 3) {
                System.out.println("Invalid choice.");
            }
        } while (method > 3);
        return method;
    }

    public boolean processpayment() {
        if (paymentMethod != null) {
            return true;
        }
        int method = chooseMethod();
        if (method == 3) {
            return false;
        }
        if (method == 1) {
            double received = Main.readDouble("Cash received (minimum $" + amount + "): ", amount);
            System.out.println("Change: $" + Main.money(received - amount));
            paymentMethod = "Cash";
        } else {
            paymentMethod = "Card";
        }
        return true;
    }

    public String toCsv() {
        return paymentid + "," + customerId + "," + customerName + "," + receiptId + "," + date
            + "," + amount + "," + cost + "," + paymentMethod;
    }

    public void displaypayment() {
        System.out.println(paymentid + " | " + customerName + " | " + date + " | Receipt: " + receiptId
            + " | Amount: $" + amount + " | " + paymentMethod);
    }
}
