package Labproject;

import java.util.ArrayList;

public class Order {
    private String orderid;
    private String arrivaldate;
    private Supplier supplier;
    private ArrayList<Product> orderedproducts = new ArrayList<>();

    public Order(Supplier supplier) {
        this.supplier = supplier;
        orderid = Inventory.nextId("O", "orders.csv");
        arrivaldate = Main.readDate("Order date");
    }

    public Order(String id, String date, Supplier supplier) {
        orderid = id;
        arrivaldate = date;
        this.supplier = supplier;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getArrivaldate() {
        return arrivaldate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public ArrayList<Product> getProducts() {
        return orderedproducts;
    }

    public void addproduct(Inventory inventory) {
        String id = Main.readText("Existing product ID, or NEW: ");
        Product product;
        if (id.equalsIgnoreCase("NEW")) {
            product = new Product();
            boolean duplicate;
            do {
                duplicate = inventory.getProduct(product.getProduct_id()) != null;
                for (int i = 0; i < orderedproducts.size(); i++) {
                    if (orderedproducts.get(i).getProduct_id().equalsIgnoreCase(product.getProduct_id())) {
                        duplicate = true;
                    }
                }
                if (duplicate) {
                    product.changeId();
                }
            } while (duplicate);
            if (product.getQuantity() == 0) {
                System.out.println("Order quantity must be positive.");
                return;
            }
        } else {
            Product existing = inventory.getProduct(id);
            if (existing == null) {
                System.out.println("Product not found.");
                return;
            }
            int quantity = Main.readInt("Quantity to order: ", 1);
            product = new Product(existing.getProduct_id(), existing.getProduct_name(), existing.getCategory(),
            existing.getPrice(), quantity, existing.getProfitdded());
        }
        orderedproducts.add(product);
    }

    public double Calculatetotalamount() {
        double total = 0;
        for (int i = 0; i < orderedproducts.size(); i++) {
            total += orderedproducts.get(i).getPrice() * orderedproducts.get(i).getQuantity();
        }
        return Main.money(total);
    }

    public void displayorder() {
        System.out.println("Order " + orderid + " | Date: " + arrivaldate + " | Supplier: " + supplier.getSuppliername());
        for (int i = 0; i < orderedproducts.size(); i++) {
            System.out.println(orderedproducts.get(i));
        }
        System.out.println("Order total: $" + Calculatetotalamount());
    }

    public void writeOrderToFile() {
        String text = "";
        for (int i = 0; i < orderedproducts.size(); i++) {
            Product product = orderedproducts.get(i);
            text += orderid + "," + arrivaldate + "," + supplier.getSupplierid() + "," + product.getProduct_id()
                + "," + product.getProduct_name() + "," + product.getCategory() + "," + product.getPrice()
                + "," + product.getQuantity() + "," + product.getProfitdded() + "\n";
        }
        Inventory.saveCsv("orders.csv", text, true);
    }

    public static void displaySavedOrders() {
        ArrayList<String[]> rows = Inventory.readCsv("orders.csv");
        if (rows.isEmpty()) {
            System.out.println("No orders saved.");
        }
        for (int i = 0; i < rows.size(); i++) {
            String[] data = rows.get(i);
            if (data.length >= 9) {
                System.out.println(data[0] + " | " + data[1] + " | Supplier: " + data[2]
                    + " | " + data[4] + " | Quantity: " + data[7] + " | Unit cost: $" + data[6]);
            } else if (data.length == 4) {
                System.out.println(data[0] + " | " + data[1] + " | " + data[2] + " | Quantity: " + data[3]);
            }
        }
    }
}
