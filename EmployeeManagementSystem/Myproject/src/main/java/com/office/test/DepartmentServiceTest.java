package com.office.test;

import com.office.Department;
import com.office.service.DepartmentService;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentServiceTest {

    private static DepartmentService departmentService;
    private static Scanner scanner;

    @BeforeAll
    public static void setup() {
        departmentService = new DepartmentService();
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
    public void testAddDepartment() {
        System.out.println("Enter Department ID:");
        int departmentId = readIntInput();
        System.out.println("Enter Department Name:");
        String departmentName = scanner.nextLine();
        System.out.println("Enter Location:");
        String location = scanner.nextLine();

        Department department = new Department(departmentId, departmentName, location);
        departmentService.addDepartment(department);

        Optional<Department> retrieved = departmentService.getDepartment(departmentId);
        Assertions.assertTrue(retrieved.isPresent(), "Department should be added successfully");
    }

    @Test
    @Order(2)
    public void testGetDepartment() {
        System.out.println("Enter Department ID to retrieve:");
        int departmentId = readIntInput();
        Optional<Department> department = departmentService.getDepartment(departmentId);
        department.ifPresentOrElse(
                d -> System.out.println("Department: " + d),
                () -> System.out.println("No department found")
        );
    }

    @Test
    @Order(3)
    public void testUpdateDepartment() {
        System.out.println("Enter Department ID to update:");
        int departmentId = readIntInput();
        System.out.println("Enter new Department Name:");
        String departmentName = scanner.nextLine();
        System.out.println("Enter new Location:");
        String location = scanner.nextLine();

        Optional<Department> department = departmentService.getDepartment(departmentId);
        department.ifPresent(d -> {
            d.setDepartmentName(departmentName);
            d.setLocation(location);
            departmentService.updateDepartment(d);
        });
    }

    @Test
    @Order(4)
    public void testDeleteDepartment() {
        System.out.println("Enter Department ID to delete:");
        int departmentId = readIntInput();
        departmentService.deleteDepartment(departmentId);
    }

    private int readIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
