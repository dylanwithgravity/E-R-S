package dao;

import model.Employee;
import org.apache.log4j.Logger;
import util.DBUtil;
import util.LoggerSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoImpl implements EmployeeDao {
    private Connection con;


    public Employee selectEmployeeByUsername(String username) {
        con = DBUtil.getInstance();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM ERS_USERS WHERE ERS_USERNAME =?");
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
               return extractEmployeeFromResultSet(rs);
            }
        } catch (SQLException e) {
            LoggerSingleton.getLogger().error("Unable to fetch employee.." + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean updateEmployeeProfile(Employee employee) {
        boolean entryAdded = false;
        con = DBUtil.getInstance();

        try {
            String baseQuery = "UPDATE ERS_USERS SET ";
            if(employee.getFirstName() == null && employee.getLastName() == null ) {
                con.close();
                return false;
            } else if(employee.getFirstName() != null && employee.getLastName() != null) {
                baseQuery += "USER_FIRST_NAME ='" + employee.getFirstName() + "'," + "USER_LAST_NAME ='" + employee.getLastName() + "'";
            } else if (employee.getFirstName() != null) {
                baseQuery +="USER_FIRST_NAME ='" + employee.getFirstName() + "'";
            } else if (employee.getLastName() != null) {
                baseQuery += "USER_LAST_NAME ='" + employee.getLastName() + "'";
            }

            String endQuery = " WHERE ERS_USERNAME = ?";

            PreparedStatement stmt = con.prepareStatement(baseQuery + endQuery);
            stmt.setString(1, employee.getUserName());
            int rowAdded = stmt.executeUpdate();
            con.commit();

            if(rowAdded == 1) {
                entryAdded = true;
            }

        } catch (SQLException e) {
            LoggerSingleton.getLogger().error("Unable to update employee.." + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return entryAdded;
    }

    private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeID(rs.getInt(1));
        employee.setUserName(rs.getString(2));
        employee.setPassword(rs.getString(3));
        employee.setFirstName(rs.getString(4));
        employee.setLastName(rs.getString(5));
        employee.setEmail(rs.getString(6));
        employee.setRoleID(rs.getInt(7));

        return employee;
    }
}
