package com.compte_courant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

public class CompteCourantWithStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String numeroCompte;
    private Long clientId;
    private BigDecimal solde;
    private BigDecimal decouvertAutorise;
    private LocalDateTime dateCreation;
    private Long statutId;        // Nouveau champ
    private String statutLibelle;
    private LocalDateTime dateMvt;

    // Constructor matching JPQL projection (with java.util.Date)
    public CompteCourantWithStatusDTO(Long id, String numeroCompte, Long clientId, 
                                     BigDecimal solde, BigDecimal decouvertAutorise, 
                                     LocalDateTime dateCreation, Long statutId,
                                     String statutLibelle, LocalDateTime dateMvt) {
        this.id = id;
        this.numeroCompte = numeroCompte;
        this.clientId = clientId;
        this.solde = solde;
        this.decouvertAutorise = decouvertAutorise;
        this.dateCreation = dateCreation;
        this.statutId = statutId;
        this.statutLibelle = statutLibelle;
        this.dateMvt = dateMvt;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroCompte() { return numeroCompte; }
    public void setNumeroCompte(String numeroCompte) { this.numeroCompte = numeroCompte; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }

    public BigDecimal getDecouvertAutorise() { return decouvertAutorise; }
    public void setDecouvertAutorise(BigDecimal decouvertAutorise) { this.decouvertAutorise = decouvertAutorise; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public Long getStatutId() { return statutId; }
    public void setStatutId(Long statutId) { this.statutId = statutId; }

    public String getStatutLibelle() { return statutLibelle; }
    public void setStatutLibelle(String statutLibelle) { this.statutLibelle = statutLibelle; }

    public LocalDateTime getDateMvt() { return dateMvt; }
    public void setDateMvt(LocalDateTime dateMvt) { this.dateMvt = dateMvt; }
}