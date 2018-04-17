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
import javafx.scene.control.TextArea;
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
    private TextArea info;
    @FXML
    private Button checkconsumo;
    @FXML
    private TextField clienteip;
    @FXML
    private TextField porta;
    @FXML
    private TextField clientid;
    @FXML
    private TextField clientzone;
    @FXML
    private Button sendmeta;
    @FXML
    private TextField meta;


    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private Socket conexao;
    private String codigo;




    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sendmeta.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Socket conexao = new Socket(clienteip.getText(), Integer.parseInt(porta.getText()));
                    PrintWriter out = new PrintWriter(conexao.getOutputStream(), true);
                    out.write("meta,"+clientid.getText()+","+clientzone.getText()+","+meta.getText());
                    info.appendText("\nMeta enviada\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        checkconsumo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Socket conexao = new Socket(clienteip.getText(), Integer.parseInt(porta.getText()));
                    PrintWriter out = new PrintWriter(conexao.getOutputStream(), true);

                    out.println("consulta,"+clientid.getText()+","+clientzone.getText());

                    String obj = new BufferedReader(new InputStreamReader(conexao.getInputStream())).readLine();
                    conexao.close();
                    String[] dividida = obj.split(";");
                    String vai = "";
                    for (int i = 0; i<dividida.length ; i++){
                        vai += dividida[i] + "\n";
                    }
                    info.setText(vai);



                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });

    }
}
