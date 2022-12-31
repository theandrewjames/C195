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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    public void UpdateAppt(ActionEvent actionEvent) {
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
        startCB.setValue(appt.getStartTime().format(dtf));
        endDateDP.setValue(appt.getEndTime().toLocalDate());
        endCB.setValue(appt.getEndTime().format(dtf));
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
}
