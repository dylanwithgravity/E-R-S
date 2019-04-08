package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EmployeeHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the session from the req, set to false so not auto created if it doesn't exist
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.sendRedirect("login");
        } else {
            req.getRequestDispatcher("html/employee_home.html").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.sendRedirect("login");
            return;
        }

        String logoutButton = req.getParameter("logout");
        if(logoutButton != null && logoutButton.equals("Log Out")) {
            resp.sendRedirect("logout");
        }

    }
}
