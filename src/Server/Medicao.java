package Server;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.util.HashMap;

public class Medicao extends HashMap<String, Medicao> implements Serializable {
    private String data,hora;
    private int codigo,consumoHora, zona,consumototal;

    public Medicao(String cod,String zone, String date,String hora,String consumoHora,String consumototal){

        this.codigo = Integer.parseInt(cod);
        this.consumoHora = Integer.parseInt(consumoHora);
        this.consumototal = Integer.parseInt(consumototal);
        this.zona = Integer.parseInt(zone);
        this.data = date;
        this.hora = hora;


    }

    public boolean mesAno(String mesano){
        String[] str = mesano.split("/");
        if (mesano.equals(str[1]+str[2])){
            return true;
        }
        else{
            return false;
        }

    }

    public int getConsumototal() {
        return consumototal;
    }


    public String getData() {
        return data;
    }

    public int getConsumoHora() {
        return consumoHora;
    }

    public String getTime() {
        return hora;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
