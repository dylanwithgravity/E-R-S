package servlet;

import com.google.gson.Gson;
import dao.EmployeeDaoImpl;
import model.Employee;
import util.LoggerSingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class GetProfileServlet extends HttpServlet {

    private Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.sendRedirect("login");
            return;
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

        String username = (String) session.getAttribute("username");
        Employee employee = employeeDao.selectEmployeeByUsername(username);
        String data = this.gson.toJson(employee);
        data = "{\"employee\": " + data + "}";
        out.print(data);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
