package com.office.service;

import com.office.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class EmployeeService {

    private static SessionFactory sessionFactory;

    // Static block for SessionFactory initialization
    static {
        try {
            sessionFactory = new Configuration().configure().addAnnotatedClass(Employee.class).buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add a new employee
    public void addEmployee(Employee employee) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            System.out.println("Employee added successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get employee by ID
    public Optional<Employee> getEmployeeById(int employeeId) {
        try (Session session = sessionFactory.openSession()) {
            Employee employee = session.get(Employee.class, employeeId);
            return Optional.ofNullable(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Update employee by ID
    public boolean updateEmployeeById(int employeeId, Employee updatedEmployee) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, employeeId);
            if (employee != null) {
                // Set the updated values
                employee.setFirstName(updatedEmployee.getFirstName());
                employee.setLastName(updatedEmployee.getLastName());
                employee.setJobTitle(updatedEmployee.getJobTitle());
                employee.setSalary(updatedEmployee.getSalary());
                session.update(employee);
                transaction.commit();
                System.out.println("Employee updated successfully");
                return true;
            } else {
                System.out.println("Employee not found");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Delete employee by ID
    public void deleteEmployeeById(int employeeId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, employeeId);
            if (employee != null) {
                session.delete(employee);
                System.out.println("Employee deleted successfully");
            } else {
                System.out.println("Employee with ID " + employeeId + " not found");
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Employee", Employee.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of(); // Return empty list if exception occurs
    }

    // Search employees by ID
    public List<Employee> searchEmployeesById(int employeeId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Employee WHERE employeeId = :employeeId";
            return session.createQuery(hql, Employee.class)
                    .setParameter("employeeId", employeeId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
