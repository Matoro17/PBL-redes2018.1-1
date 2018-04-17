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

    /**
     * Retorna uma instancia do controller para evitar multiplas instancias do mesmo
     * @return
     */
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

    /**
     * Usadao para quando o servidor é carregado para pegar o arquivo do banco de dados e deixá-lo carergado no sistema
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
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


    /**
     * Faz a checagem se o usuário atingiu seu consumo
     * @param zona
     * @param codigo
     * @return
     */
    public boolean checkConsumo(int zona, int codigo) {
        Controller control = new Controller().getInstance();
        HashMap<Integer, Cliente> clientes = control.getClientes().get(zona);
        Cliente cli = clientes.get(codigo);
        return cli.check();
    }

    /**
     * Insere a meta num cliente pelo seu ID, e zona
     * @param id
     * @param zone
     * @param meta
     */
    public void setMeta(int id, int zone, int meta) {
        clientes.get(zone).get(id).setLimite(meta);
    }


    /**
     * salva as alterações ou o arquivo carregado do programa no texto
     * @param users
     * @throws IOException
     */
    public static void saveFile(HashMap<Integer, HashMap<Integer, Cliente>> users) throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(PATH))) {
            os.writeObject(users);
        }
    }

    /**
     * Lê o arquivo do banco para recuperar os clientes e leituras
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static HashMap<Integer, HashMap<Integer, Cliente>> readFile() throws ClassNotFoundException, IOException {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(PATH))) {
            return (HashMap<Integer, HashMap<Integer, Cliente>>) is.readObject();
        }
    }

    /**
     * Adiciona uma leitura no banco de dados
     * @param med   Medição que por si só já conteem id e zona do seu usuário
     * @throws IOException
     */
    public void addLeitura(Medicao med) throws IOException {
            clientes.get(med.getZona()).get(med.getCodigo()).addMedicoes(med);
            saveFile(clientes);
    }

    /**
     * Metodo para mostrar o total de consumo de todos os dias de um determinado usuário em sua zona
     * @param id    ID do usuário
     * @param zona  Zona do usuário
     * @return  Retorna uma Palavra que conteem todas as totais letiruas de cada dia deste usuário, com virgulas para serem tratadas como quebra de linha pelo cliente
     */
    public String consultarConsumo(int id, int zona) {
        HashMap<String,HashMap<String, Medicao>> medicoescliente = clientes.get(zona).get(id).getMedicoes();
        String total = "para a zona "+zona+" e Usuario: "+id+";";
        for(Map.Entry<String, HashMap<String, Medicao>> entry : medicoescliente.entrySet()) {
            total += "Dia:"+(entry.getKey())+";";
            System.out.println(entry.getValue());//está retornando null

            for(Map.Entry<String, Medicao> entrou : entry.getValue().entrySet()){
                total += "Hora: " + entrou.getKey() + "\t";
                total += "Leitura: " + entrou.getValue().getConsumoHora() + "cm³\t";
                total += "Total: " + entrou.getValue().getConsumototal() + "cm³\t";
                total += ";";
            }
            total += ";";

        }
        return total;

    }

    /**
     * Retorna todos os clientes
     * @return
     */
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

// Main para adição de medicoes do usuário um
/*

    public static void main(String args[]) throws IOException {
        Controller control = new Controller().getInstance();
        Medicao medida = new Medicao("1","1","10/04/2018","13:00","0","0");
        control.addLeitura(medida);
        Medicao medida2 = new Medicao("1","1","16/04/2018","20:53","20","140");
        control.addLeitura(medida2);
        System.out.println(control.getClientes().get(1).get(1).getNome());
        System.out.println(control.consultarConsumo(1,1));
    }*/

}