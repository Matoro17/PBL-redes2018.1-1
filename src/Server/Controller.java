package Server;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Controller implements Serializable {

    private Server server;
    private static HashMap<Integer, HashMap<Integer,Cliente>> clientes;
    private static final String PATH = "C:\\Users\\Gabriel\\Documents\\GitHub\\PBL-redes2018.1-1\\DB.txt";


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

    public void setField() throws IOException, ClassNotFoundException {
        try {
            clientes = readFile();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void saveFile(HashMap<Integer, HashMap<Integer,Cliente>> users) throws IOException{
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(PATH))) {
            os.writeObject(users);
        }
    }

    public static HashMap<Integer, HashMap<Integer,Cliente>> readFile() throws ClassNotFoundException, IOException {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(PATH))) {
            return (HashMap<Integer, HashMap<Integer,Cliente>>) is.readObject();
        }
    }

    //Main para adição de mais usuários
    /*
    public static void main(String... args) throws Exception{
        // Populate and save our HashMap
        clientes = new HashMap<Integer, HashMap<Integer,Cliente>>();
        clientes.put(1,new HashMap<Integer, Cliente>());
        clientes.get(1).put(1,new Cliente(1,1,"josé"));
        clientes.put(2,new HashMap<Integer, Cliente>());
        clientes.get(2).put(2,new Cliente(2,2,"Amancio"));

        clientes.put(3,new HashMap<Integer, Cliente>());
        clientes.get(3).put(3,new Cliente(3,3,"Irineu"));
        saveFile(clientes);

        // Read our HashMap back into memory and print it out
        HashMap<Integer, HashMap<Integer,Cliente>> restored = readFile();

        System.out.println(restored);
    }*/

    public void addLeitura(Medicao med){

        HashMap<Integer, Cliente> temp = clientes.get(med.getZona());
        temp.get(med.getCodigo()).getMedicoes().put(med.getData(),med.getTime());
    }

    public HashMap<Integer, HashMap<Integer, Cliente>> getClientes() {
        return clientes;
    }
}
