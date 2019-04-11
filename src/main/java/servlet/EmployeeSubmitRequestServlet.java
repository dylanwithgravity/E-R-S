package servlet;

import dao.EmployeeDaoImpl;
import dao.ReimbursementDaoImpl;
import model.Employee;
import model.Reimbursement;
import util.LoggerSingleton;
import util.ReimbursementTypeHelper;
import util.RequestHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EmployeeSubmitRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session == null) {
            resp.sendRedirect("login");
        } else {
            req.getRequestDispatcher("html/employeeSubmitRequest.html").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if(session == null) {
            resp.sendRedirect("login");
        } else {

            // Get values from form
            double amount = Double.parseDouble(req.getParameter("amount"));
            String reimbursementType = req.getParameter("type");
            String description = req.getParameter("description");
            //HACK: - blob needed if able to implement

            EmployeeDaoImpl employeeDao = new EmployeeDaoImpl();
            Employee employee = employeeDao.selectEmployeeByUsername((String) session.getAttribute("username"));

            ReimbursementDaoImpl reimbursementDao = new ReimbursementDaoImpl();
            Reimbursement reimbursement = new Reimbursement();
            reimbursement.setAmount(amount);
            reimbursement.setDescription(description);
            reimbursement.setReceipt(null);
            reimbursement.setEmployeeID(employee.getEmployeeID());
            reimbursement.setTypeID(ReimbursementTypeHelper.getReimbursementTypeID(reimbursementType));

            if(reimbursementDao.insertReimbursementRequest(reimbursement)) {
                LoggerSingleton.getLogger().info("Reimbursement request submitted for: " + employee.getEmployeeID());
                //HACK: - This is temporary for debugging
                resp.sendRedirect(RequestHelper.getLoginHomepageRedirect(employee));
            }

        }

    }
}
