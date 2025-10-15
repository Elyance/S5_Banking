package com.centralisateur.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientWithStatusDTO {
    private Long id;
    private String numeroClient;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String email;
    private String telephone;
    private String adresse;
    private String profession;
    private LocalDateTime dateCreation;
    private String statutLibelle;
    private LocalDateTime dateDernierStatut;

    // Constructeur
    public ClientWithStatusDTO(Long id, String numeroClient, String nom, String prenom, 
                              LocalDate dateNaissance, String email, String telephone, 
                              String adresse, String profession, LocalDateTime dateCreation, 
                              String statutLibelle, LocalDateTime dateDernierStatut) {
        this.id = id;
        this.numeroClient = numeroClient;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.profession = profession;
        this.dateCreation = dateCreation;
        this.statutLibelle = statutLibelle;
        this.dateDernierStatut = dateDernierStatut;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroClient() {
        return numeroClient;
    }

    public void setNumeroClient(String numeroClient) {
        this.numeroClient = numeroClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getStatutLibelle() {
        return statutLibelle;
    }

    public void setStatutLibelle(String statutLibelle) {
        this.statutLibelle = statutLibelle;
    }

    public LocalDateTime getDateDernierStatut() {
        return dateDernierStatut;
    }

    public void setDateDernierStatut(LocalDateTime dateDernierStatut) {
        this.dateDernierStatut = dateDernierStatut;
    }
}