package Sensor.Sensorpk;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Sensor implements Initializable {

    @FXML
    public Button mais;
    @FXML
    public Button menos;
    @FXML
    public Label labo;
    @FXML
    public Label dis;
    @FXML
    public TextField ipsensor;
    @FXML
    public TextField portasensor;
    @FXML
    public TextField zonasensor;
    @FXML
    public TextField idsensor;
    @FXML
    public Button start;


    public Integer vazao = 0, vazaoTotalHora = 0,totalvazao = 0;
    public LocalDateTime data;

    public String local = "192.168.1.8";
    public int porta = 1145;
    private int codigo = 1, zona = 1;

    public void setup(String local,int porta,int codigo,int zona){
        this.local = local;
        this.porta = porta;
        this.codigo = codigo;
        this.zona = zona;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start.setOnMouseClicked(new EventHandler<MouseEvent>() {


            @Override
            public void handle(MouseEvent event) {
                porta = Integer.parseInt(portasensor.getText());
                local = ipsensor.getText();
                codigo = Integer.parseInt(idsensor.getText());
                zona = Integer.parseInt(zonasensor.getText());

                new Thread(() -> {
                    int count = 0;
                    try {
                        DatagramSocket socket = new DatagramSocket();
                        System.out.println(vazaoTotalHora);
                        while (true) {
                            if (vazao > 0) {
                                vazaoTotalHora = vazaoTotalHora + vazao;
                                System.out.println(vazaoTotalHora);
                                Platform.runLater(() -> {
                                    labo.setText(Integer.toString(vazaoTotalHora)+"cm³");
                                });
                            }
                            Platform.runLater(() -> {
                                dis.setText(Integer.toString(vazao)+ "cm³/s");
                            });
                            Thread.sleep(1000);
                            count++;
                            if (count >= 10){
                                totalvazao = vazaoTotalHora + totalvazao;
                                vazaoTotalHora = 0;
                                System.out.println("tentando enviar");
                                byte[] dados = String.format("%d,%d,%s,%d,%d",codigo,zona, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy,hh:mm")),vazaoTotalHora,totalvazao).getBytes();
                                socket.send(new DatagramPacket(dados,dados.length,InetAddress.getByName(local),porta));
                                count=0;
                            }

                        }
                    } catch (IOException | InterruptedException e) {

                    }

                }). start();
            }
        });


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
