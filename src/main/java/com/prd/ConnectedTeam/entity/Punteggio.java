package com.prd.ConnectedTeam.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

@Embeddable
public class Punteggio {

    @OneToOne(orphanRemoval = true, cascade = CascadeType.REMOVE)
    private Account account;
    private int punti;

    public Punteggio(){}

    public Punteggio(Account account, int punti) {
        this.account = account;
        this.punti = punti;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    @Override
    public String toString() {
        return "Voto{" +
                "account=" + account.getEmail() +
                ", punti=" + punti +
                '}';
    }

}
