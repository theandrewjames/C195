package Controller;

import Helper.DBContacts;
import Helper.DBCustomer;
import Helper.DBappt;
import Model.Appointments;
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

public class ApptUpdateController implements Initializable {
    public TextField apptIDTF;
    public TextField apptTitleTF;
    public TextField apptDescriptTF;
    public TextField apptLocTF;
    public ComboBox contactCombo;
    public TextField typeTF;
    public DatePicker startDateDP;
    public DatePicker endDateDP;
    public ComboBox startCB;
    public ComboBox endCB;
    public ComboBox custIdCB;
    public ComboBox userIdCB;

    public void Back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/apptView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public  boolean UpdateAppt(ActionEvent actionEvent) throws SQLException, IOException {
        Integer apptId = Integer.parseInt(apptIDTF.getText());
        String title = apptTitleTF.getText();
        String description = apptDescriptTF.getText();
        String location = apptLocTF.getText();
        String contactName = (String) contactCombo.getValue();
        String type = typeTF.getText();
        LocalDate startDate = startDateDP.getValue();
        LocalTime startTime = LocalTime.parse(startCB.getValue().toString());
        LocalDateTime startDT = LocalDateTime.of(startDate, startTime);
        LocalDate endDate = endDateDP.getValue();
        LocalTime endTime = LocalTime.parse(endCB.getValue().toString());
        LocalDateTime endDT = LocalDateTime.of(endDate, endTime);
        //Checking below if appt is between 8am-10pm EST
        LocalTime open = LocalTime.of(8, 00);
        LocalTime close = LocalTime.of(22, 00);
        ZonedDateTime startAppt = startDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
        ZonedDateTime endAppt = endDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalTime endApptLocal = endAppt.toLocalTime();
        LocalTime startApptLocal = startAppt.toLocalTime();
        if (startApptLocal.isBefore(open) || startApptLocal.isAfter(close) || endApptLocal.isBefore(open) || endApptLocal.isAfter(close)) {
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
        System.out.println(startDT);
        System.out.println(endDT);
        System.out.println(customerId);
        System.out.println("-Results:");
        if (overlapCheck(startDT, endDT, customerId, apptId) == false) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Time error");
            alert.setContentText("Unable to schedule appt during existing appt");
            Optional<ButtonType> result = alert.showAndWait();
            return false;
        }
        Appointments appt = new Appointments(apptId, title, description, location, type, startDT,
                endDT, customerId, userId, contactName);
        if (DBappt.updateAppt(appt) == 1) {
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
    public void loadAppt(Appointments appt) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        apptIDTF.setText(String.valueOf(appt.getApptID()));
        apptTitleTF.setText(appt.getApptTitle());
        apptDescriptTF.setText(appt.getApptDescription());
        apptLocTF.setText(appt.getApptLocation());
        ObservableList<String> contacts = FXCollections.observableArrayList();
        contacts.addAll("Anika Costa", "Daniel Garcia", "Li Lee");
        contactCombo.setItems(contacts);
        contactCombo.setValue(appt.getContactName());
        typeTF.setText(appt.getApptType());
        startDateDP.setValue(appt.getStartTime().toLocalDate());
        startCB.setValue(appt.getStartTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of(String.valueOf(ZoneId.systemDefault()))).format(dtf));
        endDateDP.setValue(appt.getEndTime().toLocalDate());
        endCB.setValue(appt.getEndTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.of(String.valueOf(ZoneId.systemDefault()))).format(dtf));
        custIdCB.setValue(appt.getCustomerID());
        userIdCB.setValue(appt.getUserID());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        ObservableList<Integer> users = FXCollections.observableArrayList();
        users.addAll(1, 2);
        userIdCB.setItems(users);
        ObservableList<Integer> custIds = FXCollections.observableArrayList();
        try {
            custIdCB.setItems(DBCustomer.GetCustIds());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public boolean overlapCheck(LocalDateTime startDT, LocalDateTime endDT, Integer customerID, Integer apptId) throws SQLException {
        LocalDateTime startUTC = startDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime endUTC = endDT.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        System.out.println("UTC:");
        System.out.println(startUTC);
        System.out.println(endUTC);
        ObservableList<Appointments> appts = DBappt.getAllAppts();
        for(Appointments appt : appts) {
            System.out.println("appt start");
            System.out.println(appt.getStartTime());
            if(appt.getCustomerID() == customerID && appt.getApptID() != apptId && ((startUTC.isAfter(appt.getStartTime()) && startUTC.isBefore(appt.getEndTime())))){

                return false;
            }
            else if(appt.getCustomerID() == customerID && appt.getApptID() != apptId &&(endUTC.isBefore(appt.getEndTime()) && endUTC.isAfter(appt.getStartTime()))){
                return false;
            }
            else if(appt.getCustomerID() == customerID && appt.getApptID() != apptId &&(startUTC.isEqual(appt.getStartTime()) && endUTC.isEqual(appt.getEndTime()))) {
                return false;
            }
            else if(appt.getCustomerID() == customerID && appt.getApptID() != apptId &&(startUTC.isEqual(appt.getStartTime()) && endUTC.isAfter(appt.getEndTime()))) {
                return false;
            }
            else if(appt.getCustomerID() == customerID && appt.getApptID() != apptId &&(endUTC.isEqual(appt.getEndTime()) && startUTC.isBefore(appt.getEndTime()))) {
                return false;
            }


        }
        return true;
    }
}
