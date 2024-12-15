package com.office;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Employee_Project")
@IdClass(Employee_Project.Key.class) // Define composite primary key directly in the entity
public class Employee_Project {

    @Id
    @Column(name = "EmployeeID")
    private int employeeId;

    @Id
    @Column(name = "ProjectID")
    private int projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EmployeeID", insertable = false, updatable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProjectID", insertable = false, updatable = false)
    private Project project;

    @Column(name = "AssignedDate")
    private Date assignedDate;

    public Employee_Project() {}

    public Employee_Project(Employee employee, Project project, Date assignedDate) {
        this.employeeId = employee.getEmployeeId();
        this.projectId = project.getProjectId();
        this.employee = employee;
        this.project = project;
        this.assignedDate = assignedDate;
    }

    // Getters, Setters, equals, hashCode, toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee_Project that = (Employee_Project) o;
        return employeeId == that.employeeId && projectId == that.projectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, projectId);
    }

    @Override
    public String toString() {
        return "Employee_Project{" +
                "employeeId=" + employeeId +
                ", projectId=" + projectId +
                ", assignedDate=" + assignedDate +
                '}';
    }

    // Nested Key Class for the Composite Key
    public static class Key implements Serializable {
        private int employeeId;
        private int projectId;

        public Key() {}

        public Key(int employeeId, int projectId) {
            this.employeeId = employeeId;
            this.projectId = projectId;
        }

        // Getters, Setters, equals, hashCode
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return employeeId == key.employeeId && projectId == key.projectId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(employeeId, projectId);
        }
    }
}
