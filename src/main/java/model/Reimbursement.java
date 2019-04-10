package model;

import java.util.Arrays;

public class Reimbursement {
    private int requestID;
    private double amount;
    private String submitDate;
    private String resolvedDate;
    private String description;
    private byte[] receipt;
    private int employeeID;
    private int adminID;
    private int statusID;
    private int typeID;

    public Reimbursement() {
    }

    public Reimbursement(int requestID, double amount, String submitDate, String resolvedDate, String description,
                         byte[] receipt, int employeeID, int adminID, int statusID, int typeID) {
        this.requestID = requestID;
        this.amount = amount;
        this.submitDate = submitDate;
        this.resolvedDate = resolvedDate;
        this.description = description;
        this.receipt = receipt;
        this.employeeID = employeeID;
        this.adminID = adminID;
        this.statusID = statusID;
        this.typeID = typeID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(String resolvedDate) {
        this.resolvedDate = resolvedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "requestID=" + requestID +
                ", amount=" + amount +
                ", submitDate='" + submitDate + '\'' +
                ", resolvedDate='" + resolvedDate + '\'' +
                ", description='" + description + '\'' +
                ", receipt=" + Arrays.toString(receipt) +
                ", employeeID=" + employeeID +
                ", adminID=" + adminID +
                ", statusID=" + statusID +
                ", typeID=" + typeID +
                '}';
    }
}
