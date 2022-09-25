package com.prd.ConnectedTeam.games;


import com.prd.ConnectedTeam.gamesRules.Game;
import org.springframework.stereotype.Component;

@Component
public class Maty implements Game {
    @Override
    public String getNomeGioco() {
        return "Maty";
    }

    @Override
    public String getDescrizioneGioco() {
        return "Addizione o Sottrazzione? Un numero tante possibili soluzioni!";
    }

    @Override
    public String getDescrizioneLungaGioco() {
        return "Obbiettivo del gioco è quello di individuare gli addendi o i sottraendi di un dato numero." +
                " Dopo cinque volte che si prova a dare la soluzione compare un aiuto" +
                " Quando tutti gli aiuti sono comparsi, se non si è trovata una soluzione la partita termina senza vincitori." +
                " Tutti possono inserire un numero alla volta da addizionare o sottrarre!"+
                " Meno aiuti vengono usati e maggiore sarà il punteggio ottenuto!."+
                " I giocatori hanno cinque minuti per trovare la soluzione!";
    }

    @Override
    public String getPathName() {
        return "maty";
    }

    @Override
    public String getAgeRange() {
        return "Per 8-11 Anni";
    }
}
