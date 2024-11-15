package BankingSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankServices {

    public BankServices() {
    }

    public void createAccount(String Name, int PIN, String AccountNmbr, double amount)
            throws SQLException, IOException {
        Account account = new Account(Name, PIN, AccountNmbr, amount);
        account.ToDatabase();
        System.out.println("Account Successfully created:" + AccountNmbr + "-" + Name + "-" + PIN + "-" + amount);

    }

    public void performTransaction(String accountNmbr, double amount, String type)
            throws InsufficientFundsException, SQLException, IOException, InvalidAccountNumberException {
        try {
            Account account = findAccount(accountNmbr);
            if (account == null) {
                throw new InvalidAccountNumberException("Error:Account Number: " + accountNmbr + " not found");
            }
            if (type.equalsIgnoreCase("Deposit")) {
                account.deposit(amount);
            } else if (type.equalsIgnoreCase("Withdraw")) {
                account.withdraw(amount);
            } else {
                System.out.println("Error: Invalid Type!");
                return;
            }

            Transaction transaction = new Transaction(accountNmbr, amount, type);
            transaction.ToDatabase();
            Logger.logTransaction("Account Number :" + transaction.getAccountNumber() + "\tTransaction Type :" + transaction.getTransactionType() + "\tAmount :" + transaction.getAmount() + "\tTimeStamp :" + transaction.getTimestamp());
            System.out.println("Transaction successful!");

        } catch (InvalidAccountNumberException e) {
            System.out.println(e.getMessage());
            Logger.logError(e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
            Logger.logError(e.getMessage());
        }
    }

    public Account findAccount(String acc_nmbr) {
        String sql = "SELECT * FROM Account1 where AccountNumber=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, acc_nmbr);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    String Name = resultSet.getString("AccountName");
                    String AccountNumber = resultSet.getString("AccountNumber");
                    double amount = resultSet.getDouble("Balance");
                    int PIN = resultSet.getInt("PIN");
                    return new Account(Name, PIN, AccountNumber, amount);
                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void viewTransactionHistory(String AccountNmbr) {

        String sql = "SELECT * FROM Transaction where AccountNumber1=?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, AccountNmbr);
            try (ResultSet resultSet = statement.executeQuery()) {
                System.out.println("TransactionID:\t\tAccountNumber:\t\tAmount:\t\tTransaction Type:\t\tTimeStamp:");


                while (resultSet.next()) {
                    int TransactionID = resultSet.getInt("TransactionID");
                    String AccountNumber = resultSet.getString("AccountNumber1");
                    double amount = resultSet.getDouble("Amount");
                    String Type = resultSet.getString("TransactionType");
                    LocalDateTime timestamp = resultSet.getTimestamp("Timestamp").toLocalDateTime();

                    System.out.println(TransactionID + "\t\t\t\t\t\t" + AccountNumber + "\t\t\t" + amount + "\t\t\t" + Type + "\t\t\t" + timestamp);
                }
                System.out.println("---------------------------------------------------------------------------");


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void Login(String AccountNumber, int PIN)
            throws InsufficientFundsException, SQLException, IOException, InvalidAccountNumberException {
        Account account1 = findAccount(AccountNumber);
        if (account1 == null) {
            throw new InvalidAccountNumberException("Error:Account Number " + AccountNumber + " not Found");

        }

        if (account1.getPIN() != PIN) {
            throw new InvalidAccountNumberException("Error:PIN of this Account " + AccountNumber + " is Invalid");

        } else {
            char choice;
            Scanner input = new Scanner(System.in);
            while (true) {
                System.out.println("1] Deposit");
                System.out.println("2] Withdraw");
                System.out.println("3] View Balance");
                System.out.println("4] View Transaction History");
                System.out.println("5] Exit");
                choice = input.next().charAt(0);
                input.nextLine();
                System.out.println("=========================== DASHBOARD ============================");
                switch (choice) {
                    case '1':
                        try {
                            System.out.println("Enter Amount to Deposit :");
                            double amount = input.nextDouble();
                            input.nextLine();
                            performTransaction(account1.getAccountNumber(), amount, "Deposit");
                        } catch (InputMismatchException e) {
                            System.out.println("Error:Invalid input");
                            Logger.logError("Error:Invalid input");
                        }
                        break;
                    case '2':
                        try {
                            System.out.println("Enter Amount for Withdraw :");
                            double amount1 = input.nextDouble();
                            input.nextLine();
                            performTransaction(account1.getAccountNumber(), amount1, "Withdraw");
                        } catch (InputMismatchException e) {
                            System.out.println("Error:Invalid input");
                            Logger.logError("Error:Invalid input");
                        }

                        break;
                    case '3':
                        account1.viewBalance();
                        break;
                    case '4':
                        viewTransactionHistory(account1.getAccountNumber());
                        break;
                    case '5':
                        return;

                    default:
                        System.out.println("Invalid Input.Please choose the number from Menu!");
                }


            }
        }
    }

    public void signUP() throws SQLException, IOException {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("\n                       Enter Personal Information\n");
            System.out.println("Name :");
            String Name = input.nextLine();
            System.out.println("CNIC :");
            String CNIC = input.nextLine();
            System.out.println("Contact Number:");
            String contact = input.nextLine();
            System.out.println("Address :");
            String Address = input.nextLine();
            System.out.println("\n------------------------------------------------------------------------\n");
            System.out.println("\n                       Enter Account Information\n");
            System.out.println("Account Title :");
            String AccountName = input.nextLine();
            System.out.println("Account Number :");
            String AccountNumber = input.nextLine();
            System.out.println("PIN :");
            int PIN = input.nextInt();
            System.out.println("Initial Balance :");
            double amount = input.nextDouble();
            //      Function call Create Acccount
            createAccount(AccountName, PIN, AccountNumber, amount);
//            the object of User class
            User user = new User(Name, CNIC, contact, Address, AccountNumber);
            user.AddtoDatabase();
        } catch (InputMismatchException e) {
            System.out.println("Error:Invalid input!");
            Logger.logError("Error:Invalid input!");
        }


    }


}
