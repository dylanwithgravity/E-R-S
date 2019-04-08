package servlet;

import dao.EmployeeDaoImpl;
import model.Employee;
import util.LoggerSingleton;
import util.RequestHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("html/login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // get the username and password and compare with db records
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        EmployeeDaoImpl edao = new EmployeeDaoImpl();
        Employee employee = edao.selectEmployeeByUsername(username);
        System.out.println(employee);

        // check if record exists and password don't matches
        if(employee == null || !password.equals(employee.getPassword())) {
            System.out.println("Here 2");
            resp.sendRedirect("login");
            System.out.println("here 3");
            return;
        }

        // set up session if user is valid
        HttpSession session = req.getSession();
        session.setAttribute("username", username);
        System.out.println("Created session!");

        // redirect to homepage (admin || standard)
        resp.sendRedirect(RequestHelper.getLoginHomepageRedirect(employee));
    }
}
