package Server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Controller implements Serializable {

    private Server server;
    private static HashMap<Integer, HashMap<Integer, Cliente>> clientes;//\
    private static final String PATH = System.getProperty("user.dir") + "/" + "DB.txt";

    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
            clientes = new HashMap<Integer, HashMap<Integer, Cliente>>();
            try {
                instance.setField();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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
    public boolean setField() throws IOException, ClassNotFoundException {
        try {
            clientes = readFile();
            if (clientes != null){
                return true;
            }
            else{
                return false;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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

            clientes.get(med.getZona()).get(med.getCodigo()).addMedicoes(med);


    }

    public String consultarConsumo(int id, int zona) {
        HashMap<String,HashMap<String, Medicao>> medicoescliente = clientes.get(zona).get(id).getMedicoes();
        System.out.println(medicoescliente.toString());
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