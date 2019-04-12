package servlet;

import util.LoggerSingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.sendRedirect("login");
        } else {
            req.getRequestDispatcher("html/admin_home.html").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null) {
            LoggerSingleton.getLogger().info("Invalid session redirecting to login");
            resp.sendRedirect("login");
            return;
        }

        String logoutButton = req.getParameter("logout");
        if(logoutButton != null && logoutButton.equals("Log Out")) {
            LoggerSingleton.getLogger().info(session.getAttribute("username"));
            resp.sendRedirect("logout");
        }


    }
}
