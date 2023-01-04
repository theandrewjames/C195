package Controller;

import Helper.DBCustomer;
import Helper.DBappt;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Updates customer on page
 */
public class CustomerUpdateController implements Initializable {
    public ComboBox divisionCB;
    public ComboBox countryCB;
    public TextField idTF;
    public TextField nameTF;
    public TextField addressTF;
    public TextField postalCodeTF;
    public TextField phoneTF;

    /**
     * Returns user to previous screen
     * @param actionEvent
     * @throws IOException
     */
    public void Back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/customerView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Grabs inputted values and saves customer
     * @param actionEvent
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public boolean UpdateCustomerSave(ActionEvent actionEvent) throws SQLException, IOException {
        String name = nameTF.getText();
        String address = addressTF.getText();
        String phone = phoneTF.getText();
        Integer id =  Integer.parseInt(idTF.getText());
        String postalCode = postalCodeTF.getText();
        String country = (String) countryCB.getSelectionModel().getSelectedItem();
        String division = (String) divisionCB.getSelectionModel().getSelectedItem();
        Customers customer = new Customers(id, name, phone, address, postalCode, division, country);
        if (DBCustomer.updateCustomer(customer) == 1) {
            Parent root = FXMLLoader.load(getClass().getResource("/View/customerView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Database error");
            alert.setContentText("Unable to add customer. Please try again");
            Optional<ButtonType> result = alert.showAndWait();
            return false;
        }
    }

    /**
     * upon selecting a country this loads the appropriate divisions
     * @param actionEvent
     * @throws SQLException
     */
    public void loadDivision(ActionEvent actionEvent) throws SQLException {
        String country = (String) countryCB.getSelectionModel().getSelectedItem();
        divisionCB.setItems(DBCustomer.getDivisions(country));
    }

    /**
     * Popules countries combo boxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countries = FXCollections.observableArrayList();
        countries.addAll("U.S", "UK", "Canada");
        countryCB.setItems(countries);
    }

    /**
     * Populates screen with info from selected customer
     * @param customer
     * @throws SQLException
     */
    public void loadCustomer(Customers customer) throws SQLException {
        idTF.setText(String.valueOf(customer.getCustomerId()));
        nameTF.setText(customer.getCustomerName());
        addressTF.setText(customer.getCustomerAddress());
        postalCodeTF.setText(customer.getPostalCode());
        phoneTF.setText(customer.getCustomerPhone());
        countryCB.setValue(customer.getCountry());
        String country = customer.getCountry();
        divisionCB.setItems(DBCustomer.getDivisions(country));
        divisionCB.setValue(customer.getDivison());

    }
}
