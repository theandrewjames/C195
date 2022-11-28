package Helper;

import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {
    public static ObservableList<Contacts>getAllContacts() throws SQLException {
    ObservableList<Contacts> contacts = FXCollections.observableArrayList();
    String sql = "SELECT * FROM contacts";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contacts contact = new Contacts(contactId,contactName,contactEmail);
            contacts.add(contact);
        }
        return contacts;
    }
    public static ObservableList<String>getContactNames() throws SQLException {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT Contact_Name FROM contacts";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String contactName = rs.getString("Contact_Name");
            contactNames.add(contactName);
        }
        return contactNames;
    }
}
