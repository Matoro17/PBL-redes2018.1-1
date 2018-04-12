package Client;

import Sensor.Sensorpk.Sensor;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Clientcontrol extends Application {
    private Stage primaryStage;

    @FXML
    private Button login;
    @FXML
    private TextField clientport;
    @FXML
    private TextField clientip;
    @FXML
    private TextField clientcode;




    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("CliLogin.fxml"));
        primaryStage.setTitle("Cliente");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();



        login.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientInterface.fxml"));
                Client client = loader.getController();
                client.criarConexao(clientip.getText(),Integer.parseInt(clientport.getText()));
                client.setCodigo(clientcode.getText());
                try {
                    primaryStage.setScene(new Scene(loader.load()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
