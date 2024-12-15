package com.office.test;

import com.office.Project;
import com.office.Department;
import com.office.service.ProjectService;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjectServiceTest {

    private static ProjectService projectService;
    private static Scanner scanner;
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("officePU");
        entityManager = entityManagerFactory.createEntityManager();
        projectService = new ProjectService();
        scanner = new Scanner(System.in);
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
    public void testAddProject() {
        System.out.println("Enter Project ID:");
        int projectId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Project Name:");
        String projectName = scanner.nextLine();
        System.out.println("Enter Department ID:");
        int departmentId = scanner.nextInt();
        scanner.nextLine();

        Department department = entityManager.find(Department.class, departmentId);
        if (department == null) {
            System.out.println("Department not found with ID: " + departmentId);
            return;
        }

        System.out.println("Enter Start Date (yyyy-mm-dd):");
        Date startDate = Date.valueOf(scanner.nextLine());
        System.out.println("Enter End Date (yyyy-mm-dd):");
        Date endDate = Date.valueOf(scanner.nextLine());

        Project project = new Project();
        project.setProjectID(projectId);
        project.setProjectName(projectName);
        project.setDepartment(department);
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        projectService.addProject(project);
        Optional<Project> retrieved = projectService.getProject(projectId);
        Assertions.assertTrue(retrieved.isPresent(), "Project should be added successfully");
    }

    @Test
    @Order(2)
    public void testGetProjectById() {
        System.out.println("Enter Project ID to retrieve:");
        int projectId = scanner.nextInt();
        Optional<Project> project = projectService.getProjectById(projectId);

        if (project.isPresent()) {
            System.out.println("Project Retrieved: " + project.get());
            Assertions.assertEquals(projectId, project.get().getProjectID(), "The project ID should match.");
        } else {
            System.out.println("Project not found with ID: " + projectId);
            Assertions.fail("Project not found with ID: " + projectId);
        }
    }

    @Test
    @Order(3)
    public void testUpdateProject() {
        System.out.println("Enter Project ID to update:");
        int projectId = scanner.nextInt();
        scanner.nextLine();

        Optional<Project> existingProject = projectService.getProjectById(projectId);
        if (existingProject.isPresent()) {
            Project project = existingProject.get();
            System.out.println("Current Project Name: " + project.getProjectName());

            System.out.println("Enter new Project Name:");
            String newProjectName = scanner.nextLine();
            project.setProjectName(newProjectName);

            System.out.println("Enter new Start Date (yyyy-mm-dd):");
            Date newStartDate = Date.valueOf(scanner.nextLine());
            project.setStartDate(newStartDate);

            System.out.println("Enter new End Date (yyyy-mm-dd):");
            Date newEndDate = Date.valueOf(scanner.nextLine());
            project.setEndDate(newEndDate);

            projectService.updateProject(projectId, project);
            Optional<Project> updatedProject = projectService.getProjectById(projectId);
            Assertions.assertTrue(updatedProject.isPresent(), "Updated project should exist.");
            Assertions.assertEquals(newProjectName, updatedProject.get().getProjectName(), "Project name should be updated.");
        } else {
            System.out.println("Project not found with ID: " + projectId);
            Assertions.fail("Project not found with ID: " + projectId);
        }
    }

    @Test
    @Order(4)
    public void testDeleteProject() {
        System.out.println("Enter Project ID to delete:");
        int projectId = scanner.nextInt();
        boolean deleted = projectService.deleteProject(projectId);
        Assertions.assertTrue(deleted, "Project should be deleted successfully.");
    }

    @Test
    @Order(5)
    public void testSearchProjectsByDateRange() {
        System.out.println("Enter start date (yyyy-mm-dd):");
        Date startDate = Date.valueOf(scanner.nextLine());
        System.out.println("Enter end date (yyyy-mm-dd):");
        Date endDate = Date.valueOf(scanner.nextLine());

        List<Project> projects = projectService.searchProjectsByDateRange(startDate, endDate);
        Assertions.assertFalse(projects.isEmpty(), "There should be projects in this date range.");
        projects.forEach(System.out::println);
    }
}
