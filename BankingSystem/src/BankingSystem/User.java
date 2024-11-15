package BankingSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    private String Name;
    private String Address;
    private String ContactNumber;
    private String CNIC;
    private String AccountNumber;

    public User(String name, String CNIC, String ContactNumber, String Address, String AccountNumber) {
        this.Name = name;
        this.ContactNumber = ContactNumber;
        this.Address = Address;
        this.CNIC = CNIC;
        this.AccountNumber = AccountNumber;

    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public String getCNIC() {
        return CNIC;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void viewUserDetail() {
        System.out.println("Name:" + getName() + "\nAddress:" + getAddress() + "\nPhone Number" + getContactNumber() + "\nCNIC:" + getCNIC() + "\nAccount Number :" + getAccountNumber());
    }

    public void AddtoDatabase()
            throws IOException {
        String sql = "Insert into USER(Name,CNIC,Address,Contact,AccountNumber) values(?,?,?,?,?)";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstate = conn.prepareStatement(sql)) {
            pstate.setString(1, Name);
            pstate.setString(2, CNIC);
            pstate.setString(3, Address);
            pstate.setString(4, ContactNumber);
            pstate.setString(5, AccountNumber);
            pstate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.logError(e.getMessage());

        }
    }

}
