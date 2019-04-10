package dao;

import model.Reimbursement;
import util.DBUtil;
import util.LoggerSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReimbursementDaoImpl implements ReimbursementDao {
    private Connection con;
    @Override
    public boolean insertReimbursementRequest(Reimbursement reimbursement) {
        con = DBUtil.getInstance();
        boolean entryAdded = false;
        int rowAdded = 0;
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO ERS_REIMBURSEMENT (REIMB_AMOUNT, " +
                    "REIMB_DESCRIPTION, REIMB_RECEIPT, REIMB_AUTHOR, REIMB_TYPE_ID) VALUES (?, ?, ?, " +
                    "?, ?)");
            stmt.setDouble(1, reimbursement.getAmount());
            stmt.setString(2, reimbursement.getDescription());
            stmt.setBytes(3, reimbursement.getReceipt());
            stmt.setInt(4, reimbursement.getEmployeeID());
            stmt.setInt(5, reimbursement.getTypeID());

            rowAdded = stmt.executeUpdate();
            con.commit();

            if(rowAdded == 1) {
                entryAdded = true;
            }


        } catch (SQLException e) {
            LoggerSingleton.getLogger().error("Error inserting request..");
            e.printStackTrace();
        }
        return entryAdded;
    }
}
