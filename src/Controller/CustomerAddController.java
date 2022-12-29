package Controller;

import Helper.DBCustomer;
import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerAddController implements Initializable {


    public TextField nameTF;
    public TextField addressTF;
    public TextField postalCodeTF;
    public TextField phoneTF;
    public TextField idTF;
    public ComboBox countryCB;
    public ComboBox divisionCB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countries = FXCollections.observableArrayList();
        countries.addAll("U.S", "UK", "Canada");
        countryCB.setItems(countries);
        int nextId = 0;
        try {
            nextId = (DBCustomer.getNextCusId()) + 1;
            idTF.setText(String.valueOf(nextId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void Back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/CustomerView.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void AddCustomerSave(ActionEvent actionEvent) throws IOException {
        String name = nameTF.getText();
        String address = addressTF.getText();
        String phone = phoneTF.getText();
        Integer id =  Integer.parseInt(idTF.getText());
        String postalCode = postalCodeTF.getText();
        String country = (String) countryCB.getSelectionModel().getSelectedItem();
        String division = (String) divisionCB.getSelectionModel().getSelectedItem();
        Customers customer = new Customers(id, name, phone, address, postalCode, division, country);
    }

    public void loadDivision(ActionEvent actionEvent) throws SQLException {
        String country = (String) countryCB.getSelectionModel().getSelectedItem();
        divisionCB.setItems(DBCustomer.getDivisions(country));

    }

}
