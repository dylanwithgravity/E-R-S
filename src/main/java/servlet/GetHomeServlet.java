package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.EmployeeDaoImpl;
import dao.ReimbursementDaoImpl;
import model.Employee;
import model.Reimbursement;
import util.LoggerSingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetHomeServlet extends HttpServlet {
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
        ReimbursementDaoImpl reimbursementDao = new ReimbursementDaoImpl();

        String username = (String) session.getAttribute("username");
        LoggerSingleton.getLogger().info("Fetching home data for.." + username);

        Employee employee = employeeDao.selectEmployeeByUsername(username);
        String queryPending =
                "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_STATUS_ID = 1 AND REIMB_AUTHOR = " + employee.getEmployeeID() + "";
        List<Reimbursement> list = reimbursementDao.selectReimbursements(queryPending);
        int numberOfPending = list.size();
        String queryResolved =
                "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_STATUS_ID IN (2,3) AND REIMB_AUTHOR = " + employee.getEmployeeID() + "";
        list = reimbursementDao.selectReimbursements(queryResolved);
        int numberOfResolved = list.size();


        JsonObject json = new JsonObject();
        json.addProperty("username", username);
        json.addProperty("pending", numberOfPending);
        json.addProperty("resolved", numberOfResolved);

        String data = this.gson.toJson(json);
        data = "{\"data\": " + data + "}";

        out.print(data);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
