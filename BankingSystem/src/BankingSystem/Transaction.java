package BankingSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Transaction {
    private String AccountNumber;
    private double amount;
    private String TransactionType;
    private LocalDateTime timestamp;

    public Transaction(String accountNumber, double amount, String transactionType) {
//        this.TransactionID=tid;
        this.AccountNumber = accountNumber;
        this.amount = amount;
        this.TransactionType = transactionType;
        this.timestamp = LocalDateTime.now();

    }

    //    public int getTransactionID(){return TransactionID;}
    public String getAccountNumber() {
        return AccountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void viewTansaction() {
        System.out.println("AccountNumber :" + getAccountNumber() + "\nAmount:" + getAmount() + "\nType" + getTransactionType() + "\nTime" + getTimestamp());
    }

    public void ToDatabase() throws SQLException {
        String sql = "INSERT INTO Transaction (AccountNumber1, Amount, TransactionType, Timestamp) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstate = conn.prepareStatement(sql)) {
            pstate.setString(1, AccountNumber);
            pstate.setDouble(2, amount);
            pstate.setString(3, TransactionType);
            pstate.setTimestamp(4, Timestamp.valueOf(timestamp));
            pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
