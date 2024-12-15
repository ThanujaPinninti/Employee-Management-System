package com.office.test;

import com.office.Employee;
import com.office.Gender;
import com.office.service.EmployeeService;
import org.junit.jupiter.api.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceTest {

    private static EmployeeService employeeService;
    private static Scanner scanner;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeAll
    public static void setup() {
        employeeService = new EmployeeService();
        scanner = new Scanner(System.in);
    }

    @AfterAll
    public static void teardown() {
        if (scanner != null) {
            scanner.close();
        }
    }

    @Test
    @Order(1)
    public void testAddEmployee() {
        System.out.println("Enter Employee ID:");
        int employeeId = readIntInput();
        System.out.println("Enter First Name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter Last Name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter Email:");
        String email = scanner.nextLine();
        System.out.println("Enter Phone Number:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter Aadhar Number:");
        String aadhar = scanner.nextLine();
        System.out.println("Enter Job Title:");
        String jobTitle = scanner.nextLine();
        System.out.println("Enter Salary:");
        double salary = readDoubleInput();
        System.out.println("Enter Department ID:");
        int departmentId = readIntInput();
        System.out.println("Enter Gender (MALE/FEMALE/OTHER):");
        String genderInput = scanner.nextLine().toUpperCase();
        Gender gender = Gender.valueOf(genderInput);
        System.out.println("Enter Date of Birth (yyyy-MM-dd):");
        Date dob = readDateInput();
        System.out.println("Enter Hire Date (yyyy-MM-dd):");
        Date hireDate = readDateInput();

        Employee employee = new Employee(employeeId, firstName, lastName, email, phoneNumber, aadhar, jobTitle, salary, departmentId, gender, dob, hireDate);
        employeeService.addEmployee(employee);

        Optional<Employee> retrieved = employeeService.getEmployeeById(employee.getEmployeeId());
        Assertions.assertTrue(retrieved.isPresent(), "Employee should be added successfully");
        System.out.println("Employee added successfully!");
    }

    @Test
    @Order(2)
    public void testGetEmployee() {
        System.out.println("Enter Employee ID to retrieve:");
        int employeeId = readIntInput();
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        employee.ifPresentOrElse(
                e -> System.out.println("Employee: " + e),
                () -> System.out.println("No employee found with the given ID.")
        );
    }

    @Test
    @Order(3)
    public void testUpdateEmployee() {
        System.out.println("Enter Employee ID to update:");
        int employeeId = readIntInput();
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        if (employee.isPresent()) {
            System.out.println("Enter new First Name:");
            String firstName = scanner.nextLine();
            System.out.println("Enter new Last Name:");
            String lastName = scanner.nextLine();
            System.out.println("Enter new Job Title:");
            String jobTitle = scanner.nextLine();
            System.out.println("Enter new Salary:");
            double salary = readDoubleInput();

            Employee updatedEmployee = new Employee(employeeId, firstName, lastName, employee.get().getEmail(),
                    employee.get().getPhoneNumber(), employee.get().getAadhar(), jobTitle, salary,
                    employee.get().getDepartmentId(), employee.get().getGender(),
                    employee.get().getDob(), employee.get().getHireDate());

            boolean isUpdated = employeeService.updateEmployeeById(employeeId, updatedEmployee);
            Assertions.assertTrue(isUpdated, "Employee should be updated successfully");
            System.out.println("Employee updated successfully!");
        } else {
            System.out.println("No employee found with the given ID.");
        }
    }


    @Test
    @Order(4)
    public void testDeleteEmployee() {
        System.out.println("Enter Employee ID to delete:");
        int employeeId = readIntInput();

        // Assuming deleteEmployeeById returns void, we can't assert directly, so we check the state instead
        employeeService.deleteEmployeeById(employeeId);

        // After deletion, verify if the employee is still present
        Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
        Assertions.assertFalse(employee.isPresent(), "Employee should be deleted successfully");
        System.out.println("Employee deleted successfully!");
    }

    // Utility methods for reading inputs
    private int readIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private double readDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Please enter a valid salary.");
            scanner.next();
        }
        double input = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        return input;
    }

    private Date readDateInput() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return DATE_FORMAT.parse(input);
            } catch (ParseException e) {
                System.out.println("Please enter a valid date in the format yyyy-MM-dd.");
            }
        }
    }
}
