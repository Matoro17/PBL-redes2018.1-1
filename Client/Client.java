import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
 
/**
 *
 * @author Emerson Ribeiro de Mello
 */
public class Client {
 
    public void iniciar(String endereco, int porta) {
        ObjectOutputStream saida;
        ObjectInputStream entrada;
        Socket conexao;
        Scanner ler;
        String mensagem = "";
        try {
            ler = new Scanner(System.in);
            conexao = new Socket(endereco, porta);
            System.out.println("Conectado ao servidor " + endereco + ", na porta: " + porta);
            System.out.println("Digite: FIM para encerrar a conexao");
 
            // ligando as conexoes de saida e de entrada
            saida = new ObjectOutputStream(conexao.getOutputStream());
            saida.flush();
            entrada = new ObjectInputStream(conexao.getInputStream());
 
            //lendo a mensagem enviada pelo servidor
            mensagem = (String) entrada.readObject();
            System.out.println("Servidor>> "+mensagem);
 
            
                System.out.print("..: ");
                
                do{ 
                    mensagem = ler.nextLine();
                    saida.writeUTF(mensagem);
                    saida.flush();
                    
                }while(!mensagem.equals("FIM"));
                
               
                //lendo a mensagem enviada pelo servidor
                mensagem = (String) entrada.readObject();
                System.out.println("Servidor>> "+mensagem);
            
 
            saida.close();
            entrada.close();
            conexao.close();
 			ler.close();
        } catch (Exception e) {
            System.err.println("erro: " + e.toString());
        }
 
    }
 
    public static void main(String[] args) {
       
 
        Client c = new Client();
        c.iniciar("localhost", 47579);
    }
}