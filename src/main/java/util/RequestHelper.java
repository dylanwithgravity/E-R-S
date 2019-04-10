package util;

import model.Employee;

public class RequestHelper {
    public static String getLoginHomepageRedirect(Employee employee) {
        switch (employee.getRoleID()) {
            case 1:
                return "admin_home";
            case 2:
                return "employee_home";
                default:
                    return "login";

        }
    }
}
