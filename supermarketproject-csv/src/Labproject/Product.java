package Labproject;

import java.util.Random;

public class Product {
    private String product_id;
    private String product_name;
    private String category;
    private double price;
    private double profitadded;
    private int quantity;

    public Product() {
        setProduct_name();
        setPrice();
        setProfitaddded();
        setCategory();
        setQuantity();
        changeId();
    }

    public Product(String id, String name, String category, double price, int quantity, double profit) {
        this.product_id = id;
        this.product_name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.profitadded = profit;
    }

    public void changeId() {
        Random random = new Random();
        int number = random.nextInt(900000) + 100000;
        product_id = "P" + number;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public double getProfitdded() {
        return profitadded;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        double totalPrice = price + profitadded;
        return Main.money(totalPrice);
    }

    public void setProduct_name() {
        product_name = Main.readText("Product name: ");
    }

    public void setPrice() {
        double value = Main.readDouble("Unit cost: ", 0.01);
        price = Main.money(value);
    }

    public void setProfitaddded() {
        double value = Main.readDouble("Profit per unit: ", 0);
        profitadded = Main.money(value);
    }

    public void setCategory() {
        category = Main.readText("Category: ");
    }

    public void setQuantity() {
        quantity = Main.readInt("Quantity: ", 0);
    }

    public void updateQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    public String toCsv() {
        return product_id + "," + product_name + "," + category + "," + price + "," + quantity + "," + profitadded;
    }

    public String cashierToString() {
        return product_id + " | " + product_name + " | Selling price: $" + getTotalPrice();
    }

    public String toString() {
        return cashierToString() + " | Cost: $" + price + " | Category: " + category + " | Stock: " + quantity;
    }
}
