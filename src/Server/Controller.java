package Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;

public class Controller {

    private Server server;
    private HashMap<Integer, HashMap<Integer,Cliente>> clientes;

    public Controller(Server server){
        this.server = server;
        clientes = new HashMap<Integer, HashMap<Integer, Cliente>>();
        this.setField();
    }

    public void setField(){
        String arquivo = "DB.txt";

        //fazer recuperação e checagem de clientes aqui via documento de texto!
        //
    }

    public void addLeitura(Medicao med){

        HashMap<Integer, Cliente> temp = clientes.get(med.getZona());
        temp.get(med.getCodigo()).getMedicoes().put(med.getData(),med.getTime());
    }

    public HashMap<Integer, HashMap<Integer, Cliente>> getClientes() {
        return clientes;
    }
}
