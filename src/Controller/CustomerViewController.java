package Controller;

import Helper.DBCustomer;
import Helper.DBappt;
import Model.Customers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Shows customers in tableview
 */
public class CustomerViewController implements Initializable {

    public TableView customerTV;
    public TableColumn customerNameTC;
    public TableColumn customerPhoneTC;
    public TableColumn customerIdTC;
    public Button addButton;
    public Button editButton;
    public Button deleteButton;
    public Button backButton;
    public TableColumn cusAddressTC;
    public TableColumn custPostalTC;
    public TableColumn custDivisionTC;
    public TableColumn custCountryTC;

    /**
     * Grabs customers and displasy them in tableview
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Customers> allCustomers = null;
        try {
            allCustomers = DBCustomer.getAllCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        customerNameTC.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneTC.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        customerIdTC.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        cusAddressTC.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        custPostalTC.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custDivisionTC.setCellValueFactory(new PropertyValueFactory<>("divison"));
        custCountryTC.setCellValueFactory(new PropertyValueFactory<>("country"));
        customerTV.setItems(allCustomers);


    }

    /**
     * Takes user to add customer screen
     * @param actionEvent
     * @throws IOException
     */
    public void Add(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/customerAdd.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Grabs selected customer and takes user to update customer screen
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void Update(ActionEvent actionEvent) throws IOException, SQLException {
        Customers customer = (Customers) customerTV.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/customerUpdate.fxml"));
        Parent root = loader.load();
        CustomerUpdateController cuc = loader.getController();
        cuc.loadCustomer(customer);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes user to previous screen
     * @param actionEvent
     * @throws IOException
     */
    public void Back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/Directory.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Deletes selected customer. If none selected it displays an error
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void Delete(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            Customers customer = (Customers) customerTV.getSelectionModel().getSelectedItem();
            DBCustomer.deleteCustomer(customer);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Customer deleted");
            alert.setContentText("Customer deleted");
            Optional<ButtonType> result = alert.showAndWait();
            customerTV.setItems(DBCustomer.getAllCustomers());
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nothing selected or unable to delete");
            alert.setContentText("Nothing selected or unable to delete");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }
}
