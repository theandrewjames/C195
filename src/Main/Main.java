package Main;

import Helper.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
        primaryStage.setTitle("Appointment scheduler");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
    }


    public static void main(String[] args) {

        Database.openConnection();
        //Database.closeConnection();
        launch(args);
    }
}
