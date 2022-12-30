package Helper;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCustomer {
    public static ObservableList<Customers> getAllCustomers() throws SQLException {
        ObservableList<Customers> customers = FXCollections.observableArrayList();
        String sql = "SELECT customers.Customer_ID, customers.customer_name, customers.address, customers.Postal_Code, customers.Phone, first_level_divisions.Division,  customers.Division_ID, countries.country \n" +
                "FROM ((customers\n" +
                "INNER JOIN  first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID)\n" +
                "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID)";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerPhone = rs.getString("Phone");
            String customerAddress = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String division = rs.getString("Division");
            String country = rs.getString("Country");
            Customers customer = new Customers(customerId, customerName, customerPhone, customerAddress, postalCode, division, country);
            customers.add(customer);
        }
        return customers;
    }
    public static ObservableList<String> getDivisions(String country) throws SQLException {
        int countryId = 0;
        if(country.equals("U.S")) countryId = 1;
        else if(country.equals("UK")) countryId = 2;
        else countryId = 3;
        ObservableList<String> divisions = FXCollections.observableArrayList();
        String sql = "SELECT Division FROM first_level_divisions WHERE Country_ID =" + countryId;
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String division = rs.getString("Division");
            divisions.add(division);
        }
        return divisions;
    }
    public static int getNextCusId() throws SQLException {
        String sql = "SELECT MAX(Customer_ID) from customers";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int highest = 0;
        while(rs.next()) {
             highest = rs.getInt("MAX(Customer_ID)");
        }
        return highest;
    }

}
