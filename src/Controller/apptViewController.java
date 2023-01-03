package Controller;

import Helper.DBappt;
import Model.Appointments;
import com.sun.scenario.effect.Offset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ObservableList<Appointments> allAppts = null;
        try {
            allAppts = DBappt.getAllAppts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        descriptCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        apptTV.setItems(allAppts);


    }

    /**
     *
     * @param actionEvent filters appts by current week
     * @throws SQLException
     */
    public void filterWeek(ActionEvent actionEvent) throws SQLException {
        LocalDateTime start = LocalDateTime.now().minusWeeks(1);
        LocalDateTime end = LocalDateTime.now().plusWeeks(1);
        ObservableList<Appointments> allAppts = DBappt.getAllAppts();
        ObservableList<Appointments> apptsWeek = FXCollections.observableArrayList();
        allAppts.forEach(appointments -> {
            if(appointments.getStartTime().isEqual(start) || appointments.getEndTime().isEqual(end)
            || (appointments.getStartTime().isAfter(start) && appointments.getEndTime().isBefore(end))){
                apptsWeek.add(appointments);
            }
        });
            apptTV.setItems(apptsWeek);
    }

    /**
     *
     * @param actionEvent filters appointment tableview by current month
     * @throws SQLException
     */
    public void filterMonth(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointments> allAppts = DBappt.getAllAppts();
        ObservableList<Appointments> apptsMonth = FXCollections.observableArrayList();
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        allAppts.forEach(appointments -> {
            if(appointments.getStartTime().getMonthValue() == currentMonth && appointments.getStartTime().getYear() == currentYear) {
                apptsMonth.add(appointments);
            }
        });
        apptTV.setItems(apptsMonth);

    }

    /**
     * removes month/week filter and displays all appts
     * @param actionEvent
     */
    public void filterAll(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointments> allAppts = DBappt.getAllAppts();
        apptTV.setItems(allAppts);
    }

    /**
     *
     * @param actionEvent returns user to directory page by clicking back button
     * @throws IOException
     */
    public void Back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Directory.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void DeleteAppt(ActionEvent actionEvent) throws SQLException {
        Appointments appt = apptTV.getSelectionModel().getSelectedItem();
        DBappt.deleteAppt(appt);
        apptTV.setItems(DBappt.getAllAppts());
    }

    public void EditAppt(ActionEvent actionEvent) throws IOException {
        Appointments appt = apptTV.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/apptUpdate.fxml"));
        Parent root = loader.load();
        ApptUpdateController auc = loader.getController();
        auc.loadAppt(appt);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void AddAppt(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/apptAdd.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
