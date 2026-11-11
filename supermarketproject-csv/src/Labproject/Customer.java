package Labproject;

public class Customer {
    private String customer_Id;
    private String name;
    private String date_Of_Birth;
    private String phone_number;
    private String address;

    public Customer() {
        setName();
        setDateOfBirth();
        setPhone();
        setAddress();
        customer_Id = Inventory.nextId("C", "customers.csv");
    }

    public Customer(String id, String name, String birth, String phone, String address) {
        customer_Id = id;
        this.name = name;
        date_Of_Birth = birth;
        phone_number = phone;
        this.address = address;
    }

    public String getCustomerId() {
        return customer_Id;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return date_Of_Birth;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setName() {
        name = Main.readText("Customer name: ");
    }

    public void setDateOfBirth() {
        date_Of_Birth = Main.readDate("Date of birth");
    }

    public void setPhone() {
        phone_number = Main.readPhone();
    }

    public void setAddress() {
        address = Main.readText("Address: ");
    }

    public String toCsv() {
        return customer_Id + "," + name + "," + date_Of_Birth + "," + phone_number + "," + address;
    }

    public String toString() {
        return customer_Id + " | " + name + " | Born: " + date_Of_Birth + " | " + phone_number + " | " + address;
    }
}
