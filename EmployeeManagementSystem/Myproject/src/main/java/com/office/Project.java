package com.office;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Project")
public class Project {

    @Id
    @Column(name = "ProjectID")
    private int projectId;

    @Column(name = "ProjectName", nullable = false)
    private String projectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DepartmentID", foreignKey = @ForeignKey(name = "FK_DepartmentID"))
    private Department department;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "EndDate")
    private Date endDate;

    // Getters and Setters
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", department=" + (department != null ? department.getDepartmentName() : "N/A") +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
