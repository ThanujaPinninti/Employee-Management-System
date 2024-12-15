package com.office.service;

import com.office.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Optional;

public class DepartmentService {

    private static SessionFactory sessionFactory;

    // Static block for SessionFactory initialization
    static {
        try {
            sessionFactory = new Configuration().configure().addAnnotatedClass(Department.class).buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add a new department
    public void addDepartment(Department department) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
            System.out.println("Department added successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get department by ID
    public Optional<Department> getDepartment(int departmentId) {
        try (Session session = sessionFactory.openSession()) {
            Department department = session.get(Department.class, departmentId);
            return Optional.ofNullable(department);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Update department
    public void updateDepartment(Department department) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(department);
            transaction.commit();
            System.out.println("Department updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete department by ID
    public void deleteDepartment(int departmentId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Department department = session.get(Department.class, departmentId);
            if (department != null) {
                session.delete(department);
                System.out.println("Department deleted successfully");
            } else {
                System.out.println("Department with ID " + departmentId + " not found");
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get all departments
    public List<Department> getAllDepartments() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Department", Department.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of(); // Return empty list if exception occurs
    }

    // Search departments by ID
    public List<Department> searchDepartmentsById(int departmentId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Department WHERE departmentId = :departmentId";
            return session.createQuery(hql, Department.class)
                    .setParameter("departmentId", departmentId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
