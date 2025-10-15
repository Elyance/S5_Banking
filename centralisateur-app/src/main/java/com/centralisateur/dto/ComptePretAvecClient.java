package com.centralisateur.dto;

import com.centralisateur.entity.Client;
import com.compte_pret.dto.ComptePretWithStatusDTO;

public class ComptePretAvecClient {
    Client client;
    ComptePretWithStatusDTO comptePret;
    
    // Constructeur
    public ComptePretAvecClient() {
        
    }
    public ComptePretAvecClient(Client client, ComptePretWithStatusDTO comptePret) {
        this.client = client;
        this.comptePret = comptePret;
    }

    // Getters et Setters
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public ComptePretWithStatusDTO getComptePret() {
        return comptePret;
    }
    public void setComptePret(ComptePretWithStatusDTO comptePret) {
        this.comptePret = comptePret;
    }
}
