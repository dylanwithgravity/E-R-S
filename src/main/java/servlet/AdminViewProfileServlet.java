package servlet;

import dao.EmployeeDaoImpl;
import model.Employee;
import util.RequestHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminViewProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.sendRedirect("login");
        } else {
            EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
            String userName = (String) session.getAttribute("username");
            Employee employee = employeeDao.selectEmployeeByUsername(userName);
            //resp.sendRedirect(RequestHelper.getLoginHomepageRedirect(employee));
            req.getRequestDispatcher("html/adminViewProfile.html").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.sendRedirect("login");
        } else {
            String button = req.getParameter("homeButton");
            if (button != null && button.equals("Home")) {
                EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
                String userName = (String) session.getAttribute("username");
                Employee employee = employeeDao.selectEmployeeByUsername(userName);
                resp.sendRedirect(RequestHelper.getLoginHomepageRedirect(employee));
            }
        }
    }
}
