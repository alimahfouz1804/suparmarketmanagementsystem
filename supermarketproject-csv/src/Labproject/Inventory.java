package Labproject;

import java.io.*;
import java.util.ArrayList;

public class Inventory {

    public ArrayList<Product> products = new ArrayList<>();
    // The same two file operations are reused by all CSV files.

    public static boolean saveCsv(String file, String text, boolean append) {
        try {
            FileWriter writer = new FileWriter(file, append);
            writer.write(text);
            writer.close();
            return true;
        } catch (IOException e) {
            System.out.println("Could not save " + file + ": " + e.getMessage());
            return false;
        }
    }

    public static ArrayList<String[]> readCsv(String file) {
        ArrayList<String[]> rows = new ArrayList<>();
        if (!new File(file).exists()) {
            return rows;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    rows.add(line.split(",", -1));
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Could not read " + file + ": " + e.getMessage());
        }
        return rows;
    }

    public static String nextId(String prefix, String file) {
        ArrayList<String[]> rows = readCsv(file);
        int largest = 0;
        for (int i = 0; i < rows.size(); i++) {
            String id = rows.get(i)[0];
            if (id.startsWith(prefix)) {
                String number = id.substring(prefix.length());
                boolean digits = !number.isEmpty() && number.length() < 9;
                for (int j = 0; j < number.length(); j++) {
                    if (number.charAt(j) < '0' || number.charAt(j) > '9') {
                        digits = false;
                    }
                }
                if (digits) {
                    int currentId = Integer.parseInt(number);
                    if (currentId > largest) {
                        largest = currentId;
                    }
                }
            }
        }
        return prefix + (largest + 1);
    }

    public Product getProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProduct_id().equalsIgnoreCase(id)) {
                return products.get(i);
            }
        }
        return null;
    }

    public void addproduct() {
        Product product = new Product();
        while (getProduct(product.getProduct_id()) != null) {
            product.changeId();
        }
        products.add(product);
        writeProductsToFile();
        System.out.println(product);
    }

    public void removeproduct() {
        Product product = getProduct(Main.readText("Product ID to remove: "));
        if (product == null) {
            System.out.println("Product not found.");
        } else {
            products.remove(product);
            writeProductsToFile();
            System.out.println("Product removed.");
        }
    }

    public void displayproduct() {
        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
        }
        for (int i = 0; i < products.size(); i++) {
            System.out.println(products.get(i));
        }
    }

    public void findbyid() {
        Product product = getProduct(Main.readText("Product ID: "));
        if (product == null) {
            System.out.println("Product not found.");
        } else {
            System.out.println(product);
        }
    }

    public void displayproductsbycategory() {
        String category = Main.readText("Category: ");
        boolean found = false;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getCategory().equalsIgnoreCase(category)) {
                System.out.println(products.get(i));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching products.");
        }
    }

    public void receiveOrder(Order order) {
        for (int i = 0; i < order.getProducts().size(); i++) {
            Product incoming = order.getProducts().get(i);
            Product existing = getProduct(incoming.getProduct_id());
            if (existing == null) {
                products.add(incoming);
            } else {
                existing.updateQuantity(existing.getQuantity() + incoming.getQuantity());
            }
        }
        writeProductsToFile();
    }

    public void writeProductsToFile() {
        String text = "";
        for (int i = 0; i < products.size(); i++) {
            text += products.get(i).toCsv() + "\n";
        }
        saveCsv("products.csv", text, false);
    }

    public void readProductsFromFile() {
        products.clear();
        ArrayList<String[]> rows = readCsv("products.csv");
        for (int i = 0; i < rows.size(); i++) {
            String[] data = rows.get(i);
            if (data.length >= 5) {
                double profit = 0;
                if (data.length >= 6) {
                    profit = Double.parseDouble(data[5]);
                }
                products.add(new Product(data[0], data[1], data[2], Double.parseDouble(data[3]),
                Integer.parseInt(data[4]), profit));
            }
        }
    }
}
