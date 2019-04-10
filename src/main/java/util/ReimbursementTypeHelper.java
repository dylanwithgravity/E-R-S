package util;

import model.Reimbursement;

public class ReimbursementTypeHelper {
    public static int getReimbursementTypeID(String reimbursementType) {
        switch (reimbursementType) {
            case "Lodging":
                return 1;
            case "Travel":
                return 2;
            case "Food":
                return 3;
            case "Other":
                return 4;
                default:
                    return 4;
        }

    }
}
