package dao;

import model.Reimbursement;

import java.util.List;

public interface ReimbursementDao {
    boolean insertReimbursementRequest(Reimbursement reimbursement);
    List<Reimbursement> selectReimbursements(String query);
    boolean updateReimbursementRequest(Reimbursement reimbursement);
}
