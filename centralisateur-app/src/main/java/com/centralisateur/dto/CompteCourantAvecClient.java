package com.centralisateur.dto;

import com.compte_courant.dto.CompteCourantWithStatusDTO;
import com.centralisateur.entity.Client;

public class CompteCourantAvecClient {
    CompteCourantWithStatusDTO compte;
    Client client;

    public CompteCourantAvecClient(CompteCourantWithStatusDTO compte, Client client) {
        this.compte = compte;
        this.client = client;
    }

    // Getters et setters
    public CompteCourantWithStatusDTO getCompte() {
        return compte;
    }

    public void setCompte(CompteCourantWithStatusDTO compte) {
        this.compte = compte;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
