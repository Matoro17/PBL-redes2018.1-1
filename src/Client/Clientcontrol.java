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
                    ObjectInputStream in = new ObjectInputStream(conexao.getInputStream());
                    //BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                    ObjectOutputStream out = new ObjectOutputStream(conexao.getOutputStream());
                    //PrintWriter out = new PrintWriter(conexao.getOutputStream(), true);

                    out.writeUTF("meta,"+clientid.getText()+","+clientzone.getText()+","+meta.getText());
                    String str = in.readUTF();
                    System.out.println("recebeu misera");
                    info.setText(str);
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
                    System.out.println("criou conexao");
                    //ObjectInputStream in = new ObjectInputStream(conexao.getInputStream());

                    //BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                    System.out.println("fez o in");
                    //ObjectOutputStream out = new ObjectOutputStream(conexao.getOutputStream());
                    PrintWriter out = new PrintWriter(conexao.getOutputStream(), true);
                    System.out.println("fez o out");
                    System.out.println("criou os in e out");
                    out.println("consulta,"+clientid.getText()+","+clientzone.getText());
                    System.out.println("tentou escrever para o server");
                    String obj = new BufferedReader(new InputStreamReader(conexao.getInputStream())).readLine();
                    //String str = in.readLine();
                    System.out.println("tentou ler oq o server respondeu");
                    info.setText("recebeu misera");
                    System.out.println("tentou colocar coisas na label de info");
                    info.setText(obj);
                    conexao.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });

    }
}
