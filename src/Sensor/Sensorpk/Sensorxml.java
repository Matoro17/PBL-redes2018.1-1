package Sensor.Sensorpk;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Sensorxml implements Initializable {

    @FXML
    public Button mais;
    @FXML
    public Button menos;
    @FXML
    public Label labo;

    public Integer vazao = 0, vazaoTotalHora = 0,valoranterior = vazao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       new Thread(() -> {
           System.out.println(vazaoTotalHora);
            while (true) {


                if(vazao > 0){
                    vazaoTotalHora = vazaoTotalHora + vazao;
                    System.out.println(vazaoTotalHora);
                    Platform.runLater(() -> {
                        labo.setText(Integer.toString(vazaoTotalHora));
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }). start();

        mais.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                vazao = vazao + 1;
            }

        });

        menos.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (vazao > 0) {
                    vazao = vazao - 1;
                }
            }

        });
    }
}
