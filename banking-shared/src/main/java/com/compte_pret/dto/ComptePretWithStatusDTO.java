package com.compte_pret.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

public class ComptePretWithStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String numeroCompte;
    private Long clientId;
    private BigDecimal soldeRestantDu;
    private LocalDateTime dateCreation;
    private String statutLibelle;
    private LocalDateTime dateDernierStatut;

    // Constructeur avec 7 paramètres (correspond à votre requête JPQL)
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
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public String getStatutLibelle() { return statutLibelle; }
    public void setStatutLibelle(String statutLibelle) { this.statutLibelle = statutLibelle; }

    public LocalDateTime getDateDernierStatut() { return dateDernierStatut; }
    public void setDateDernierStatut(LocalDateTime dateDernierStatut) { this.dateDernierStatut = dateDernierStatut; }

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