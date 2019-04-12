package servlet;

import dao.EmployeeDaoImpl;
import model.Employee;
import util.LoggerSingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("html/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.sendRedirect("login");
            return;
        }
        LoggerSingleton.getLogger().info(session.getAttribute("username") + " updating profile data.");

        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

        String username = (String) session.getAttribute("username");

        EmployeeDaoImpl edao = new EmployeeDaoImpl();
        Employee employee = edao.selectEmployeeByUsername(username);
        employee.setFirstName(req.getParameter("firstname"));
        employee.setLastName(req.getParameter("lastname"));

        boolean updated = employeeDao.updateEmployeeProfile(employee);

        if(employee.getRoleID() == 2) {
            resp.sendRedirect("emp_view_profile");

        } else {
            resp.sendRedirect("admin_view_profile");
        }

    }

}
