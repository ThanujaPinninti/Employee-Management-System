package com.office;

import java.util.Date;

public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String aadhar;
    private String jobTitle;
    private double salary;
    private int departmentId;
    private Gender gender;
    private Date dob;
    private Date hireDate;

    // Default constructor for Hibernate
    public Employee() {}

    // Constructor with all parameters
    public Employee(int employeeId, String firstName, String lastName, String email, String phoneNumber,
                    String aadhar, String jobTitle, double salary, int departmentId, Gender gender,
                    Date dob, Date hireDate) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.aadhar = aadhar;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.departmentId = departmentId;
        this.gender = gender;
        this.dob = dob;
        this.hireDate = hireDate;
    }

    // Getters and setters for all fields
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public String toString() {
        return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName +
                ", email=" + email + ", phoneNumber=" + phoneNumber + ", aadhar=" + aadhar + ", jobTitle=" + jobTitle +
                ", salary=" + salary + ", departmentId=" + departmentId + ", gender=" + gender + ", dob=" + dob +
                ", hireDate=" + hireDate + "]";
    }
}
