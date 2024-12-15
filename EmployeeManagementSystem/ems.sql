CREATE DATABASE ems;
USE ems;

-- Department Table
CREATE TABLE Department (
    DepartmentID INT AUTO_INCREMENT PRIMARY KEY,
    DepartmentName VARCHAR(55) NOT NULL,
    Location VARCHAR(55) NOT NULL
);

DROP TABLE IF EXISTS Employee;


-- Employee_Project Table (Many-to-Many relationship)
CREATE TABLE Employee_Project (
    EmployeeID INT,
    ProjectID INT,
    AssignedDate DATE,
    PRIMARY KEY (EmployeeID, ProjectID),
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID),
    FOREIGN KEY (ProjectID) REFERENCES Project(ProjectID)
);



SHOW TABLES;
SELECT * FROM Project;
SELECT * FROM Employee_Project;
SELECT * FROM Department;
SELECT * FROM Employee;


