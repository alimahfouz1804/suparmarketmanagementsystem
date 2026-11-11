package Labproject;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class Main {

    public static final Scanner in = new Scanner(System.in);

    public static String readText(String prompt) {
        String text;
        do {
            System.out.print(prompt);
            text = in.nextLine().trim();
            if (text.isEmpty() || text.contains(",") || text.contains("\"")) {
                System.out.println("Enter text without commas or quotation marks.");
            }
        } while (text.isEmpty() || text.contains(",") || text.contains("\""));
        return text;
    }

    public static int readInt(String prompt, int minimum) {
        int value;
        do {
            System.out.print(prompt);
            while (!in.hasNextInt()) {
                System.out.println("Enter a whole number.");
                in.nextLine();
            }
            value = in.nextInt();
            in.nextLine();
            if (value < minimum) {
                System.out.println("Minimum value: " + minimum);
            }
        } while (value < minimum);
        return value;
    }

    public static double readDouble(String prompt, double minimum) {
        double value;
        do {
            System.out.print(prompt);
            while (!in.hasNextDouble()) {
                System.out.println("Enter a number.");
                in.nextLine();
            }
            value = in.nextDouble();
            in.nextLine();
            if (!Double.isFinite(value) || value < minimum) {
                System.out.println("Enter a finite number of at least " + minimum);
            }
        } while (!Double.isFinite(value) || value < minimum);
        return value;
    }

    public static String readPhone() {
        String phone;
        boolean valid;
        do {
            phone = readText("Phone number: ");
            valid = phone.length() == 8;
            for (int i = 0; i < phone.length(); i++) {
                if (phone.charAt(i) < '0' || phone.charAt(i) > '9') {
                    valid = false;
                }
            }
            if (valid) {
                String prefix = phone.substring(0, 2);
                valid = prefix.equals("03") || prefix.equals("70") || prefix.equals("71")
                || prefix.equals("76") || prefix.equals("78") || prefix.equals("81");
            }
            if (!valid) {
                System.out.println("Enter a valid 8-digit phone number.");
            }
        } while (!valid);
        return phone;
    }

    public static boolean validDate(String date) {
        if (date.length() != 10 || date.charAt(2) != '/' || date.charAt(5) != '/') {
            return false;
        }
        for (int i = 0; i < date.length(); i++) {
            if (i != 2 && i != 5 && (date.charAt(i) < '0' || date.charAt(i) > '9')) {
                return false;
            }
        }
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6));
        if (year < 1900 || month < 1 || month > 12) {
            return false;
        }
        int[] days = {
            31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        };
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
            days[1] = 29;
        }
        return day >= 1 && day <= days[month - 1];
    }

    public static String readDate(String prompt) {
        String date;
        do {
            date = readText(prompt + " (dd/mm/yyyy): ");
            if (!validDate(date)) {
                System.out.println("Invalid date.");
            }
        } while (!validDate(date));
        return date;
    }

    public static double money(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static void main(String[] args) {
        System.out.println("===== SUPERMARKET MANAGEMENT SYSTEM =====");
        Inventory inventory = new Inventory();
        inventory.readProductsFromFile();
        ArrayList<String[]> staff = Inventory.readCsv("staff.csv");
        Cashier cashier;
        Manager manager;
        if (staff.size() >= 2 && staff.get(0).length >= 6 && staff.get(1).length >= 6) {
            String[] cashierData = staff.get(0);
            String[] managerData = staff.get(1);
            cashier = new Cashier(cashierData[0], cashierData[1], cashierData[2], cashierData[4], Double.parseDouble(cashierData[5]));
            manager = new Manager(managerData[0], managerData[1], managerData[2], managerData[4], Double.parseDouble(managerData[5]));
        } else {
            System.out.println("Cashier information:");
            cashier = new Cashier();
            System.out.println("Manager information:");
            manager = new Manager();
            Inventory.saveCsv("staff.csv", cashier.toCsv() + "\n" + manager.toCsv() + "\n", false);
        }
        String[] files = {
            "products.csv", "employees.csv", "customers.csv", "suppliers.csv",
            "orders.csv", "payments.csv", "receipts.csv", "sale_items.csv"
        };
        for (int i = 0; i < files.length; i++) {
            if (!new File(files[i]).exists()) {
                Inventory.saveCsv(files[i], "", false);
            }
        }
        int choice;
        do {
            System.out.println();
            System.out.println("1. Manager");
            System.out.println("2. Cashier");
            System.out.println("3. Accounting");
            System.out.println("4. Exit");
            choice = readInt("Choice: ", 1);
            switch (choice) {
                case 1:
                    String password = readText("Manager password: ");
                    if (!password.equals("1234")) {
                        System.out.println("Wrong password.");
                        break;
                    }
                    int managerChoice;
                    do {
                        System.out.println("\n===== MANAGER MENU =====");
                        System.out.println("1. Add product");
                        System.out.println("2. Remove product");
                        System.out.println("3. Display products");
                        System.out.println("4. Search ID");
                        System.out.println("5. Search category");
                        System.out.println("6. Add employee");
                        System.out.println("7. Remove employee");
                        System.out.println("8. Display employees");
                        System.out.println("9. Employee salary");
                        System.out.println("10. Order products");
                        System.out.println("11. Back");
                        System.out.println("12. Display saved orders");
                        System.out.println("13. Display suppliers");
                        managerChoice = readInt("Choice: ", 1);
                        switch (managerChoice) {
                            case 1:
                                inventory.addproduct();
                                break;
                            case 2:
                                inventory.removeproduct();
                                break;
                            case 3:
                                inventory.displayproduct();
                                break;
                            case 4:
                                inventory.findbyid();
                                break;
                            case 5:
                                inventory.displayproductsbycategory();
                                break;
                            case 6:
                                manager.addEmployee();
                                break;
                            case 7:
                                manager.removeEmployeeById();
                                break;
                            case 8:
                                manager.displayAllEmployees();
                                break;
                            case 9:
                                manager.calculateTotalSalary();
                                break;
                            case 10:
                                Supplier supplier = new Supplier();
                                Order order = new Order(supplier);
                                do {
                                    order.addproduct(inventory);
                                } while (readInt("Add another? (1=yes, 0=no): ", 0) == 1);
                                if (order.getProducts().isEmpty()) {
                                    break;
                                }
                                supplier.writeSupplierToFile();
                                order.writeOrderToFile();
                                inventory.receiveOrder(order);
                                order.displayorder();
                                break;
                            case 11:
                                break;
                            case 12:
                                Order.displaySavedOrders();
                                break;
                            case 13:
                                Supplier.displaySavedSuppliers();
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    } while (managerChoice != 11);
                    break;
                case 2:
                    int cashierChoice;
                    do {
                        System.out.println("\n===== CASHIER MENU =====");
                        System.out.println("1. Customer purchase");
                        System.out.println("2. Display customers");
                        System.out.println("3. Back");
                        System.out.println("4. Display payments");
                        System.out.println("5. Display saved receipts");
                        cashierChoice = readInt("Choice: ", 1);
                        switch (cashierChoice) {
                            case 1:
                                cashier.addcustomer();
                                Customer customer = cashier.customers.get(cashier.customers.size() - 1);
                                int cartChoice;
                                do {
                                    System.out.println();
                                    System.out.println("1. Add item");
                                    System.out.println("2. Remove item");
                                    System.out.println("3. Display cart");
                                    System.out.println("4. Pay");
                                    System.out.println("5. Cancel purchase");
                                    cartChoice = readInt("Choice: ", 1);
                                    switch (cartChoice) {
                                        case 1:
                                            inventory.displayproduct();
                                            String id = readText("Product ID: ");
                                            int quantity = readInt("Quantity: ", 1);
                                            cashier.addProductToCart(id, quantity, inventory);
                                            break;
                                        case 2:
                                            cashier.removeFromCartById();
                                            break;
                                        case 3:
                                            cashier.displayCart();
                                            break;
                                        case 4:
                                            if (cashier.checkout(customer)) {
                                                cartChoice = 5;
                                            }
                                            break;
                                        case 5:
                                            cashier.cancelCart();
                                            break;
                                        default:
                                            System.out.println("Invalid choice.");
                                    }
                                } while (cartChoice != 5);
                                inventory.writeProductsToFile();
                                break;
                            case 2:
                                cashier.displaycustomer();
                                break;
                            case 3:
                                break;
                            case 4:
                                cashier.displayPayments();
                                break;
                            case 5:
                                Receipt.displaySavedReceipts();
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    } while (cashierChoice != 3);
                    break;
                case 3:
                    System.out.println(new Accounting(cashier).generalReport());
                    break;
                case 4:
                    System.out.println("System closed.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);
    }
}
