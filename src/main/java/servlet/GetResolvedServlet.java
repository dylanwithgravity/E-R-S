package servlet;

import com.google.gson.Gson;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class GetResolvedServlet extends HttpServlet {
    private Gson gson = new Gson();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.sendRedirect("login");
            return;
        }
        LoggerSingleton.getLogger().info(session.getAttribute("username") + " requesting resolved requests.");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        ReimbursementDaoImpl reimbursementDao = new ReimbursementDaoImpl();
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

        String queryResolved = "";

        String username = (String) session.getAttribute("username");

        Employee employee = employeeDao.selectEmployeeByUsername(username);
        if (employee.getRoleID() == 2) {
            queryResolved =
                    "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_STATUS_ID IN (2,3) AND REIMB_AUTHOR = " + employee.getEmployeeID() + "";
        } else {
            queryResolved = "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_STATUS_ID IN (2,3)";
        }
        List<Reimbursement> list = reimbursementDao.selectReimbursements(queryResolved);

        for (Reimbursement rb: list
        ) {
            try {
                Date date = dateFormat.parse(rb.getSubmitDate());
                String dtString = dateFormat.format(date);
                rb.setSubmitDate(dtString);
                date = dateFormat.parse(rb.getResolvedDate());
                dtString = dateFormat.format(date);
                rb.setResolvedDate(dtString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


        String data = this.gson.toJson(list);
        data = "{\"resolved\": " + data + "}";
        out.print(data);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
