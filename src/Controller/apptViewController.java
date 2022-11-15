package Controller;

import Helper.DBappt;
import Model.Appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class apptViewController implements Initializable {
    public TableView<Appointments> apptTV;
    public TableColumn<Appointments, Integer> apptIDCol;
    public TableColumn<Appointments, String>titleCol;
    public TableColumn<Appointments, String> descriptCol;
    public TableColumn<Appointments, String> locCol;
    public TableColumn<Appointments, Integer> contactCol;
    public TableColumn<Appointments, String> typeCol;
    public TableColumn<Appointments, LocalDateTime> startCol;
    public TableColumn<Appointments, LocalDateTime> endCol;
    public TableColumn<Appointments, Integer> custIDCol;
    public TableColumn<Appointments, Integer> userIDCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descriptCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        try {
            apptTV.setItems(DBappt.getAllAppts());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void filterWeek(ActionEvent actionEvent) {
    }

    public void filterMonth(ActionEvent actionEvent) {
    }

    public void filterAll(ActionEvent actionEvent) {
    }
}
