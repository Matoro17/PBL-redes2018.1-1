package Client;

import Sensor.Sensorpk.Sensor;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.applet.Main;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class Clientcontrol implements Initializable {


    @FXML
    private Label info;
    @FXML
    private Button checkconsumo;

    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private Socket conexao;
    private String codigo;

    BufferedReader in;
    PrintWriter out;

    private String ip = "localhost";
    private int porta = 12345;

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            conexao = new Socket(ip, porta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                 PrintWriter out = new PrintWriter(conexao.getOutputStream(), true)) {

            } catch (IOException e) {
                e.printStackTrace();
        }



        checkconsumo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Socket conexao = new Socket(ip, porta);
                    BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                    PrintWriter out = new PrintWriter(conexao.getOutputStream(), true);

                    out.println("relatorio");
                    /*String str = in.readLine();
                    System.out.println("recebeu misera");
                    info.setText(str);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
