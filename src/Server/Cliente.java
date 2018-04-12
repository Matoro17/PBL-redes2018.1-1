package Server;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Cliente implements Serializable {
    private int codigo,zona;
    private int limite,consumo;
    private HashMap<String,HashMap<String, Medicao>> medicoes;// <Data medicao, hash<Hora medicao, medicao daquela hora>>
    private String nome;


    public Cliente(int codigo,int zona, String nome){
        this.codigo = codigo;
        this.zona = zona;
        this.nome = nome;
    }

    public HashMap getMedicoes(){
        return medicoes;
    }

    public boolean check(){
        if (consumoMensal() >= limite){
            return true;
        }
        else{
            return false;
        }
    }
    public int consumoMensal(){
        int consumoTotal = 0;
        Collection cont = this.medicoes.values();
        Iterator i = cont.iterator();
        while (i.hasNext()){
            Medicao temp = (Medicao) i.next();
            consumoTotal = consumoTotal + temp.getConsumoHora();
        }
        return consumoTotal;
    }

    public int getConsumo() {
        return consumo;
    }

    public int getZona() {
        return zona;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getLimite() {
        return limite;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }
}
