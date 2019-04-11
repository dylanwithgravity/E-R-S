package servlet;

import com.google.gson.Gson;
import dao.EmployeeDaoImpl;
import dao.ReimbursementDaoImpl;
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

public class GetPendingServlet extends HttpServlet {

    private Gson gson = new Gson();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session == null) {
            resp.sendRedirect("login");
            return;
        }
        LoggerSingleton.getLogger().info(session.getAttribute("username") + " requesting pending requests.");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        ReimbursementDaoImpl reimbursementDao = new ReimbursementDaoImpl();
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();

        String username = (String) session.getAttribute("username");
        int employeeID = employeeDao.selectEmployeeByUsername(username).getEmployeeID();
        String queryPending =
                "SELECT * FROM ERS_REIMBURSEMENT WHERE REIMB_STATUS_ID = 1 AND REIMB_AUTHOR = " + employeeID + "";
        List<Reimbursement> list = reimbursementDao.selectReimbursements(queryPending);

        for (Reimbursement rb: list
             ) {
            try {
                Date date = dateFormat.parse(rb.getSubmitDate());
                String dtString = dateFormat.format(date);
                rb.setSubmitDate(dtString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //if(rb.getAmount() )

        }


        String data = this.gson.toJson(list);
        data = "{\"pending\": " + data + "}";
        out.print(data);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
