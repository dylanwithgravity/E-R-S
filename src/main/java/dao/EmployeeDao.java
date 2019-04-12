package dao;

import model.Employee;

public interface EmployeeDao {
    Employee selectEmployeeByUsername(String username);
    boolean updateEmployeeProfile(Employee employee);
}
