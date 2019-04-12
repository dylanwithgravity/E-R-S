package servlet;

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

public class UpdateReimbursementRequestServlet extends HttpServlet {
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
        LoggerSingleton.getLogger().info(session.getAttribute("username") + " updating reimbursement data.");

        ReimbursementDaoImpl reimbursementDao = new ReimbursementDaoImpl();
        EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();



        String username = (String) session.getAttribute("username");
        int adminID = employeeDao.selectEmployeeByUsername(username).getEmployeeID();

        //int ticketID = Integer.parseInt(req.getParameter("value"));
        String ticketIDS = (req.getParameter("approve"));
        System.out.println(ticketIDS);
        int ticketID = 0;
        int statusID = 0;
        if(ticketIDS != null) {
            statusID = 2;
            ticketID = Integer.parseInt(ticketIDS);
        } else {
            statusID = 3;
            ticketID = Integer.parseInt(req.getParameter("deny"));
        }



        Reimbursement reimbursement = new Reimbursement();

        reimbursement.setAdminID(adminID);
        reimbursement.setStatusID(statusID);
        reimbursement.setRequestID(ticketID);

        reimbursementDao.updateReimbursementRequest(reimbursement);


        resp.sendRedirect("admin_view_pend");
    }

}
