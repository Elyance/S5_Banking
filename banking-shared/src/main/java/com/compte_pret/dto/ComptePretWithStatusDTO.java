package com.compte_pret.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

public class ComptePretWithStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String numeroCompte;
    private Long clientId;
    private BigDecimal soldeRestantDu;
    private LocalDateTime dateCreation;
    private BigDecimal tauxInteret;
    private String statutLibelle;
    private LocalDateTime dateDernierStatut;
    private String dateCreationFormatted;
    private String dateDernierStatutFormatted;

    // Constructeur avec 7 paramètres
    public ComptePretWithStatusDTO(Long id, String numeroCompte, Long clientId, 
                                  BigDecimal soldeRestantDu, LocalDateTime dateCreation, 
                                  String statutLibelle, LocalDateTime dateDernierStatut) {
        this.id = id;
        this.numeroCompte = numeroCompte;
        this.clientId = clientId;
        this.soldeRestantDu = soldeRestantDu;
        this.dateCreation = dateCreation;
        this.statutLibelle = statutLibelle;
        this.dateDernierStatut = dateDernierStatut;
        
        // Formatage des dates
        this.dateCreationFormatted = formatDate(dateCreation, "yyyy/MM/dd HH:mm");
        this.dateDernierStatutFormatted = formatDate(dateDernierStatut, "yyyy/MM/dd HH:mm");
    }

    // Méthode de formatage
    private String formatDate(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroCompte() { return numeroCompte; }
    public void setNumeroCompte(String numeroCompte) { this.numeroCompte = numeroCompte; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public BigDecimal getSoldeRestantDu() { return soldeRestantDu; }
    public void setSoldeRestantDu(BigDecimal soldeRestantDu) { this.soldeRestantDu = soldeRestantDu; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { 
        this.dateCreation = dateCreation;
        this.dateCreationFormatted = formatDate(dateCreation, "yyyy/MM/dd HH:mm");
    }

    public String getStatutLibelle() { return statutLibelle; }
    public void setStatutLibelle(String statutLibelle) { this.statutLibelle = statutLibelle; }

    public LocalDateTime getDateDernierStatut() { return dateDernierStatut; }
    public void setDateDernierStatut(LocalDateTime dateDernierStatut) { 
        this.dateDernierStatut = dateDernierStatut;
        this.dateDernierStatutFormatted = formatDate(dateDernierStatut, "yyyy/MM/dd HH:mm");
    }

    public BigDecimal getTauxInteret() { return tauxInteret; }
    public void setTauxInteret(BigDecimal tauxInteret) { this.tauxInteret = tauxInteret; }

    public String getDateCreationFormatted() { 
        if (dateCreationFormatted == null && dateCreation != null) {
            dateCreationFormatted = formatDate(dateCreation, "yyyy/MM/dd HH:mm");
        }
        return dateCreationFormatted; 
    }

    public String getDateDernierStatutFormatted() { 
        if (dateDernierStatutFormatted == null && dateDernierStatut != null) {
            dateDernierStatutFormatted = formatDate(dateDernierStatut, "yyyy/MM/dd HH:mm");
        }
        return dateDernierStatutFormatted; 
    }

    @Override
    public String toString() {
        return "ComptePretWithStatusDTO{" +
                "id=" + id +
                ", numeroCompte='" + numeroCompte + '\'' +
                ", clientId=" + clientId +
                ", soldeRestantDu=" + soldeRestantDu +
                ", dateCreation=" + dateCreation +
                ", statutLibelle='" + statutLibelle + '\'' +
                ", dateDernierStatut=" + dateDernierStatut +
                '}';
    }
}