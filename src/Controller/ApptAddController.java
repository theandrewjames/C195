package Controller;

import Helper.DBContacts;
import Helper.DBCustomer;
import Helper.DBappt;
import Model.Appointments;
import Model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class ApptAddController implements Initializable {
    public static int newApptId = 3;
    public ComboBox contactCombo;
    public TextField apptIDTF;
    public TextField apptTitleTF;
    public TextField apptDescriptTF;
    public TextField apptLocTF;
    public TextField typeTF;
    public DatePicker startDateDP;
    public ComboBox startCB;
    public ComboBox endCB;
    public DatePicker endDateDP;
    public TextField customerIdTF;
    public ComboBox userIdCB;
    public ComboBox custIdCB;


    public void Back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/apptView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Contact name combobox loaded with names from DB.Start/end time combobox loaded with time
     * at 30 min intervals from 00:00 to 23:30
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int nextId = 0;
            nextId = DBappt.getNextApptId() + 1;
            apptIDTF.setText(String.valueOf(nextId));
            contactCombo.setItems(DBContacts.getContactNames());
            ObservableList<Integer> custIds = FXCollections.observableArrayList();
            custIdCB.setItems(DBCustomer.GetCustIds());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ObservableList<Integer> users = FXCollections.observableArrayList();
        users.addAll(1, 2);
        userIdCB.setItems(users);
        ObservableList<String> times = FXCollections.observableArrayList();
        String [] halfHour = {"00", "30"};
        for(int i = 0;i < 24;i++) {
            for(int j =0;j<2;j++) {
                String time = i + ":" + halfHour[j];
                if(i < 10) {
                    time = "0" + time;
                }
                times.add(time);
            }
        }
        startCB.setItems(times);
        endCB.setItems(times);
    }

    public  boolean AddAppt(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            Integer apptId = Integer.parseInt(apptIDTF.getText());
            String title = apptTitleTF.getText();
            String description = apptDescriptTF.getText();
            String location = apptLocTF.getText();
            String contactName = contactCombo.getValue().toString();
            String type = typeTF.getText();
            LocalDate startDate = startDateDP.getValue();
            LocalTime startTime = LocalTime.parse(startCB.getValue().toString());
            LocalDateTime startDT = LocalDateTime.of(startDate, startTime);
            LocalDate endDate = endDateDP.getValue();
            LocalTime endTime = LocalTime.parse(endCB.getValue().toString());
            LocalDateTime endDT = LocalDateTime.of(endDate, endTime);
            //Checking below if appt is between 8am-10pm EST
            ZonedDateTime open = ZonedDateTime.of(startDate, LocalTime.of(8, 00), ZoneId.of("America/New_York"));
            ZonedDateTime close = ZonedDateTime.of(endDate, LocalTime.of(22, 00), ZoneId.of("America/New_York"));
            ZonedDateTime startAppt = ZonedDateTime.of(startDate, startTime, ZoneId.of("America/New_York"));
            ZonedDateTime endAppt = ZonedDateTime.of(endDate, endTime, ZoneId.of("America/New_York"));
            if (startAppt.isBefore(open) || startAppt.isAfter(close) || endAppt.isBefore(open) || endAppt.isAfter(close)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Please select a time between business hours of 8am-10pm EST");
                Optional<ButtonType> result = alert.showAndWait();
                return false;
            }
            //Check if appt start time is before appt end time
            if (startAppt.isAfter(endAppt) || startAppt.isEqual(endAppt)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setContentText("Start time needs to before end time");
                Optional<ButtonType> result = alert.showAndWait();
                return false;
            }
            Integer userId = Integer.parseInt(userIdCB.getValue().toString());
            Integer customerId = Integer.parseInt(custIdCB.getValue().toString());
            //Checks if appts overlap
            if (overlapCheck(startDT, endDT, customerId) == false) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Time error");
                alert.setContentText("Unable to schedule appt during existing appt");
                Optional<ButtonType> result = alert.showAndWait();
                return false;
            }
            ;
            Appointments appt = new Appointments(apptId, title, description, location, type, startDT,
                    endDT, customerId, userId, contactName);
            if (DBappt.addAppt(appt) == 1) {
                Parent root = FXMLLoader.load(getClass().getResource("/View/apptView.fxml"));
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                return true;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Database error");
                alert.setContentText("Unable to add appt. Please try again");
                Optional<ButtonType> result = alert.showAndWait();
                return false;
            }
        }
        catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Please complete form");
            alert.setContentText("Please do not leave any empty fields");
            Optional<ButtonType> result = alert.showAndWait();
            return false;
        }
    }

    public boolean overlapCheck(LocalDateTime startDT, LocalDateTime endDT, Integer customerID) throws SQLException {
        ObservableList<Appointments> appts = DBappt.getAllAppts();
        for(Appointments appt : appts) {
            if(appt.getCustomerID() == customerID && (startDT.isAfter(appt.getStartTime()) || endDT.isBefore(appt.getEndTime()))){

                return false;
            }
        }
        return true;
    }
}
