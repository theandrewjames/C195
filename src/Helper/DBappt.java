package Helper;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


/**
 * Retrieves appointments
 */
public class DBappt {
    /**
     * Gets all appointments for each month
     * @throws SQLException
     */
    public static void getApptsByMonth() throws SQLException {
        String results = "";
        int i = 1;
        while(i < 13) {
            String sql = "SELECT count(Type), Type from appointments where Month(Start) = ? group by Type;";
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ps.setInt(1, i);
            results = results + "\n" + Month.of(i) + " " + "Number & Type of appointments:";
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                    results = results + "\n" + rs.getInt("count(Type)") + " " + rs.getString("Type");
            }
            if(rs.next() == false) {
                i++;
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Report");
        alert.setContentText(results);
        Optional<ButtonType> result = alert.showAndWait();
    }

    /**
     * Gets all appointments
     * @return
     * @throws SQLException
     */
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

    /**
     * Generates next available appt id
     * @return
     * @throws SQLException
     */
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

    /**
     * Adds appt to database
     * @param appointments
     * @return
     * @throws SQLException
     */
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
        ps.setTimestamp(6, Timestamp.valueOf(appointments.getStartTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).format(dtf)));
        ps.setTimestamp(7, Timestamp.valueOf(appointments.getEndTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).format(dtf)));
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

    /**
     * Updates appt to database
     * @param appt
     * @return
     * @throws SQLException
     */
    public static int updateAppt(Appointments appt) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String contactName = appt.getContactName();
        Integer contactId = 0;
        if(contactName.equals("Anika Costa")) contactId = 1;
        else if(contactName.equals("Daniel Garcia")) contactId = 2;
        else contactId =3;
        String sql = "UPDATE appointments SET Appointment_ID=?,Title=?, Description=?, Location=?, Type=?, Start=?,End=?,Last_Update=?,Last_Updated_By=?,Customer_ID=?,User_ID=?,Contact_ID=? WHERE Appointment_ID=?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1, appt.getApptID());
        ps.setString(2, appt.getApptTitle());
        ps.setString(3, appt.getApptDescription());
        ps.setString(4, appt.getApptLocation());
        ps.setString(5, appt.getApptType());
        ps.setTimestamp(6, Timestamp.valueOf(appt.getStartTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).format(dtf)));
        ps.setTimestamp(7, Timestamp.valueOf(appt.getEndTime().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).format(dtf)));
        ps.setTimestamp(8, Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).format(dtf)));
        ps.setString(9, "Admin");
        ps.setInt(10, appt.getCustomerID());
        ps.setInt(11, appt.getUserID());
        ps.setInt(12, contactId);
        ps.setInt(13, appt.getApptID());
        int rs = ps.executeUpdate();
        return rs;
    }

    /**
     * deletes appt from database
     * @param appt
     * @throws SQLException
     */
    public static void deleteAppt(Appointments appt) throws SQLException {
        String sql = "DELETE from appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1,appt.getApptID());
        int rowsAffected = ps.executeUpdate();
    }

    /**
     * Gets appointments for selected contact
     * @throws SQLException
     */
    public static void getApptByContact() throws  SQLException {
        String sql = "SELECT * from appointments where Contact_ID=?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        int i = 1;
        String results = "";
        String contactName = "";
        while(i < 4) {
            ps.setInt(1, i);
            if(i == 1) contactName = "Anika Costa";
            else if(i == 2) contactName = "Daniel Garcia";
            else if(i == 3) contactName = "Li Lee";
            results = results + "\n" + "Schedule for " + contactName + "\n";
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                results += "\n" + "Appt ID: " + rs.getInt("Appointment_ID") + " Title: "
                        + rs.getString("Title") + " Type: " + rs.getString("Type") +
                        " Description: " + rs.getString("Description") + " Start: " + rs.getTimestamp("Start").toLocalDateTime()
                        + " End: " + rs.getTimestamp("End").toLocalDateTime() + " Customer ID: " + rs.getInt("Customer_ID") + "\n";

            }
            if(rs.next() == false) i++;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Report");
        alert.setContentText(results);
        Optional<ButtonType> result = alert.showAndWait();
    }
}
