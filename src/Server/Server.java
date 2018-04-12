package Server;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    int portaTCP = 12345;
    int portaUDP = 22222;
    private Medicao data;
    public static Controller control;



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server novo = new Server();
        new Thread(new Server(new Socket(),new Controller(novo))).start();
    }

    public Server() {
        try {
            System.out.println("Servidor ativo");
            System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());

            String conf[] = Files.lines(Paths.get("conf.txt")).findFirst().get().split(";");
            portaTCP = Integer.parseInt(conf[0]);
            portaUDP = Integer.parseInt(conf[1]);

            System.out.println("Porta TCP: " + portaTCP);
            System.out.println("Porta UDP: " + portaUDP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void udp() {
        new Thread(() -> {
            Controller bd = Controller.getInstance();

            try {
                DatagramSocket socket = new DatagramSocket(portaUDP);
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

                while (true) {
                    socket.receive(packet);

                    try {
                        Object obj = new ObjectInputStream(new ByteArrayInputStream(packet.getData())).readObject();

                        if (obj instanceof Registro)
                            bd.salvarRegistro((Registro) obj);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void tcp() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(portaTCP);

                while (true)
                    new Cliente(serverSocket.accept()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private class Cliente extends Thread {

        private Socket socket;

        public Cliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BancoDados bd = BancoDados.getInstance();

            try {
                Object obj = new ObjectInputStream(socket.getInputStream()).readObject();

                if (obj instanceof Consulta)
                    new ObjectOutputStream(socket.getOutputStream()).writeObject(bd.consultarConsumo((Consulta) obj));

                if (obj instanceof Agendamento)
                    bd.salvarAgendamento((Agendamento) obj);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void smtp() {
        new Thread(() -> {
            BancoDados bd = BancoDados.getInstance();

            try {
                while (true) {
                    Thread.sleep(60000);

                    bd.listaEmail().forEach(end -> {
                        try {
                            Runtime.getRuntime().exec(
                                    String.format("python3 %s %s %s %s","email.py", end, "SMCRA", "Limite de consumo atingido"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }




    @Override
    public void run(){
        try{
           UDPServer(portaUDP,128);
           DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

           while(true){
               ServerSocket s = new ServerSocket(portaTCP);
               System.out.println("Esperando conexão de cliente...");
               conexaoClient = s.accept();
               System.out.println("conected!");

               BufferedReader in = new BufferedReader(new InputStreamReader(conexaoClient.getInputStream()));
               PrintWriter out = new PrintWriter(conexaoClient.getOutputStream(), true);
               System.out.println(in.readLine());
               /*if(in.readLine().equals("relatorio")){
                   out.println("tome um relatorio");
               }*/
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
       } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean checkConsumo(int zona, int codigo){
        HashMap<Integer, Cliente> clientes = control.getClientes().get(zona);
        Cliente cli = clientes.get(codigo);
        return cli.check();
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