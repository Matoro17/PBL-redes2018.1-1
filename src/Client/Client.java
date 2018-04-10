package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
 
public class Client{

    private ObjectOutputStream saida;
    private ObjectInputStream entrada;
    private Socket conexao;
    private String codigo;


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




 
    public static void main(String[] args) {

    }
}