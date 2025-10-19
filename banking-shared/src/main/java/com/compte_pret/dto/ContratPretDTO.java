package com.compte_pret.dto;

import java.math.BigDecimal;
import java.io.Serializable;

public class ContratPretDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long comptePretId;
    private String comptePretNumero;
    private int duree;
    private BigDecimal montantEmprunte;
    private BigDecimal montantTotalArembourser;
    private BigDecimal montantRembourse;
    private BigDecimal montantRestantDu;
    private BigDecimal mensualite;
    private BigDecimal tauxInteret;
    private Long typePaiementId;
    private String typePaiementLibelle;
    private String dateDebutContrat;
    private String dateFinContrat;


    // Constructor for Hibernate native query
    public ContratPretDTO(Long id, Long comptePretId, String comptePretNumero, int duree, 
                         BigDecimal montantEmprunte, BigDecimal montantRestantDu, Long typePaiementId, 
                         String typePaiementLibelle, String dateDebutContrat, String dateFinContrat) {
        this.id = id;
        this.comptePretId = comptePretId;
        this.comptePretNumero = comptePretNumero;
        this.duree = duree;
        this.montantEmprunte = montantEmprunte;
        this.montantRestantDu = montantRestantDu;
        this.typePaiementId = typePaiementId;
        this.typePaiementLibelle = typePaiementLibelle;
        this.dateDebutContrat = dateDebutContrat;
        this.dateFinContrat = dateFinContrat;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComptePretId() {
        return comptePretId;
    }

    public void setComptePretId(Long comptePretId) {
        this.comptePretId = comptePretId;
    }
    public String getComptePretNumero() {
        return comptePretNumero;
    }
    public void setComptePretNumero(String comptePretNumero) {
        this.comptePretNumero = comptePretNumero;
    }
    public int getDuree() {
        return duree;
    }
    public void setDuree(int duree) {
        this.duree = duree;
    }
    public BigDecimal getMontantEmprunte() {
        return montantEmprunte;
    }
    public void setMontantEmprunte(BigDecimal montantEmprunte) {
        this.montantEmprunte = montantEmprunte;
    }
    public BigDecimal getMontantTotalArembourser() {
        return montantTotalArembourser;
    }
    public void setMontantTotalArembourser(BigDecimal montantTotalArembourser) {
        this.montantTotalArembourser = montantTotalArembourser;
    }
    public BigDecimal getMontantRembourse() {
        return montantRembourse;
    }
    public void setMontantRembourse(BigDecimal montantRembourse) {
        this.montantRembourse = montantRembourse;
    }
    public BigDecimal getMontantRestantDu() {
        return montantRestantDu;
    }
    public void setMontantRestantDu(BigDecimal montantRestantDu) {
        this.montantRestantDu = montantRestantDu;
    }
    public BigDecimal getMensualite() {
        return mensualite;
    }
    public void setMensualite(BigDecimal mensualite) {
        this.mensualite = mensualite;
    }
    public BigDecimal getTauxInteret() {
        return tauxInteret;
    }
    public void setTauxInteret(BigDecimal tauxInteret) {
        this.tauxInteret = tauxInteret;
    }
    public Long getTypePaiementId() {
        return typePaiementId;
    }
    public void setTypePaiementId(Long typePaiementId) {
        this.typePaiementId = typePaiementId;
    }
    public String getTypePaiementLibelle() {        
        return typePaiementLibelle;
    }
    public void setTypePaiementLibelle(String typePaiementLibelle) {
        this.typePaiementLibelle = typePaiementLibelle;
    }
    public String getDateDebutContrat() {
        return dateDebutContrat;
    }
    public void setDateDebutContrat(String dateDebutContrat) {
        this.dateDebutContrat = dateDebutContrat;
    }
    public String getDateFinContrat() {
        return dateFinContrat;
    }
    public void setDateFinContrat(String dateFinContrat) {
        this.dateFinContrat = dateFinContrat;
    }
}
