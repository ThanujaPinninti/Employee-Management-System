package com.office.service;

import com.office.Project;
import com.office.Department;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Transactional
public class ProjectService {

    @PersistenceContext
    private EntityManager entityManager;

    // 1. Add (Create) Project
    public Project addProject(Project project) {
        entityManager.persist(project);
        return project;
    }

    // 2. Retrieve (Get) Project by ID using Optional
    public Optional<Project> getProjectById(int projectID) {
        return Optional.ofNullable(entityManager.find(Project.class, projectID));
    }

    // 3. Retrieve (Get) All Projects
    public List<Project> getAllProjects() {
        return entityManager.createQuery("SELECT p FROM Project p", Project.class).getResultList();
    }

    // 4. Update Project by ID
    public Project updateProject(int projectID, Project updatedProject) {
        // Retrieve the existing project from the database
        Project existingProject = entityManager.find(Project.class, projectID);
        if (existingProject != null) {
            // Update the existing project with new values
            existingProject.setProjectName(updatedProject.getProjectName());
            existingProject.setDepartment(updatedProject.getDepartment());
            existingProject.setStartDate(updatedProject.getStartDate());
            existingProject.setEndDate(updatedProject.getEndDate());

            // Persist the updated project
            return entityManager.merge(existingProject);
        }
        return null; // Return null if the project doesn't exist
    }

    // 5. Delete Project by ID
    public boolean deleteProject(int projectID) {
        Project project = entityManager.find(Project.class, projectID);
        if (project != null) {
            entityManager.remove(project);
            return true;
        }
        return false; // Return false if the project is not found
    }

    // 6. Search Projects by Department
    public List<Project> searchProjectsByDepartment(int departmentId, Department department) {
        return entityManager.createQuery(
                        "SELECT p FROM Project p WHERE p.department = :department", Project.class)
                .setParameter("department", department)
                .getResultList();
    }

    // 7. Search Projects by Date Range
    public List<Project> searchProjectsByDateRange(Date startDate, Date endDate) {
        return entityManager.createQuery(
                        "SELECT p FROM Project p WHERE p.startDate >= :startDate AND p.endDate <= :endDate", Project.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    // 8. Retrieve Project by ID using a simple method name (alias for getProjectById)
    public Optional<Project> getProject(int projectID) {
        return getProjectById(projectID);
    }
}
