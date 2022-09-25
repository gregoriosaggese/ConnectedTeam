package com.prd.ConnectedTeam.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
public class Account {

    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Id @GeneratedValue
    private Long id;
    private String nome;
    private String sesso;

    @Column(unique = true)
    private String email;
    private String password;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] profilePicture;


    public Account() {
    }

    public Account(String nome,String email, String password,String sesso) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.sesso = sesso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }
}
