package Server;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.*;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

public class Server extends Thread {
    int portaTCP = 1144;
    int portaUDP = 1145;



    public static void main(String[] args) {
        Server servidor = new Server();
        servidor.udp();
        servidor.tcp();
    }

    /**
     * Inicio do Server
     */
    public Server() {
        try {
            System.out.println("Servidor ativo");
            System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());

            System.out.println("Porta TCP: " + portaTCP);
            System.out.println("Porta UDP: " + portaUDP);
            Controller bd = Controller.getInstance();
            if (bd.setField()){
                System.out.println("Controller leu os arquivos de usuário");
                System.out.println( bd.getClientes().toString());
            }
            else{
                System.out.println("sem arquivos lidos");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo de execução das Thread UDP
     */
    public void udp() {
        new Thread(() -> {
            Controller bd = Controller.getInstance();

            try {
                DatagramSocket socket = new DatagramSocket(portaUDP);
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

                while (true) {
                    socket.receive(packet);

                    String jose = new String(packet.getData(), 0, packet.getLength());
                    String[] partes = jose.split(",");

                    Medicao temp = new Medicao(partes[0],partes[1],partes[2],partes[3],partes[4],partes[5]);

                    System.out.println(temp.getConsumototal());
                    if ( bd.getClientes().get(temp.getCodigo()) == null){
                        System.out.println("Cliente não cadastrado");
                    }
                    else{
                        bd.addLeitura(temp);
                        if (bd.checkConsumo(temp.getZona(),temp.getCodigo())){
                            System.out.println("Cliente ID: "+temp.getCodigo()+"Bateu a meta de consumo");
                            //enviarEmailAlerta(temp.getZona(),temp.getCodigo());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Metodo de execução das Thread TCP
     */
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

    /**
     * Classe Cliente para ser chamada como uma Thread e ser executada como a mesma
     */
    private class Cliente extends Thread {

        private Socket socket;

        public Cliente(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            Controller bd = Controller.getInstance();

            try {//lembrando que o padrão de entrada de metas é: " Pedido, id, zona, meta"

                String obj = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
                String[] spitonhim = obj.split(",");
                System.out.println(spitonhim[0]);
                if (spitonhim[0].equals("consulta")) {

                    PrintWriter printa = new PrintWriter(socket.getOutputStream(),true);
                    System.out.println(bd.consultarConsumo(Integer.parseInt(spitonhim[1]), Integer.parseInt(spitonhim[2])));
                    printa.println(bd.consultarConsumo(Integer.parseInt(spitonhim[1]), Integer.parseInt(spitonhim[2])));

                }
                else if (spitonhim[0].equals("meta")){
                    bd.setMeta(Integer.parseInt(spitonhim[1]),Integer.parseInt(spitonhim[2]),Integer.parseInt(spitonhim[3]));
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Envia um e-mail de alerta para o Usuário da Zona e ID passados como parâmetro
     * @param zona  Zona do Usuário
     * @param codigo    Id do usuário
     */
	public void enviarEmailAlerta(int zona,int codigo) {//codigo do cliente que precisa ser notificado

        Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("dora@outlook.com", "Batatinha666");
                    }
                });

        /** Ativa Debug para sessão */
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("dora@outlook.com")); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse("seuamigo@gmail.com, seucolega@hotmail.com, seuparente@yahoo.com.br");

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Aviso de Consumo de água");//Assunto
            message.setText("Você bateu sua meta de consumo, cuidado!");
            /**Método para enviar a mensagem criada*/
            Transport.send(message);

            System.out.println("Feito!!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}