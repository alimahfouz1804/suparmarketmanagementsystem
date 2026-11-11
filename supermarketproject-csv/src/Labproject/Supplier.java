package Labproject;

import java.util.ArrayList;

public class Supplier {
    private String supplierid;
    private String suppliername;
    private String supplierphone;
    private String supplierlocation;
    private String supplierproducttype;

    public Supplier() {
        setSupplierid();
        setSuppliername();
        setSupplierphone();
        setSupplierlocation();
        setSupplierproducttype();
    }

    public Supplier(String id, String name, String phone, String location, String type) {
        supplierid = id;
        suppliername = name;
        supplierphone = phone;
        supplierlocation = location;
        supplierproducttype = type;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public String getSupplierphone() {
        return supplierphone;
    }

    public String getSupplierlocation() {
        return supplierlocation;
    }

    public String getSupplierproducttype() {
        return supplierproducttype;
    }

    public void setSupplierid() {
        supplierid = Main.readText("Supplier ID (reuse an existing ID to update): ");
    }

    public void setSuppliername() {
        suppliername = Main.readText("Supplier name: ");
    }

    public void setSupplierphone() {
        supplierphone = Main.readPhone();
    }

    public void setSupplierlocation() {
        supplierlocation = Main.readText("Supplier location: ");
    }

    public void setSupplierproducttype() {
        supplierproducttype = Main.readText("Supplier product type: ");
    }

    public String toCsv() {
        return supplierid + "," + suppliername + "," + supplierphone + "," + supplierlocation + "," + supplierproducttype;
    }

    public void writeSupplierToFile() {
        ArrayList<String[]> rows = Inventory.readCsv("suppliers.csv");
        String text = "";
        for (int i = 0; i < rows.size(); i++) {
            String[] data = rows.get(i);
            if (data.length >= 5 && !data[0].equalsIgnoreCase(supplierid)) {
                text += new Supplier(data[0], data[1], data[2], data[3], data[4]).toCsv() + "\n";
            }
        }
        Inventory.saveCsv("suppliers.csv", text + toCsv() + "\n", false);
    }

    public static void displaySavedSuppliers() {
        ArrayList<String[]> rows = Inventory.readCsv("suppliers.csv");
        if (rows.isEmpty()) {
            System.out.println("No suppliers saved.");
        }
        for (int i = 0; i < rows.size(); i++) {
            String[] data = rows.get(i);
            if (data.length >= 5) {
                System.out.println(new Supplier(data[0], data[1], data[2], data[3], data[4]));
            }
        }
    }

    public String toString() {
        return supplierid + " | " + suppliername + " | " + supplierphone + " | " + supplierlocation + " | " + supplierproducttype;
    }
}
