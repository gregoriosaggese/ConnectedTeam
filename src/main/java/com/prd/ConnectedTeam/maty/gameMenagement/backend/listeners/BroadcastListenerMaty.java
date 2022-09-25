package com.prd.ConnectedTeam.maty.gameMenagement.backend.listeners;

import com.prd.ConnectedTeam.entity.Account;
import com.vaadin.flow.component.UI;


public interface BroadcastListenerMaty{
    void countUser(UI ui, String nome);
    void startGame1();
    void receiveIndizio(String message);
    void countDown(String n);
    void addUsers(UI ui,int i);
    void numeroInserito(String operazione);
    void partititaVincente(String parola,int punteggio);
    void partititanonVincente();
    void browserIsLeavingCalled(Account account);
    void numeroDaSotrarre(String numero,String numOriginale);
    void numeroDaSommare(String numOriginaale);
}
