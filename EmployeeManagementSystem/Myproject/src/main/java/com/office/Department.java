package com.office;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Department") // Matches the SQL schema table name
public class Department {

    @Id
    @Column(name = "DepartmentID") // Matches the SQL schema column name
    private int departmentId;

    @Column(name = "DepartmentName", nullable = false) // Matches the SQL schema column name
    private String departmentName;

    @Column(name = "Location", nullable = false) // Fixed nullable alignment
    private String location;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Project> projects;

    // Constructors
    public Department() {}

    public Department(int departmentId, String departmentName, String location) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.location = location;
    }

    // Getters and Setters
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
