package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Server extends Thread {
    private Socket conexaoClient;
    private DatagramSocket conexaoSensor;
    private byte[] buffer;
    int porta = 12345;
    private Medicao data;
    public static Controller control;


    public static void main(String[] args) {


        new Thread(new Server()).start();

    }

    public Server(Socket sock, Controller c) throws IOException, ClassNotFoundException {
        control = new Controller(this);
        conexaoClient = sock;
        control = c;
    }
    public Server(){

    }

    public void UDPServer(int port, int buffer) throws IOException {
        this.conexaoSensor = new DatagramSocket(port);
        this.buffer = new byte[buffer];
    }


    @Override
    public void run(){
        try{
           //ObjectInputStream entrada = new ObjectInputStream(conexaoClient.getInputStream());
           //ObjectOutputStream saida = new ObjectOutputStream(conexaoClient.getOutputStream());
           UDPServer(porta,128);
           DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
           //String a = entrada.readUTF();
           while(true){
               conexaoSensor.receive(packet);
               System.out.println(new String(packet.getData(), 0, packet.getLength()));
               String jose = new String(packet.getData(), 0, packet.getLength());
               String[] partes = jose.split(",");


               Medicao temp = new Medicao(partes[0],partes[1],partes[2],partes[3],partes[4],partes[5]);

               System.out.println(temp.getConsumoHora());
               if ( control.getClientes().get(temp.getCodigo()) == null){
                   System.out.println("Cliente não cadastrado");
               }
               else{
                   control.addLeitura(temp);
               }

               if (checkConsumo(temp.getZona(),temp.getCodigo())){
                   //enviarEmailAlerta(temp.getZona(),temp.getCodigo());
               }
               System.out.println(temp.getConsumoHora());
           }
       }catch (IOException e){

       }
    }

    public boolean checkConsumo(int zona, int codigo){
        HashMap<Integer, Cliente> clientes = control.getClientes().get(zona);
        Cliente cli = clientes.get(codigo);
        return cli.check();
    }

    public void criarConexao(Controller c, int port) throws ClassNotFoundException {
        try{
            ServerSocket s = new ServerSocket(port);
            while(true){
                System.out.println("Esperando conexão...");
                conexaoClient = s.accept();
                System.out.println("conected!");
                Thread t = new Server(conexaoClient,c);
                t.start();
                
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException: " + e);

        }
    }

    /*
	public void enviarEmailAlerta(int zona,int codigo){//codigo do cliente que precisa ser notificado

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("gabrielsilvadeazevedo@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("gabrielsilvadeazevedo@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}*/
}