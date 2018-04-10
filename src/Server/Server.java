/*
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Server {
 
    public void iniciar(int porta) {
        ObjectOutputStream saida;
        ObjectInputStream entrada;
        boolean sair = false;
        String mensagem = "";
 
        try {
            // criando um socket para ouvir na porta e com uma fila de tamanho 10
            ServerSocket Server = new ServerSocket(porta, 10);
            Socket conexao;
            while (!sair) {
                System.out.println("Ouvindo na porta: " + porta);
 
                //ficarah bloqueado aqui ate' alguem cliente se conectar
                conexao = Server.accept();
 
                System.out.println("Conexao estabelecida com: " + conexao.getInetAddress().getHostAddress());
 
                //obtendo os fluxos de entrada e de saida
                saida = new ObjectOutputStream(conexao.getOutputStream());
                entrada = new ObjectInputStream(conexao.getInputStream());
 
                //enviando a mensagem abaixo ao cliente
                saida.writeObject("Conexao estabelecida com sucesso...\n");
 
                do {//fica aqui ate' o cliente enviar a mensagem FIM
                    try {
                        //obtendo a mensagem enviada pelo cliente
                        mensagem = (String) entrada.readUTF();
                        System.out.println("Cliente>> " + mensagem);
                        	if(mensagem.equals("enviar email")) {
                        		System.out.println("tentou enviar email");
                        		enviarEmail("user","pass");
                        	}
                        saida.writeUTF(mensagem);
                    } catch (IOException iOException) {
                        System.err.println("erro: " + iOException.toString());
                    }
                } while (!mensagem.equals("FIM"));
                saida.writeUTF("FIM");
                System.out.println("Conexao encerrada pelo cliente");
                sair = true;
                saida.close();
                entrada.close();
                conexao.close();
                Server.close();
 
            }
 
        } catch (Exception e) {
            System.err.println("Erro: " + e.toString());
        }
    }
 
    public static void main(String[] args) {
        int porta = 47579;
 
 
        Server s = new Server();
        s.iniciar(porta);
    }
    
	public void enviarEmail(String username, String password){

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
		
	}
}*/