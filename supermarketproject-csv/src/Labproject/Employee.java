package Labproject;

public class Employee {
    private String employee_Id;
    private String name;
    private String address;
    private String role;
    private String phone_number;
    private double hourly_payment;

    public Employee() {
        setName();
        setAddress();
        setRole();
        setPhoneNumber();
        setHourlyPayment();
        employee_Id = Inventory.nextId("E", "employees.csv");
    }

    public Employee(String id, String name, String address, String role, String phone, double hourly) {
        employee_Id = id;
        this.name = name;
        this.address = address;
        this.role = role;
        phone_number = phone;
        hourly_payment = hourly;
    }

    public String getId() {
        return employee_Id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getRole() {
        return role;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public double getHourlyPayment() {
        return hourly_payment;
    }

    public void setName() {
        name = Main.readText("Employee name: ");
    }

    public void setAddress() {
        address = Main.readText("Address: ");
    }

    public void setRole() {
        role = Main.readText("Role: ");
    }

    public void setPhoneNumber() {
        phone_number = Main.readPhone();
    }

    public void setHourlyPayment() {
        hourly_payment = Main.readDouble("Hourly pay: ", 0);
    }

    public String toCsv() {
        return employee_Id + "," + name + "," + address + "," + role + "," + phone_number + "," + hourly_payment;
    }

    public String toString() {
        return employee_Id + " | " + name + " | " + role + " | " + address
            + " | Phone: " + phone_number + " | Hourly pay: $" + hourly_payment;
    }
}
