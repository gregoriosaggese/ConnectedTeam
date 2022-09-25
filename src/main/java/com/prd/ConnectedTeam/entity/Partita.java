package com.prd.ConnectedTeam.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Partita {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Timestamp timestamp;
    @NotNull
    private String gioco;
    private boolean vinta;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Punteggio> array;

    public Partita(){
        array=new ArrayList<Punteggio>();
    }

    public Partita(Timestamp timestamp, boolean vinta) {
        this.timestamp = timestamp;
        this.vinta = vinta;
        array= new ArrayList<>();
    }

    public Partita(Timestamp timestamp) {
        this.timestamp = timestamp;
        array= new ArrayList<>();
    }

    public Partita(Timestamp timestamp, String gioco) {
        this.timestamp = timestamp;
        array= new ArrayList<>();
        this.gioco=gioco;
    }

    public Partita(Timestamp timestamp, boolean vinta, ArrayList<Punteggio> array) {
        this.timestamp = timestamp;
        this.vinta = vinta;
        this.array = array;
    }

    public void removePunteggio(Account account){
        for(int i=0 ; i<array.size();i++){
            if(array.get(i).getAccount().equals(account)){
                array.remove(i);
            }
        }
    }

    public void removePunteggio(long accountid){
        for(int i=0 ; i<array.size();i++){
            if(array.get(i).getAccount().getId()==accountid){
                array.remove(i);
            }
        }
    }

    public void removePunteggio(String accountName){
        for(int i=0 ; i<array.size();i++){
            if(array.get(i).getAccount().getNome().equals(accountName)){
                array.remove(i);
            }
        }
    }

    public void addPunteggio(Punteggio punteggio){
        array.add(punteggio);
    }

    public List<Punteggio> getArray() {
        return array;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGioco() {
        return gioco;
    }

    public void setGioco(String gioco) {
        this.gioco = gioco;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isVinta() {
        return vinta;
    }

    public void setVinta(boolean vinta) {
        this.vinta = vinta;
    }

    @Override
    public String toString() {
        return "Partita{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", gioco='" + gioco + '\'' +
                ", vinta=" + vinta +
                ", array=" + array +
                '}';
    }
}
