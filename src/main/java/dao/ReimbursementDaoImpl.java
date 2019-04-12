package dao;

import model.Reimbursement;
import util.DBUtil;
import util.LoggerSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDaoImpl implements ReimbursementDao {
    private Connection con;

    @Override
    public boolean updateReimbursementRequest(Reimbursement reimbursement) {
        boolean entryAdded = false;
        con = DBUtil.getInstance();

        try {

            PreparedStatement stmt = con.prepareStatement("UPDATE ERS_REIMBURSEMENT SET REIMB_RESOLVED = sysdate, " +
                    "REIMB_RESOLVER = ?, REIMB_STATUS_ID = ? WHERE REIMB_ID = ?");
            stmt.setInt(1, reimbursement.getAdminID());
            stmt.setInt(2, reimbursement.getStatusID());
            stmt.setInt(3, reimbursement.getRequestID());

            int rowAdded = stmt.executeUpdate();
            con.commit();

            if(rowAdded == 1) {
                entryAdded = true;
            }

        } catch (SQLException e) {
            LoggerSingleton.getLogger().error("Unable to update reimbursement.." + e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return entryAdded;
    }

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

    @Override
    public List<Reimbursement> selectReimbursements(String query) {
        con = DBUtil.getInstance();
        List<Reimbursement> list = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int reimbID = rs.getInt("REIMB_ID");
                double amount = rs.getDouble("REIMB_AMOUNT");
                String reimbSubmitted = rs.getString("REIMB_SUBMITTED");
                String reimbResolved = rs.getString("REIMB_RESOLVED");
                String reimbDescription = rs.getString("REIMB_DESCRIPTION");
                //HACK: - get receipt?
                int reimbAuthor = rs.getInt("REIMB_AUTHOR");
                int reimbResolver = rs.getInt("REIMB_RESOLVER");
                int reimbStatusID = rs.getInt("REIMB_STATUS_ID");
                int reimbTypeID = rs.getInt("REIMB_TYPE_ID");

                list.add(new Reimbursement(reimbID, amount, reimbSubmitted, reimbResolved, reimbDescription, null,
                        reimbAuthor, reimbResolver, reimbStatusID, reimbTypeID));
            }
        } catch (SQLException e) {
            LoggerSingleton.getLogger().fatal("Error selecting reimbursements..");
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
