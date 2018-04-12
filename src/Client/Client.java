package Client;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Scanner;
 
public class Client implements Initializable, Serializable {

    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private Socket conexao;
    private String codigo;



    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String criarConexao(String ip, int port){
        try {
            conexao = new Socket(ip,port);
            saida = new ObjectOutputStream(conexao.getOutputStream());
            entrada = new ObjectInputStream(conexao.getInputStream());
            return "Sucesso";
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}