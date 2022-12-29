package Controller;

import Helper.DBCustomer;
import Helper.DBappt;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerUpdateController implements Initializable {
    public ComboBox divisionCB;
    public ComboBox countryCB;
    public TextField idTF;
    public TextField nameTF;
    public TextField addressTF;
    public TextField postalCodeTF;
    public TextField phoneTF;

    public void Back(ActionEvent actionEvent) {
    }

    public void UpdateCustomerSave(ActionEvent actionEvent) {
    }

    public void loadDivision(ActionEvent actionEvent) throws SQLException {
        String country = (String) countryCB.getSelectionModel().getSelectedItem();
        divisionCB.setItems(DBCustomer.getDivisions(country));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countries = FXCollections.observableArrayList();
        countries.addAll("U.S", "UK", "Canada");
        countryCB.setItems(countries);
    }

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