package Helper;

import Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

//Retries all appointments
public class DBappt {
    public static ObservableList<Appointments>getAllAppts() throws SQLException {
        ObservableList<Appointments> appts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM APPOINTMENTS";
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
            int contactID = rs.getInt("contact_ID");
            Appointments appointment = new Appointments(apptID,apptTitle,apptDescription,apptLocation,apptType,start,end,customerID,userID,contactID);
            appts.add(appointment);
        }
        return appts;

    }
}
