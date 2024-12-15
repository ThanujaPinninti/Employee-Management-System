package com.office.test;

import com.office.Employee;
import com.office.Project;
import com.office.Employee_Project;
import com.office.service.EmployeeProjectService;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeProjectServiceTest {

    private static EmployeeProjectService employeeProjectService;
    private static Scanner scanner;
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void setup() {
        // Initialize JPA EntityManager for database connection
        entityManagerFactory = Persistence.createEntityManagerFactory("officePU");
        entityManager = entityManagerFactory.createEntityManager();
        employeeProjectService = new EmployeeProjectService();
        scanner = new Scanner(System.in); // Initialize Scanner for user input
    }

    @AfterAll
    public static void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    @Order(1)
    public void testAssignProjectToEmployee() {
        System.out.println("Enter Employee ID:");
        int employeeId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Project ID:");
        int projectId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Assigned Date (yyyy-mm-dd):");
        Date assignedDate = Date.valueOf(scanner.nextLine()); // Use java.sql.Date.valueOf

        // Fetch Employee and Project from the database using IDs
        Employee employee = entityManager.find(Employee.class, employeeId);
        Project project = entityManager.find(Project.class, projectId);

        if (employee == null || project == null) {
            System.out.println("Employee or Project not found with the provided IDs.");
            return;
        }

        Employee_Project employeeProject = employeeProjectService.assignProjectToEmployee(employee, project, assignedDate);

        Assertions.assertNotNull(employeeProject, "Employee-Project assignment should be successful.");
        Assertions.assertEquals(employeeId, employeeProject.getEmployee(), "Employee ID should match.");
        Assertions.assertEquals(projectId, employeeProject.getProject().getProjectID(), "Project ID should match.");
    }

    @Test
    @Order(3)
    public void testUpdateEmployeeProject() {
        System.out.println("Enter Employee ID:");
        int employeeId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Project ID:");
        int projectId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter New Assigned Date (yyyy-mm-dd):");
        Date assignedDate = Date.valueOf(scanner.nextLine()); // Use java.sql.Date.valueOf

        Employee_Project existingAssignment = employeeProjectService.getEmployeeProject(employeeId, projectId);

        if (existingAssignment == null) {
            System.out.println("No existing Employee-Project assignment found.");
            return;
        }

        existingAssignment.setAssignedDate(assignedDate);
        Employee_Project updatedAssignment = employeeProjectService.updateEmployee_Project(existingAssignment);

        Assertions.assertNotNull(updatedAssignment, "Employee-Project assignment should be updated.");
        Assertions.assertEquals(assignedDate, updatedAssignment.getAssignedDate(), "Assigned date should be updated.");
    }

    // Add further test cases as needed...
}
