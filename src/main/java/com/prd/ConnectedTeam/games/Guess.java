package com.prd.ConnectedTeam.games;


import com.prd.ConnectedTeam.gamesRules.Game;
import org.springframework.stereotype.Component;

@Component
public class Guess implements Game {

    @Override
    public String getNomeGioco() {
        return "Guess";
    }

    @Override
    public String getPathName() {
        return "guess";
    }

    @Override
    public String getDescrizioneGioco() {
        return "Come, Dove, Quando, Perché... Quattro affermazioni, una parola!";
    }

    @Override
    public String getDescrizioneLungaGioco() {
        return "Obbiettivo del gioco è quello di individuare, date quattro affermazioni, la parola da esse descritta." +
                " La quartina definisce il “come”, il “dove”, il “quando” e il “perché”." +
                " Ogni 30 secondi compare un indizio della quartina." +
                " Quando tutti i suggerimenti sono comparsi, se non si è trovata una soluzione la partita termina senza vincitori." +
                " Tutti possono suggerire la soluzione e tutti devono essere d’accordo sulla soluzione finale!"+
                " Meno indizi vengono usati e maggiore sarà il punteggio ottenuto!."+
                " I giocatori hanno 2 minuti per indovinare la parola!";
    }

    @Override
    public String getAgeRange() {
        return "Per 16-20 Anni";
    }
}
