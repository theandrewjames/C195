package Controller;

import Helper.DBContacts;
import Helper.DBCustomer;
import Helper.DBappt;
import Model.Contacts;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    public void AddAppt(ActionEvent actionEvent) {
        Integer apptId = Integer.parseInt(apptIDTF.getText());
        String title = apptTitleTF.getText();
        String description = apptDescriptTF.getText();
        String location = apptLocTF.getText();
        String contactName = contactCombo.getValue().toString();
        String type = typeTF.getText();
        LocalDate startDate = startDateDP.getValue();

        LocalDate endDate = endDateDP.getValue();

        Integer userId = Integer.parseInt(userIdCB.getValue().toString());
        Integer customerId = Integer.parseInt(customerIdTF.getText());

    }
}
