package Labproject;

import java.util.ArrayList;
import java.util.Random;

public class Cashier extends Employee {

    public ArrayList<Product> cart = new ArrayList<>();
    public ArrayList<Customer> customers = new ArrayList<>();
    public ArrayList<Payment> payments = new ArrayList<>();

    public Cashier() {
        super();
        readCustomersFromFile();
        readPaymentsFromFile();
    }

    public Cashier(String id, String name, String address, String phone, double hourly) {
        super(id, name, address, "Cashier", phone, hourly);
        readCustomersFromFile();
        readPaymentsFromFile();
    }

    public void addcustomer() {
        Customer customer = new Customer();
        customers.add(customer);
        writeCustomersToFile();
    }

    public void addProductToCart(String id, int quantity, Inventory inventory) {
        Product product = inventory.getProduct(id);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }
        if (quantity > product.getQuantity()) {
            System.out.println("Not enough stock.");
            return;
        }
        for (int i = 0; i < quantity; i++) {
            cart.add(product);
        }
        int remainingQuantity = product.getQuantity() - quantity;
        product.updateQuantity(remainingQuantity);
        System.out.println("Added to cart.");
    }

    public void removeFromCartById() {
        String id = Main.readText("Product ID: ");
        int choice = Main.readInt("Remove 1=one unit, 2=all units, 0=cancel: ", 0);
        if (choice == 0) {
            return;
        }
        if (choice != 1 && choice != 2) {
            System.out.println("Invalid choice.");
            return;
        }
        boolean found = false;
        for (int i = 0; i < cart.size(); i++) {
            Product product = cart.get(i);
            if (product.getProduct_id().equalsIgnoreCase(id)) {
                int newQuantity = product.getQuantity() + 1;
                product.updateQuantity(newQuantity);
                cart.remove(i);
                found = true;
                if (choice == 1) {
                    break;
                }
                i--;
            }
        }
        if (found) {
            System.out.println("Removed from cart.");
        } else {
            System.out.println("Product not in cart.");
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < cart.size(); i++) {
            total += cart.get(i).getTotalPrice();
        }
        return Main.money(total);
    }

    public void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
        }
        for (int i = 0; i < cart.size(); i++) {
            System.out.println(cart.get(i).cashierToString());
        }
        System.out.println("Cart total: $" + calculateTotal());
    }

    public void cancelCart() {
        for (int i = 0; i < cart.size(); i++) {
            Product product = cart.get(i);
            product.updateQuantity(product.getQuantity() + 1);
        }
        cart.clear();
        System.out.println("Purchase cancelled; stock restored.");
    }

    public boolean checkout(Customer customer) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return false;
        }
        String date = Main.readDate("Sale date");
        Random random = new Random();
        int spin = random.nextInt(3);
        double discount;
        if (spin == 1) {
            discount = 0.05;
        } else if (spin == 2) {
            discount = 0.10;
        } else {
            discount = 0;
        }
        System.out.println("Discount wheel: " + (discount * 100) + "%");
        Receipt receipt = new Receipt(customer, date, cart, discount);
        double cost = 0;
        for (int i = 0; i < cart.size(); i++) {
            cost += cart.get(i).getPrice();
        }
        Payment payment = new Payment(customer, receipt, cost);
        System.out.println("Total: $" + receipt.getTotal());
        boolean paid = payment.processpayment();
        if (paid == false) {
            System.out.println("Payment cancelled; items remain in cart.");
            return false;
        }
        payments.add(payment);
        writePaymentsToFile();
        receipt.writeReceiptToFile();
        receipt.printReceipt();
        cart.clear();
        System.out.println("Checkout completed.");
        return true;
    }

    public void writeCustomersToFile() {
        String text = "";
        for (int i = 0; i < customers.size(); i++) {
            text += customers.get(i).toCsv() + "\n";
        }
        Inventory.saveCsv("customers.csv", text, false);
    }

    public void readCustomersFromFile() {
        customers.clear();
        ArrayList<String[]> rows = Inventory.readCsv("customers.csv");
        for (int i = 0; i < rows.size(); i++) {
            String[] data = rows.get(i);
            if (data.length >= 5) {
                customers.add(new Customer(data[0], data[1], data[2], data[3], data[4]));
            }
        }
    }

    public void writePaymentsToFile() {
        String text = "";
        for (int i = 0; i < payments.size(); i++) {
            text += payments.get(i).toCsv() + "\n";
        }
        Inventory.saveCsv("payments.csv", text, false);
    }

    public void readPaymentsFromFile() {
        payments.clear();
        ArrayList<String[]> rows = Inventory.readCsv("payments.csv");
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).length >= 8) {
                payments.add(new Payment(rows.get(i)));
            }
        }
    }

    public void displayPayments() {
        if (payments.isEmpty()) {
            System.out.println("No payments saved.");
        }
        for (int i = 0; i < payments.size(); i++) {
            payments.get(i).displaypayment();
        }
    }

    public void displaycustomer() {
        if (customers.isEmpty()) {
            System.out.println("No customers saved.");
        }
        for (int i = 0; i < customers.size(); i++) {
            System.out.println(customers.get(i));
        }
    }
}
