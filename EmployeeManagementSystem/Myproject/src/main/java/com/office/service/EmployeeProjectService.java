package com.office.service;

import com.office.Employee;
import com.office.Project;
import com.office.Employee_Project;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public class EmployeeProjectService {

    @PersistenceContext
    private EntityManager entityManager;

    // Get employee by ID
    public Employee getEmployee(int employeeId) {
        return entityManager.find(Employee.class, employeeId);
    }

    // Get project by ID
    public Project getProject(int projectId) {
        return entityManager.find(Project.class, projectId);
    }

    // Assign project to employee
    public Employee_Project assignProjectToEmployee(Employee employee, Project project, Date assignedDate) {
        if (employee == null || project == null || assignedDate == null) {
            throw new IllegalArgumentException("Employee, Project, and Assigned Date must not be null.");
        }

        Employee_Project employeeProject = new Employee_Project(employee, project, assignedDate);
        entityManager.persist(employeeProject);
        return employeeProject;
    }

    // Add Employee_Project record
    public Employee_Project addEmployee_Project(Employee_Project employeeProject) {
        if (employeeProject == null) {
            throw new IllegalArgumentException("Employee_Project must not be null.");
        }
        entityManager.persist(employeeProject);
        return employeeProject;
    }

    // Update Employee_Project record
    public Employee_Project updateEmployee_Project(Employee_Project employeeProject) {
        if (employeeProject == null) {
            throw new IllegalArgumentException("Employee_Project must not be null.");
        }
        return entityManager.merge(employeeProject);
    }

    // Search Employee_Project records by employee and/or project IDs
    public List<Employee_Project> searchEmployeeProject(Integer employeeId, Integer projectId) {
        // Create the base query
        StringBuilder query = new StringBuilder("SELECT ep FROM Employee_Project ep WHERE 1=1");

        // Add conditions based on the input parameters
        if (employeeId != null) {
            query.append(" AND ep.employee.employeeID = :employeeId");
        }

        if (projectId != null) {
            query.append(" AND ep.project.projectID = :projectId");
        }

        // Create the query object
        var queryObj = entityManager.createQuery(query.toString(), Employee_Project.class);

        // Set the parameters if provided
        if (employeeId != null) {
            queryObj.setParameter("employeeId", employeeId);
        }

        if (projectId != null) {
            queryObj.setParameter("projectId", projectId);
        }

        // Execute the query and return the result list
        return queryObj.getResultList();
    }

    // Get all assignments for a specific employee
    public List<Employee_Project> getAssignmentsByEmployee(int employeeID) {
        return entityManager.createQuery(
                        "SELECT ep FROM Employee_Project ep WHERE ep.employee.employeeID = :employeeID", Employee_Project.class)
                .setParameter("employeeID", employeeID)
                .getResultList();
    }

    // Get all assignments for a specific project
    public List<Employee_Project> getAssignmentsByProject(int projectID) {
        return entityManager.createQuery(
                        "SELECT ep FROM Employee_Project ep WHERE ep.project.projectID = :projectID", Employee_Project.class)
                .setParameter("projectID", projectID)
                .getResultList();
    }

    // Unassign project from employee
    public boolean unassignProjectFromEmployee(Employee employee, Project project) {
        Optional<Employee_Project> employeeProject = entityManager.createQuery(
                        "SELECT ep FROM Employee_Project ep WHERE ep.employee = :employee AND ep.project = :project", Employee_Project.class)
                .setParameter("employee", employee)
                .setParameter("project", project)
                .getResultStream()
                .findFirst();

        if (employeeProject.isPresent()) {
            entityManager.remove(employeeProject.get());
            return true;
        }
        return false;
    }

    // Get specific Employee_Project record by employee and project IDs
    public Employee_Project getEmployeeProject(int employeeId, int projectId) {
        return entityManager.createQuery(
                        "SELECT ep FROM Employee_Project ep WHERE ep.employee.employeeID = :employeeId AND ep.project.projectID = :projectId", Employee_Project.class)
                .setParameter("employeeId", employeeId)
                .setParameter("projectId", projectId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    // Get all Employee_Project assignments
    public List<Employee_Project> getAllAssignments() {
        return entityManager.createQuery("SELECT ep FROM Employee_Project ep", Employee_Project.class)
                .getResultList();
    }

    // Delete Employee_Project record by employee and project IDs
    public boolean deleteEmployeeProject(int employeeId, int projectId) {
        Employee_Project employeeProject = getEmployeeProject(employeeId, projectId);
        if (employeeProject != null) {
            entityManager.remove(employeeProject);
            return true;
        }
        return false;
    }
}
