package Controller;

import Helper.DBCustomer;
import Helper.DBUsers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    public Button loginButton;
    public Label locLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Button exitButton;
    public TextField usernameTF;
    public TextField passwordTF;
    public Label warningLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Locale.setDefault(new Locale("fr")); To test french feature
        ResourceBundle rb = ResourceBundle.getBundle("Language/Login", Locale.getDefault());
        Locale locale = Locale.getDefault();
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        locLabel.setText(rb.getString("location") + ": " + ZoneId.systemDefault());
    }
    @FXML
    public  void toDirectory(ActionEvent actionEvent) throws IOException, SQLException {
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        try {
            DBUsers.userLogin(username, password);
            Parent root = FXMLLoader.load(getClass().getResource("/View/Directory.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch(SQLException e) {
            //Locale.setDefault(new Locale("fr")); To test french feature
            ResourceBundle rb = ResourceBundle.getBundle("Language/Login", Locale.getDefault());
            Locale locale = Locale.getDefault();
            warningLabel.setText(rb.getString("incorrect") + " " + rb.getString("username") + "/" + rb.getString("password"));
        }

    }
    public void ExitWindow(ActionEvent actionEvent) {
            System.exit(0);
    }
}
