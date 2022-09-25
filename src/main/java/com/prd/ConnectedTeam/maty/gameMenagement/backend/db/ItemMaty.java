package com.prd.ConnectedTeam.maty.gameMenagement.backend.db;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.util.Arrays;

@Entity
public class ItemMaty {

    private static int max=1;
    @Id //@GeneratedValue
    private int id;
    private String indizi[];
    private String parola;
    private String operazione;

    public String getOperazione() {
        return operazione;
    }

    public void setOperazione(String operazione) {
        this.operazione = operazione;
    }

    public ItemMaty(){}

    public ItemMaty(String parola,String operazione) {
        this.indizi = new String[4];
        this.parola = parola;
        this.operazione = operazione;
    }

    @PrePersist
    private void settaId(){
        synchronized (ItemMaty.class) {
            id = max;
            max++;
        }
    }

    //TODO: Vedi che qua sta fatto con gli indici non con il set dell array
    public void addIndizio(int i, String indizio){
        if(i>=0 && i<4){
            indizi[i]=indizio;
        }
    }

    public String getIndizio(int i){
        return indizi[i];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", indizi=" + Arrays.toString(indizi) +
                ", parola='" + parola + '\'' +
                '}';
    }
}
