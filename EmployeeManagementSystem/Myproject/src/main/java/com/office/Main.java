package com.office;

import com.office.service.DepartmentService;
import com.office.service.EmployeeService;
import com.office.service.ProjectService;
import com.office.service.EmployeeProjectService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.sql.Date;

public class Main {

    private static DepartmentService departmentService;
    private static EmployeeService employeeService;
    private static ProjectService projectService;
    private static EmployeeProjectService employeeProjectService;
    private static Scanner scanner;
    private static SessionFactory sessionFactory;
    private static Session session;
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    public static void main(String[] args) {
        // Initialize services and scanner
        departmentService = new DepartmentService();
        employeeService = new EmployeeService();
        projectService = new ProjectService();
        employeeProjectService = new EmployeeProjectService();
        scanner = new Scanner(System.in);

        // Initialize Hibernate SessionFactory and Session
        sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Department.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Project.class)
                .addAnnotatedClass(Employee_Project.class)
                .buildSessionFactory();

        session = sessionFactory.getCurrentSession();

        // Initialize JPA EntityManager
        // entityManagerFactory = Persistence.createEntityManagerFactory("officePU");
        //  entityManager = entityManagerFactory.createEntityManager();

        while (true) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Department Operations");
            System.out.println("2. Employee Operations");
            System.out.println("3. Project Operations");
            System.out.println("4. Employee-Project Assignments");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    departmentOperations();
                    break;
                case 2:
                    employeeOperations();
                    break;
                case 3:
                    projectOperations();
                    break;
                case 4:
                    employeeProjectOperations();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    entityManager.close();
                    entityManagerFactory.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void departmentOperations() {
        while (true) {
            System.out.println("\nDepartment Operations:");
            System.out.println("1. Add Department");
            System.out.println("2. Get Department");
            System.out.println("3. Update Department");
            System.out.println("4. Search Departments");
            System.out.println("5. Delete Department");
            System.out.println("6. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addDepartment();
                    break;
                case 2:
                    getDepartment();
                    break;
                case 3:
                    updateDepartment();
                    break;
                case 4:
                    searchDepartmentsById();
                    break;
                case 5:
                    deleteDepartment();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    // Department Operations Methods

    public static void addDepartment() {
        System.out.println("Enter Department ID:");
        int departmentId = readIntInput();  // Using readIntInput for better validation

        scanner.nextLine();  // Consume newline
        System.out.println("Enter Department Name:");
        String departmentName = scanner.nextLine();

        System.out.println("Enter Location:");
        String location = scanner.nextLine();

        // In your code
        Department department = new Department();
        department.setDepartmentId(departmentId);  // Correct usage
        department.setDepartmentName(departmentName);
        department.setLocation(location);
        ;

        departmentService.addDepartment(department);
        System.out.println("Department added successfully.");
    }

    public static void getDepartment() {
        System.out.println("Enter Department ID to retrieve:");
        int departmentId = readIntInput();

        Optional<Department> departmentOpt = departmentService.getDepartment(departmentId);

        if (departmentOpt.isPresent()) {
            Department department = departmentOpt.get();
            System.out.println("Department Found: " + department.getDepartmentName());
        } else {
            System.out.println("Department not found.");
        }
    }

    public static void updateDepartment() {
        System.out.println("Enter Department ID to update:");
        int departmentId = readIntInput();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter new Department Name:");
        String departmentName = scanner.nextLine();

        System.out.println("Enter new Location:");
        String location = scanner.nextLine();

        // In your code
        Department department = new Department();
        department.setDepartmentId(departmentId);  // Correct usage
        department.setDepartmentName(departmentName);
        department.setLocation(location);


        departmentService.updateDepartment(department);
        System.out.println("Department updated successfully.");
    }

    public static void searchDepartmentsById() {
        System.out.println("Enter Department ID to search:");
        int departmentId = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over

        List<Department> departments = departmentService.searchDepartmentsById(departmentId);
        if (departments.isEmpty()) {
            System.out.println("No matching department found for the given ID.");
        } else {
            System.out.println("Department Found:");
            departments.forEach(department ->
                    System.out.println(department.getDepartmentName() + " (" + department.getLocation() + ")"));
        }
    }

    public static void deleteDepartment() {
        System.out.println("Enter Department ID to delete:");
        int departmentId = readIntInput();
        scanner.nextLine(); // Consume newline

        try (Session session = sessionFactory.openSession()) { // Open a session and use try-with-resources
            session.beginTransaction();

            // Fetch the Department by ID (no cascade delete)
            Department department = session.get(Department.class, departmentId);

            if (department != null) {
                // Delete the Department
                session.delete(department);
                System.out.println("Department deleted successfully.");
            } else {
                System.out.println("Department not found.");
            }

            session.getTransaction().commit(); // Commit transaction
        } catch (Exception e) {
            System.err.println("Error deleting department: " + e.getMessage());
        }
    }

    // Helper method to safely read integer input
    private static int readIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer value.");
            scanner.next(); // Discard invalid input
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        return value;
    }

    public static void employeeOperations() {
        while (true) {
            System.out.println("\nEmployee Operations:");
            System.out.println("1. Add Employee");
            System.out.println("2. Get Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Search Employees");
            System.out.println("5. Delete Employee");
            System.out.println("6. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    getEmployee();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    searchEmployees();
                    break;
                case 5:
                    deleteEmployee();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Add Employee method
    public static void addEmployee() {
        System.out.println("Enter Employee ID:");
        int employeeId = readIntInput();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter First Name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter Last Name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter Date of Birth (YYYY-MM-DD):");
        String dobStr = scanner.nextLine();
        System.out.println("Enter Gender (MALE, FEMALE, OTHER):");
        String genderStr = scanner.nextLine();
        Gender gender = Gender.valueOf(genderStr.toUpperCase());
        System.out.println("Enter Hire Date (YYYY-MM-DD):");
        String hireDateStr = scanner.nextLine();
        System.out.println("Enter Job Title:");
        String jobTitle = scanner.nextLine();
        System.out.println("Enter Salary:");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter Aadhar Number:");
        String aadharNumber = scanner.nextLine();
        System.out.println("Enter Phone Number:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter Email:");
        String email = scanner.nextLine();
        System.out.println("Enter Department ID:");
        int departmentId = readIntInput();

        // Fetch the department
        Optional<Department> optionalDepartment = departmentService.getDepartment(departmentId);
        if (optionalDepartment.isEmpty()) {
            System.out.println("Department with ID " + departmentId + " not found. Please create the department first.");
            return;
        }

        Department department = optionalDepartment.get();

        // Create and set Employee object
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setDob(Date.valueOf(dobStr));
        employee.setGender(gender);
        employee.setHireDate(Date.valueOf(hireDateStr));
        employee.setJobTitle(jobTitle);
        employee.setSalary(salary);
        employee.setAadhar(aadharNumber);
        employee.setPhoneNumber(phoneNumber);
        employee.setEmail(email);
        employee.setDepartmentId(departmentId); // Set the Department ID (or use setDepartment() if using Department object)

        // Add employee
        employeeService.addEmployee(employee);
        System.out.println("Employee added successfully.");
    }

    // Get Employee method
    public static void getEmployee() {
        System.out.println("Enter Employee ID to retrieve:");
        int employeeId = readIntInput();

        Optional<Employee> employeeOptional = employeeService.getEmployeeById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            System.out.println("Employee Found: " + employee.getFirstName() + " " + employee.getLastName());
        } else {
            System.out.println("Employee not found.");
        }
    }

    // Update Employee method
    public static void updateEmployee() {
        System.out.println("Enter Employee ID to update:");
        int employeeId = readIntInput();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter new First Name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter new Last Name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter new Gender (MALE, FEMALE, OTHER):");
        String genderStr = scanner.nextLine();
        Gender gender = Gender.valueOf(genderStr.toUpperCase());
        System.out.println("Enter new Job Title:");
        String jobTitle = scanner.nextLine();
        System.out.println("Enter new Salary:");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter new Aadhar Number:");
        String aadharNumber = scanner.nextLine();
        System.out.println("Enter new Phone Number:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter new Email:");
        String email = scanner.nextLine();

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setGender(gender);
        employee.setJobTitle(jobTitle);
        employee.setSalary(salary);
        employee.setAadhar(aadharNumber);
        employee.setPhoneNumber(phoneNumber);
        employee.setEmail(email);

        employeeService.updateEmployeeById(employeeId, employee);
        System.out.println("Employee updated successfully.");
    }

    // Search Employees method
    public static void searchEmployees() {
        System.out.println("Enter Employee ID to search:");
        int employeeId = readIntInput();

        Optional<Employee> employeeOptional = employeeService.getEmployeeById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            System.out.println("Employee Found:");
            System.out.println("ID: " + employee.getEmployeeId());
            System.out.println("First Name: " + employee.getFirstName());
            System.out.println("Last Name: " + employee.getLastName());
            System.out.println("Gender: " + employee.getGender());
            System.out.println("Job Title: " + employee.getJobTitle());
            System.out.println("Salary: " + employee.getSalary());
            System.out.println("Aadhar: " + employee.getAadhar());
            System.out.println("Phone Number: " + employee.getPhoneNumber());
            System.out.println("Email: " + employee.getEmail());

            if ( employee.getDepartmentId() != 0) {
                System.out.println("Department ID: " + employee.getDepartmentId());
            } else {
                System.out.println("Department: Not Assigned");
            }
        } else {
            System.out.println("No employee found with ID: " + employeeId);
        }
    }

    // Delete Employee method
    public static void deleteEmployee() {
        System.out.println("Enter Employee ID to delete:");
        int employeeId = readIntInput();

        employeeService.deleteEmployeeById(employeeId);

        Optional<Employee> deletedEmployeeOptional = employeeService.getEmployeeById(employeeId);
        if (!deletedEmployeeOptional.isPresent()) {
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee deletion failed.");
        }
    }

    public static void projectOperations() {
        while (true) {
            System.out.println("\nProject Operations:");
            System.out.println("1. Add Project");
            System.out.println("2. Get Project");
            System.out.println("3. Update Project");
            System.out.println("4. Search Projects");
            System.out.println("5. Delete Project");
            System.out.println("6. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addProject();
                    break;
                case 2:
                    getProject();
                    break;
                case 3:
                    updateProject();
                    break;
                case 4:
                    searchProjects();
                    break;
                case 5:
                    deleteProject();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }


// Project Operations Methods

    public static void addProject() {
        System.out.println("Enter Project ID:");
        int projectId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter Project Name:");
        String projectName = scanner.nextLine();
        System.out.println("Enter Department ID:");
        int departmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Fetch the Department object based on the department ID
        Department department = entityManager.find(Department.class, departmentId);
        if (department == null) {
            System.out.println("Department not found with ID: " + departmentId);
            return;
        }

        Project project = new Project();
        project.setProjectID(projectId);
        project.setProjectName(projectName);
        project.setDepartment(department);  // Set the Department object based on ID (not name)

        projectService.addProject(project);
        System.out.println("Project added successfully.");
    }


    public static void getProject() {
        System.out.println("Enter Project ID to retrieve:");
        int projectId = scanner.nextInt();

        Optional<Project> project = projectService.getProject(projectId);
        if (project.isPresent()) {
            // Corrected method name to getDepartmentId()
            System.out.println("Project Found: " + project.get().getProjectName() +
                    " (Department ID: " + project.get().getDepartment().getDepartmentId() + ")");
        } else {
            System.out.println("Project not found.");
        }
    }

    public static void updateProject() {
        System.out.println("Enter Project ID to update:");
        int projectId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter new Project Name:");
        String projectName = scanner.nextLine();
        System.out.println("Enter new Department ID:");
        int departmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Fetch the Department object based on the department ID
        Department department = entityManager.find(Department.class, departmentId);
        if (department == null) {
            System.out.println("Department not found with ID: " + departmentId);
            return;
        }
        // Retrieve the existing project using projectId
        // Retrieve the existing project using projectId
        Optional<Project> existingProject = projectService.getProject(projectId);
        if (existingProject.isPresent()) {
            Project project = existingProject.get();
            project.setProjectName(projectName);
            project.setDepartment(department);  // Set the Department object (based on departmentId)

            // Call updateProject with both projectId and updated project object
            projectService.updateProject(projectId, project);
            System.out.println("Project updated successfully.");
        } else {
            System.out.println("Project not found with ID: " + projectId);
        }
    }

    public static void searchProjects() {
        System.out.println("Enter Project ID to search:");
        int projectId = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline

        // Call the existing method for searching by project ID
        Optional<Project> projectOpt = projectService.getProject(projectId);

        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            System.out.println("Project Found: " + project.getProjectName() +
                    " (Department ID: " + project.getDepartment().getDepartmentId() + ")");
        } else {
            System.out.println("No project found with ID: " + projectId);
        }
    }


    public static void deleteProject() {
        System.out.println("Enter Project ID to delete:");
        int projectId = scanner.nextInt();

        // Delete the project using the projectId
        projectService.deleteProject(projectId);

        Optional<Project> deletedProject = projectService.getProject(projectId);
        if (deletedProject.isEmpty()) {
            System.out.println("Project deleted successfully.");
        } else {
            System.out.println("Project deletion failed.");
        }
    }
// Employee-Project Operations Methods

    public static void employeeProjectOperations() {
        while (true) {
            System.out.println("\nEmployee-Project Assignment Operations:");
            System.out.println("1. Add Employee-Project Assignment");
            System.out.println("2. Get Employee-Project Assignment");
            System.out.println("3. Update Employee-Project Assignment");
            System.out.println("4. Search Employee-Project Assignment");
            System.out.println("5. Delete Employee-Project Assignment");
            System.out.println("6. Back to Main Menu");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addEmployeeProject();
                    break;
                case 2:
                    getEmployeeProject();
                    break;
                case 3:
                    updateEmployeeProject();
                    break;
                case 4:
                    searchEmployeeProject();
                    break;
                case 5:
                    deleteEmployeeProject();
                    break;
                case 6:
                    return; // Go back to the main menu
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // 1. Add Employee-Project Assignment
    public static void addEmployeeProject() {
        System.out.println("Enter Employee ID:");
        int employeeId = scanner.nextInt();
        System.out.println("Enter Project ID:");
        int projectId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter Assignment Date (YYYY-MM-DD):");
        String dateStr = scanner.nextLine();
        Date assignedDate = java.sql.Date.valueOf(dateStr);

        // Retrieve Employee and Project from database (via service)
        Employee employee = employeeProjectService.getEmployee(employeeId);
        Project project = employeeProjectService.getProject(projectId);

        if (employee != null && project != null) {
            Employee_Project employeeProject = new Employee_Project(employee, project, assignedDate);
            employeeProjectService.addEmployee_Project(employeeProject);
            System.out.println("Employee-Project assignment added successfully.");
        } else {
            System.out.println("Employee or Project not found.");
        }
    }

    // 2. Get Employee-Project Assignment
    public static void getEmployeeProject() {
        System.out.println("Enter Employee ID:");
        int employeeId = scanner.nextInt();
        System.out.println("Enter Project ID:");
        int projectId = scanner.nextInt();

        Employee_Project employeeProject = employeeProjectService.getEmployeeProject(employeeId, projectId);
        if (employeeProject != null) {
            System.out.println("Employee " + employeeProject.getEmployee().getFirstName() + " " +
                    employeeProject.getEmployee().getLastName() + " is assigned to project " +
                    employeeProject.getProject().getProjectName() + " (Assigned on: " +
                    employeeProject.getAssignedDate() + ")");
        } else {
            System.out.println("No such assignment found.");
        }
    }

    // 3. Update Employee-Project Assignment
    public static void updateEmployeeProject() {
        System.out.println("Enter Employee ID:");
        int employeeId = scanner.nextInt();
        System.out.println("Enter Project ID:");
        int projectId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter new Assignment Date (YYYY-MM-DD):");
        String dateStr = scanner.nextLine();
        Date assignedDate = java.sql.Date.valueOf(dateStr);

        Employee_Project employeeProject = employeeProjectService.getEmployeeProject(employeeId, projectId);
        if (employeeProject != null) {
            employeeProject.setAssignedDate(assignedDate);
            employeeProjectService.updateEmployee_Project(employeeProject);
            System.out.println("Employee-Project assignment updated successfully.");
        } else {
            System.out.println("Assignment not found.");
        }
    }

    // 4. Search Employee-Project Assignment
    public static void searchEmployeeProject() {
        System.out.println("Enter Employee ID or Project ID to search (Enter 0 to skip):");

        // Read employee ID and project ID from the user
        int employeeId = scanner.nextInt();
        int projectId = scanner.nextInt();

        // Convert the primitive int to Integer to handle the null cases properly
        Integer empId = (employeeId == 0) ? null : employeeId;
        Integer projId = (projectId == 0) ? null : projectId;

        // Search for Employee-Project assignments based on the provided IDs
        List<Employee_Project> assignments = employeeProjectService.searchEmployeeProject(empId, projId);

        if (assignments.isEmpty()) {
            System.out.println("No assignments found for the provided ID.");
        } else {
            System.out.println("Search results:");
            for (Employee_Project assignment : assignments) {
                System.out.println("Employee " + assignment.getEmployee().getFirstName() + " " +
                        assignment.getEmployee().getLastName() + " is assigned to project " +
                        assignment.getProject().getProjectName() + " (Assigned on: " +
                        assignment.getAssignedDate() + ")");
            }
        }
    }

    // 5. Delete Employee-Project Assignment
    public static void deleteEmployeeProject() {
        System.out.println("Enter Employee ID:");
        int employeeId = scanner.nextInt();
        System.out.println("Enter Project ID:");
        int projectId = scanner.nextInt();

        boolean result = employeeProjectService.deleteEmployeeProject(employeeId, projectId);
        if (result) {
            System.out.println("Employee-Project assignment deleted successfully.");
        } else {
            System.out.println("Assignment not found.");
        }
    }


}