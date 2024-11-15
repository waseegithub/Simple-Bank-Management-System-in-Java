package BankingSystem;

import javax.swing.text.StyledEditorKit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Account {
    private String AccountTitle;
    private String AccountNumber;
    private double balance;
    private int PIN;

    public Account(String accountname, int PIN, String accountNumber, double initialbalance) {
        this.AccountTitle = accountname;
        this.AccountNumber = accountNumber;
        this.balance = initialbalance;
        this.PIN = PIN;
    }

    public String getAccountTitle() {
        return AccountTitle;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public int getPIN() {
        return PIN;
    }

    public void setAccountTitle(String accountTitle) {
        this.AccountTitle = accountTitle;
    }

    public void setAccountNumber(String accountNumber) {
        this.AccountNumber = accountNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setPIN(int pin) {
        this.PIN = pin;
    }

    public void deposit(double newbalance)
            throws SQLException, IOException {

        if (balance > 0) {
            balance += newbalance;
            UpdateAccountFromDatabase();
            System.out.println("Entered Amount deposited successfully!");
        } else {
            System.out.println("Entered amount should be greater than Zero(0)!");
        }
    }

    public void withdraw(double newbalance)
            throws InsufficientFundsException, SQLException, IOException {
        if (newbalance > 0 && newbalance <= balance) {
            balance -= newbalance;
            UpdateAccountFromDatabase();
            System.out.println("Amount withdrawed successfully!");

        } else {

            throw new InsufficientFundsException("Error:Insufficient balance of Account Number:" + getAccountNumber());
        }
    }

    public void viewBalance()


            throws IOException {
        String sql = "SELECT Balance FROM Account1 where AccountNumber=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, getAccountNumber());
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    double amount = resultSet.getDouble("Balance");

                    System.out.println("Your current balance is :" + amount);
                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
            Logger.logError(e.getMessage());
        }


    }

    public void ToDatabase() throws SQLException, IOException {
        String sql = "Insert into Account1(AccountName,PIN,AccountNumber,Balance) values(?,?,?,?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstate = conn.prepareStatement(sql)) {
            pstate.setString(1, AccountTitle);
            pstate.setInt(2, PIN);
            pstate.setString(3, AccountNumber);
            pstate.setDouble(4, balance);
            pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.logError(e.getMessage());

        }

    }

    public void UpdateAccountFromDatabase() throws SQLException, IOException {
        String sql = "UPDATE  Account1 set Balance=? where AccountNumber=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstate = conn.prepareStatement(sql)) {
            pstate.setDouble(1, balance);
            pstate.setString(2, AccountNumber);
            pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.logError(e.getMessage());

        }

    }

}
