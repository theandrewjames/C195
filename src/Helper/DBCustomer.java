package Helper;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Manipulates database customer records
 */
public class DBCustomer {
    /**
     * gets all customers
     * @return
     * @throws SQLException
     */
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

    /**
     * Gets all divisions
     * @param country
     * @return
     * @throws SQLException
     */
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

    /**
     * Gets division id based on parameter division string
     * @param division
     * @return
     * @throws SQLException
     */
    public static int getDivisionId(String division) throws SQLException {
        String sql = "SELECT Division_ID from first_level_divisions WHERE Division=?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        int divisionID = 0;
        while(rs.next()) {
            divisionID = rs.getInt("Division_ID");
        }
        return  divisionID;
    }

    /**
     * Gets next available customer ID and returns it
     * @return
     * @throws SQLException
     */
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

    /**
     * Gets all customer ids and returns them
     * @return
     * @throws SQLException
     */
    public static ObservableList<Integer> GetCustIds() throws SQLException {
        ObservableList<Integer> custIds = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID from customers ORDER BY Customer_ID";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Integer customerId = rs.getInt("Customer_ID");
            custIds.add(customerId);
        }
        return custIds;
    }

    /**
     * Deletes customer ID if paramter customer matches existing customer
     * @param customers
     * @throws SQLException
     */
    public static void deleteCustomer(Customers customers) throws SQLException {
        String sql = "SELECT * FROM appointments where Customer_ID =?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1, customers.getCustomerId());
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Cannot delete customer with open appts. Please delete appts first");
            Optional<ButtonType> result = alert.showAndWait();
        }
        else {
            String sql2 = "DELETE from customers WHERE Customer_ID=?";
            PreparedStatement ps1 = Database.connection.prepareStatement(sql2);
            ps1.setInt(1, customers.getCustomerId());
            int rows = ps1.executeUpdate();
        }
    }

    /**
     * Adds customer to database
     * @param customers
     * @return
     * @throws SQLException
     */
    public static int addCustomer(Customers customers) throws SQLException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)Values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1, customers.getCustomerId());
        ps.setString(2, customers.getCustomerName());
        ps.setString(3, customers.getCustomerAddress());
        ps.setString(4, customers.getPostalCode());
        ps.setString(5, customers.getCustomerPhone());
        ps.setTimestamp(6, Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).format(dtf)));
        ps.setString(7, "Admin");
        ps.setTimestamp(8, Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).format(dtf)));
        ps.setString(9, "Admin");
        ps.setInt(10, DBCustomer.getDivisionId(customers.getDivison()));
        int rs = ps.executeUpdate();
        return rs;
    }

    /**
     * Updates customer
     * @param customers
     * @return
     * @throws SQLException
     */
    public static int updateCustomer(Customers customers) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sql = "UPDATE customers SET Customer_Name=?,Address=?,Postal_Code=?,Phone=?,Last_Update=?,Last_Updated_By=?,Division_ID=? WHERE Customer_ID=?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, customers.getCustomerName());
        ps.setString(2, customers.getCustomerAddress());
        ps.setString(3, customers.getPostalCode());
        ps.setString(4, customers.getCustomerPhone());
        ps.setTimestamp(5, Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).format(dtf)));
        ps.setString(6, "Admin");
        ps.setInt(7, DBCustomer.getDivisionId(customers.getDivison()));
        ps.setInt(8, customers.getCustomerId());
        int rs = ps.executeUpdate();
        return rs;
    }

    /**
     * For report which shows how many customers in each country
     * @throws SQLException
     */
    public static void groupByCountry() throws SQLException {
        String sql = "SELECT COUNT(customers.Customer_ID),countries.country FROM ((customers\n" +
                "INNER JOIN first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID)\n" +
                "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID)\n" +
                "GROUP BY country";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String results = "";
        while(rs.next()) {
            results += "\n" + "Total number of customers in " + rs.getString("country") + " is "
                    + rs.getInt("COUNT(customers.Customer_ID)");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Report");
        alert.setContentText(results);
        Optional<ButtonType> result = alert.showAndWait();
    }
}
