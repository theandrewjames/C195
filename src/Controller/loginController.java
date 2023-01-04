package Controller;

import Helper.DBCustomer;
import Helper.DBUsers;
import Helper.DBappt;
import Model.Appointments;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controls login screen. Lambda below
 */
public class loginController implements Initializable {
    public Button loginButton;
    public Label locLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Button exitButton;
    public TextField usernameTF;
    public TextField passwordTF;
    public Label warningLabel;

    /**
     * Lambda to eliminate exit method at bottom of this file.
     * French language features below
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitButton.setOnAction(e ->System.exit(0));
        //Locale.setDefault(new Locale("fr")); To test french feature
        ResourceBundle rb = ResourceBundle.getBundle("Language/Login", Locale.getDefault());
        Locale locale = Locale.getDefault();
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        loginButton.setText(rb.getString("login"));
        exitButton.setText(rb.getString("exit"));
        locLabel.setText(rb.getString("location") + ": " + ZoneId.systemDefault());
    }

    /**
     * Tries username/password and saves attempt to file. If successful goes to next screen, else
     * error appears. Also checks if appt in next 15 minutes
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    public  void toDirectory(ActionEvent actionEvent) throws IOException, SQLException {
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        FileWriter write = new FileWriter("login_activity.txt", true);
        PrintWriter print = new PrintWriter(write);
        try {
            if(DBUsers.userLogin(username, password) == true)  {
                print.print("SUCCESSFUL login for " + username + " at " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
                print.close();
            }
            else {
                print.print("UNSUCCESSFUL login for " + username + " at " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
                print.close();
            }

            ObservableList<Appointments> appts = DBappt.getAllAppts();
            LocalDateTime current = LocalDateTime.now();
            LocalDateTime currentPlus15 = LocalDateTime.now().plusMinutes(15);
            LocalDateTime  ldt15 = currentPlus15.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime  ldtcurrent = current.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

            for(Appointments appt : appts) {
                if(appt.getStartTime().isBefore(ldt15) && appt.getStartTime().isAfter(ldtcurrent)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setContentText("Upcoming appt id: " + appt.getApptID() + " Start time: " + appt.getStartTime());
                    Optional<ButtonType> result = alert.showAndWait();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/Directory.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No appts in next 15 minutes");
            alert.setContentText("No appts in next 15 minutes");
            Optional<ButtonType> result = alert.showAndWait();
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
    //Method eliminated by lambda in initialize.
    /*public void ExitWindow(ActionEvent actionEvent) {
            System.exit(0);
    }*/
}
