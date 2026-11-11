package Labproject;

import java.util.ArrayList;

public class Receipt {
    private String receipt_reference;
    private String customer_name;
    private String customer_id;
    private String date_and_time;
    private double subtotal;
    private double discount;
    private double total;
    private ArrayList<String> items = new ArrayList<>();
    private String itemRows = "";

    public Receipt(Customer customer, String date, ArrayList<Product> cart, double discount) {
        receipt_reference = Inventory.nextId("R", "receipts.csv");
        customer_name = customer.getName();
        customer_id = customer.getCustomerId();
        date_and_time = date;
        this.discount = discount;
        for (int i = 0; i < cart.size(); i++) {
            Product product = cart.get(i);
            items.add(product.cashierToString());
            subtotal += product.getTotalPrice();
            itemRows += receipt_reference + "," + product.getProduct_id() + "," + product.getProduct_name()
                + ",1," + product.getPrice() + "," + product.getTotalPrice() + "\n";
        }
        subtotal = Main.money(subtotal);
        total = Main.money(subtotal * (1 - discount));
    }

    public String getReceiptReference() {
        return receipt_reference;
    }

    public String getCustomerName() {
        return customer_name;
    }

    public String customer_id() {
        return customer_id;
    }

    public String getDateAndTime() {
        return date_and_time;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotal() {
        return total;
    }

    public void printReceipt() {
        System.out.println("\n===== RECEIPT " + receipt_reference + " =====");
        System.out.println(customer_name + " | " + customer_id + " | " + date_and_time);
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i));
        }
        System.out.println("Subtotal: $" + subtotal + " | Discount: " + (discount * 100) + "% | Total: $" + total);
    }

    public void writeReceiptToFile() {
        Inventory.saveCsv("receipts.csv", receipt_reference + "," + customer_id + "," + customer_name + ","
            + date_and_time + "," + subtotal + "," + discount + "," + total + "\n", true);
        Inventory.saveCsv("sale_items.csv", itemRows, true);
    }

    public static void displaySavedReceipts() {
        ArrayList<String[]> rows = Inventory.readCsv("receipts.csv");
        ArrayList<String[]> itemData = Inventory.readCsv("sale_items.csv");
        if (rows.isEmpty()) {
            System.out.println("No receipts saved.");
        }
        for (int i = 0; i < rows.size(); i++) {
            String[] data = rows.get(i);
            if (data.length >= 7) {
                System.out.println("Receipt " + data[0] + " | " + data[2] + " | " + data[3] + " | Total: $" + data[6]);
                for (int j = 0; j < itemData.size(); j++) {
                    String[] item = itemData.get(j);
                    if (item.length >= 6 && item[0].equals(data[0])) {
                        System.out.println("  " + item[2] + " | Quantity: " + item[3] + " | Unit price: $" + item[5]);
                    }
                }
            }
        }
    }
}
