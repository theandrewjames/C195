package Helper;

import Model.Appointments;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

//Retrieves all appointments
public class DBappt {
    public static ObservableList<Appointments>getAllAppts() throws SQLException {
        ObservableList<Appointments> appts = FXCollections.observableArrayList();
        String sql = "SELECT appointments.Appointment_ID,appointments.Title,appointments.Description,\n" +
                "appointments.Location,appointments.Type,appointments.Start,appointments.end,appointments.Customer_ID,\n" +
                "appointments.User_ID,contacts.Contact_ID,contacts.Contact_Name\n" +
                "FROM appointments\n" +
                "INNER JOIN contacts on appointments.Contact_ID = contacts.Contact_ID";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int apptID = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String apptDescription = rs.getString("Description");
            String apptLocation = rs.getString("Location");
            String apptType = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("end").toLocalDateTime();
            int customerID = rs.getInt("customer_ID");
            int userID = rs.getInt("user_ID");
            String contactName = rs.getString("contact_name");
            Appointments appointment = new Appointments(apptID,apptTitle,apptDescription,apptLocation,apptType,start,end,customerID,userID,contactName);
            appts.add(appointment);
        }
        return appts;

    }
    public static int getNextApptId() throws SQLException {
        String sql = "SELECT max(Appointment_ID) FROM appointments";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int highest = 0;
        while(rs.next()) {
            highest = rs.getInt("MAX(Appointment_ID)");
        }
        return highest;
    }
    public static int addAppt(Appointments appointments) throws  SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String contactName = appointments.getContactName();
        Integer contactId = 0;
        if(contactName.equals("Anika Costa")) contactId = 1;
        else if(contactName.equals("Daniel Garcia")) contactId = 2;
        else contactId =3;
        String sql = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Start,End, Create_Date," +
                "Created_By,Last_Update,Last_Updated_By,Customer_ID,User_ID,Contact_ID)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?, ?)";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1,appointments.getApptID());
        ps.setString(2,appointments.getApptTitle());
        ps.setString(3,appointments.getApptDescription());
        ps.setString(4,appointments.getApptLocation());
        ps.setString(5, appointments.getApptType());
        ps.setTimestamp(6, Timestamp.valueOf(appointments.getStartTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
        ps.setTimestamp(7, Timestamp.valueOf(appointments.getEndTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime()));
        ps.setTimestamp(8, Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).format(dtf)));
        ps.setString(9, "Admin");
        ps.setTimestamp(10, Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).format(dtf)));
        ps.setString(11, "Admin");
        ps.setInt(12, appointments.getCustomerID());
        ps.setInt(13, appointments.getUserID());
        ps.setInt(14, contactId);
        int rs = ps.executeUpdate();
        return rs;
    }
}
