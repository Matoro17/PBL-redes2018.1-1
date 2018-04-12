package Client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientStart extends Application{
    private Stage primaryStage;
    private Clientcontrol cliente;
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
        primaryStage.setTitle("Cliente");
        Parent root = FXMLLoader.load(getClass().getResource("Clientinterface.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
   /*
    @Override
    public void initialize(URL location, ResourceBundle resources) {



        login.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle (MouseEvent event){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientInterface.fxml"));
                primaryStage.setScene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
            cliente.criarConexao(clientip.getText(), Integer.parseInt(clientport.getText()));
            cliente.setCodigo(clientcode.getText());

        }

            });
    }*/
}
