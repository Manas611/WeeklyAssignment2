import java.io.*;
import java.util.*;

// Employee interface
interface Employee {
    int getEmployeeID();
    String getName();
    String getPosition();
    double getSalary();
}

// FullTimeEmployee class implementing the Employee interface
class FullTimeEmployee implements Employee, Serializable {
    public static final long serialVersionUID = 1L;
    public int employeeID;
    public String name;
    public String position;
    public double salary;

    public FullTimeEmployee(int employeeID, String name, String position, double salary) {
        this.employeeID = employeeID;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    @Override
    public int getEmployeeID() {
        return employeeID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPosition() {
        return position;
    }

    @Override
    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee ID: " + employeeID + ", Name: " + name + ", Position: " + position + ", Salary: Rs " + salary;
    }
}

// Employee Management System class
public class Assignment2 {
    private List<Employee> employees;

    public Assignment2() {
        employees = new ArrayList<>();
    }

    // Add Employee
    public void addEmployee(Employee employee) throws IllegalArgumentException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        for (Employee emp : employees) {
            if (emp.getEmployeeID() == employee.getEmployeeID()) {
                throw new IllegalArgumentException("Employee ID must be unique");
            }
        }
        employees.add(employee);
    }

    // Remove Employee
    public void removeEmployee(int employeeID) throws NoSuchElementException {
        for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext(); ) {
            Employee emp = iterator.next();
            if (emp.getEmployeeID() == employeeID) {
                iterator.remove();
                return;
            }
        }
        throw new NoSuchElementException("Employee not found with ID: " + employeeID);
    }

    // Display Employee Information
    public void displayEmployeeInfo(int employeeID) throws NoSuchElementException {
        for (Employee emp : employees) {
            if (emp.getEmployeeID() == employeeID) {
                System.out.println(emp);
                return;
            }
        }
        throw new NoSuchElementException("Employee not found with ID: " + employeeID);
    }

    // Display All Employees
    public void displayAllEmployees() {
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    // Calculate Total Salary
    public double calculateTotalSalary() {
        double totalSalary = 0;
        for (Employee emp : employees) {
            totalSalary += emp.getSalary();
        }
        return totalSalary;
    }

    // Sort Employees by Salary
    public void sortEmployeesBySalary() {
        Collections.sort(employees, Comparator.comparingDouble(Employee::getSalary));
    }

    // Search Employees by name, position, or salary range
    public List<Employee> searchEmployees(String keyword) {
        List<Employee> searchResults = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getName().contains(keyword) || emp.getPosition().contains(keyword) || String.valueOf(emp.getSalary()).contains(keyword)) {
                searchResults.add(emp);
            }
        }
        return searchResults;
    }

    // Update Employee Information
    public void updateEmployeeInfo(int employeeID, String newName, String newPosition, double newSalary) throws NoSuchElementException {
        for (Employee emp : employees) {
            if (emp.getEmployeeID() == employeeID) {
                ((FullTimeEmployee) emp).name = newName;
                ((FullTimeEmployee) emp).position = newPosition;
                ((FullTimeEmployee) emp).salary = newSalary;
                return;
            }
        }
        throw new NoSuchElementException("Employee not found with ID: " + employeeID);
    }

    // Serialization
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(employees);
            System.out.println("Employee data saved to file: " + filename);
        } catch (IOException e) {
            System.err.println("Error saving employee data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            employees = (List<Employee>) ois.readObject();
            System.out.println("Employee data loaded from file: " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading employee data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Assignment2 system = new Assignment2();

        // Test functionalities
        try {
            // Add Employees
            system.addEmployee(new FullTimeEmployee(1, "John Doe", "Manager", 50000));
            system.addEmployee(new FullTimeEmployee(2, "Alice Smith", "Developer", 60000));
            system.addEmployee(new FullTimeEmployee(3, "Bob Johnson", "Designer", 55000));

            // Display All Employees
            System.out.println("All Employees:");
            system.displayAllEmployees();

            // Display Employee Information
            System.out.println("\nEmployee Information:");
            system.displayEmployeeInfo(2);

            // Calculate Total Salary
            System.out.println("\nTotal Salary: Rs" + system.calculateTotalSalary());

            // Sort Employees by Salary
            system.sortEmployeesBySalary();
            System.out.println("\nEmployees sorted by Salary:");
            system.displayAllEmployees();

            // Update Employee Information
            system.updateEmployeeInfo(1, "John Smith", "Senior Manager", 60000);
            System.out.println("\nUpdated Employee Information:");
            system.displayEmployeeInfo(1);

            // Remove Employee
            system.removeEmployee(3);
            System.out.println("\nEmployees after removal:");
            system.displayAllEmployees();

            // Serialization
            system.saveToFile("employees.ser");
            system.loadFromFile("employees.ser");

        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
