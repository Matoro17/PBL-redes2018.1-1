package Sensor.Sensorpk;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Sensormain extends Application {

    Scene login,data;

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sensorxml.fxml"));
        primaryStage.setTitle("Sensor");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
