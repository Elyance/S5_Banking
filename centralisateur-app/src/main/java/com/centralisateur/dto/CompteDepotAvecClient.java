package com.centralisateur.dto;

import com.centralisateur.entity.Client;
import java.math.BigDecimal;

public class CompteDepotAvecClient {
    private String compteDepotJson; // JSON brut reçu depuis le microservice
    private Client client;

    // Parsed fields for easy display in JSP
    private Long id;
    private String numeroCompte;
    private String dateCreation;
    private BigDecimal solde;

    public CompteDepotAvecClient() {
    }

    public CompteDepotAvecClient(String compteDepotJson, Client client) {
        this.compteDepotJson = compteDepotJson;
        this.client = client;
    }

    public String getCompteDepotJson() {
        return compteDepotJson;
    }

    public void setCompteDepotJson(String compteDepotJson) {
        this.compteDepotJson = compteDepotJson;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }
}