package Server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Controller implements Serializable {

    private Server server;
    private static HashMap<Integer, HashMap<Integer, Cliente>> clientes;
    private static final String PATH = "C:\\Users\\Gabriel\\Documents\\GitHub\\PBL-redes2018.1-1\\DB.txt";

    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Controller(Server server) throws IOException, ClassNotFoundException {
        this.server = server;
        clientes = new HashMap<Integer, HashMap<Integer, Cliente>>();
        try {
            setField();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
        Seta a base de dados
     */
    public void setField() throws IOException, ClassNotFoundException {
        try {
            clientes = readFile();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /*
        Checa se o cliente atingiu o consumo
     */
    public boolean checkConsumo(int zona, int codigo) {
        HashMap<Integer, Cliente> clientes = getClientes().get(zona);
        Cliente cli = clientes.get(codigo);
        return cli.check();
    }

    public void setMeta(int id, int zone, int meta) {
        clientes.get(zone).get(id).setLimite(meta);
    }


    //Savar base de dados
    public static void saveFile(HashMap<Integer, HashMap<Integer, Cliente>> users) throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(PATH))) {
            os.writeObject(users);
        }
    }

    /*
        Ler arquivo da base de dados
     */
    public static HashMap<Integer, HashMap<Integer, Cliente>> readFile() throws ClassNotFoundException, IOException {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(PATH))) {
            return (HashMap<Integer, HashMap<Integer, Cliente>>) is.readObject();
        }
    }

    public void addLeitura(Medicao med) {

        HashMap<Integer, Cliente> temp = clientes.get(med.getZona());
        temp.get(med.getCodigo()).getMedicoes().put(med.getData(), med.getTime());
    }

    public String consultarConsumo(int id, int zona) {
        HashMap medicoescliente = clientes.get(zona).get(id).getMedicoes();
        return medicoescliente.toString();

    }

    public HashMap<Integer, HashMap<Integer, Cliente>> getClientes() {
        return clientes;
    }

    //Main para adição de mais usuários
/*
    public static void main(String... args) throws Exception{
        // Populate and save our HashMap
        clientes = new HashMap<Integer, HashMap<Integer,Cliente>>();
        clientes.put(1,new HashMap<Integer, Cliente>());
        clientes.get(1).put(1,new Cliente(1,1,"Gabriel","gabrielsilvadeazevedo@gmail.com"));
        clientes.put(2,new HashMap<Integer, Cliente>());
        clientes.get(2).put(2,new Cliente(2,2,"Amancio","amancinho@outlook.com"));

        clientes.put(3,new HashMap<Integer, Cliente>());
        clientes.get(3).put(3,new Cliente(3,3,"Irineu","chibatadejeova@gmail.com"));
        saveFile(clientes);

        // Read our HashMap back into memory and print it out
        HashMap<Integer, HashMap<Integer,Cliente>> restored = readFile();

        System.out.println(restored);
    }*/

}