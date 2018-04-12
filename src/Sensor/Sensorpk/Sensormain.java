package Sensor.Sensorpk;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Sensormain extends Application {

    Scene login,data;
    private Stage primaryStage;

    @FXML
    public Button enviar;
    @FXML
    public TextField ip;
    @FXML
    public TextField port;
    @FXML
    public TextField clientcode;
    @FXML
    public TextField clientzone;

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Sensor");
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


        enviar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("sensorxml.fxml"));
                Sensor sensor = loader.getController();
                sensor.setup(ip.getText(),Integer.parseInt(port.getText()),Integer.parseInt(clientcode.getText()),Integer.parseInt(clientzone.getText()));

            }

        });

    }

    public void changeStage(String fxml) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

}