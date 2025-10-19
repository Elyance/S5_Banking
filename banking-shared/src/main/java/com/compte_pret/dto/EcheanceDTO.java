package com.compte_pret.dto;

import java.math.BigDecimal;
import java.io.Serializable;

public class EcheanceDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String dateEcheance;
    private BigDecimal montantCapital;
    private BigDecimal montantInteret;
    private String statutLibelle;
    private String datePaiement;

    // Constructor for Hibernate native query
    public EcheanceDTO(Long id, String dateEcheance, BigDecimal montantCapital, BigDecimal montantInteret, String statutLibelle, String datePaiement) {
        this.id = id;
        this.dateEcheance = dateEcheance;
        this.montantCapital = montantCapital;
        this.montantInteret = montantInteret;
        this.statutLibelle = statutLibelle;
        this.datePaiement = datePaiement;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(String dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public BigDecimal getMontantCapital() {
        return montantCapital;
    }

    public void setMontantCapital(BigDecimal montantCapital) {
        this.montantCapital = montantCapital;
    }

    public BigDecimal getMontantInteret() {
        return montantInteret;
    }

    public void setMontantInteret(BigDecimal montantInteret) {
        this.montantInteret = montantInteret;
    }

    public String getStatutLibelle() {
        return statutLibelle;
    }

    public void setStatutLibelle(String statutLibelle) {
        this.statutLibelle = statutLibelle;
    }

    public String getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(String datePaiement) {
        this.datePaiement = datePaiement;
    }

    public BigDecimal getMontantTotal() {
        return montantCapital.add(montantInteret);
    }
}