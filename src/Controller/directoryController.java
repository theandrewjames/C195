package Controller;

import Helper.DBCustomer;
import Helper.DBappt;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Controls directory buttons for customers, appts, reports
 */
public class directoryController {


    public int totalNumButton;
    public int countryReportBtn;

    /**
     * Exits window
     * @param actionEvent
     */
    public void ExitWindow(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Takes user to appts tableview page
     * @param actionEvent
     * @throws IOException
     */
    public void GoToAppts(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/apptView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes user to customers tableview page
     * @param actionEvent
     * @throws IOException
     */
    public void GoToCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/CustomerView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays report which shows total number of appts per month and type
     * @param actionEvent
     * @throws SQLException
     */
    public void TotalNumAppts(ActionEvent actionEvent) throws SQLException {
        DBappt.getApptsByMonth();
    }

    /**
     * Displays report which shows schedules for each contact
     * @param actionEvent
     * @throws SQLException
     */
    public void scheduleReport(ActionEvent actionEvent) throws SQLException {
    DBappt.getApptByContact();
    }

    /**
     * Displays report which shows how many customers are from each country
     * @param actionEvent
     * @throws SQLException
     */
    public void groupByCountry(ActionEvent actionEvent) throws SQLException {
        DBCustomer.groupByCountry();
    }
}
