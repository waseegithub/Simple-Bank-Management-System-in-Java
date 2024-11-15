package BankingSystem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Bank {

    public Bank() {

    }

    public static void Menu()
            throws InsufficientFundsException, SQLException, IOException, InvalidAccountNumberException {
        System.out.println("\n=====================WELCOME TO SIMPLE BANKING SYSTEM ============================\n");
        System.out.println("HABIB BANK LIMITED (HBL)");
        System.out.println("Branch Code : 0465");
        System.out.println("Garh Maharaja More,Ahmad Pur Sial,Jhang");
        System.out.println("==============================================================================");

        Scanner input = new Scanner(System.in);
        BankServices bankservices = new BankServices();
        char choice = '0';

        while (true) {
            System.out.println("1] Login");
            System.out.println("2] SignUp/Create Account");
            System.out.println("3] Exit");
            choice = input.nextLine().charAt(0);
            switch (choice) {
                case '1':
                    System.out.println("================================ Login =========================");
                    System.out.println("Enter Account Number :");
                    String AccountNumber = input.nextLine();
                    System.out.println("Enter you PIN :");
                    int PIN = input.nextInt();
                    try {
                        bankservices.Login(AccountNumber, PIN);

                    } catch (InvalidAccountNumberException e) {
                        System.out.println(e.getMessage());
                        Logger.logError(e.getMessage());
                    }
                    input.nextLine();
                    break;
                case '2':
                    System.out.println("================================ SIGNUP/CREATE ACCOUNT =========================");
                    bankservices.signUP();


                    break;
                case '3':
                    System.exit(0);

                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }

    }

    public static void main(String[] args)
            throws InsufficientFundsException, SQLException, IOException, InvalidAccountNumberException {
        Menu();
    }
}
