package com.prd.ConnectedTeam.guess.gamesMenagemet.backend.listeners;

import com.prd.ConnectedTeam.entity.Account;
import com.vaadin.flow.component.UI;

public interface BroadcastListener {
    void countUser(UI ui, String nome);
    void startGame1();
    void receiveIndizio(String message);
    void countDown(String n);
    void addUsers(UI ui,int i);
    void parolaVotata();
    void partititaVincente(String parola,int punteggio);
    void partititanonVincente();
    void browserIsLeavingCalled(Account account);
}
