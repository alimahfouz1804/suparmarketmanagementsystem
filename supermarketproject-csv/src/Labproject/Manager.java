package Labproject;

import java.util.ArrayList;

public class Manager extends Employee {
    private Employee[] employees = new Employee[20];
    private int employeecount;

    public Manager() {
        super();
        readEmployeesFromFile();
    }

    public Manager(String id, String name, String address, String phone, double hourly) {
        super(id, name, address, "Manager", phone, hourly);
        readEmployeesFromFile();
    }

    public int getEmployeeCount() {
        return employeecount;
    }

    public Employee findEmployee(String id) {
        for (int i = 0; i < employeecount; i++) {
            if (employees[i].getId().equalsIgnoreCase(id)) {
                return employees[i];
            }
        }
        return null;
    }

    public void addEmployee() {
        if (employeecount == employees.length) {
            increaseCapacity();
        }
        employees[employeecount] = new Employee();
        employeecount++;
        writeEmployeesToFile();
        System.out.println("Employee added.");
    }

    public void removeEmployeeById() {
        String id = Main.readText("Employee ID to remove: ");
        for (int i = 0; i < employeecount; i++) {
            if (employees[i].getId().equalsIgnoreCase(id)) {
                for (int j = i; j < employeecount - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employeecount--;
                employees[employeecount] = null;
                writeEmployeesToFile();
                System.out.println("Employee removed.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    public void calculateTotalSalary() {
        Employee employee = findEmployee(Main.readText("Employee ID: "));
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }
        double hours = Main.readDouble("Hours worked: ", 0);
        System.out.println("Salary: $" + Main.money(employee.getHourlyPayment() * hours));
    }

    public void displayAllEmployees() {
        if (employeecount == 0) {
            System.out.println("No employees saved.");
        }
        for (int i = 0; i < employeecount; i++) {
            System.out.println(employees[i]);
        }
    }

    private void increaseCapacity() {
        Employee[] larger = new Employee[employees.length * 2];
        for (int i = 0; i < employeecount; i++) {
            larger[i] = employees[i];
        }
        employees = larger;
    }

    public void writeEmployeesToFile() {
        String text = "";
        for (int i = 0; i < employeecount; i++) {
            text += employees[i].toCsv() + "\n";
        }
        Inventory.saveCsv("employees.csv", text, false);
    }

    public void readEmployeesFromFile() {
        employeecount = 0;
        ArrayList<String[]> rows = Inventory.readCsv("employees.csv");
        for (int i = 0; i < rows.size(); i++) {
            String[] data = rows.get(i);
            Employee employee = null;
            if (data.length >= 6) {
                employee = new Employee(data[0], data[1], data[2], data[3], data[4], Double.parseDouble(data[5]));
            } else if (data.length == 3) {
                employee = new Employee(data[0], data[1], "Unknown", "Employee", "Unknown", Double.parseDouble(data[2]));
            }
            if (employee != null) {
                if (employeecount == employees.length) {
                    increaseCapacity();
                }
                employees[employeecount] = employee;
                employeecount++;
            }
        }
    }
}
