package com.compte_pret.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "echeances")
public class Echeance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contrat_pret_id", nullable = false)
    private ContratPret contratPret;

    @Column(name = "date_echeance", nullable = false)
    private LocalDate dateEcheance;

    @Column(name = "montant_capital", nullable = false, precision = 12, scale = 2)
    private BigDecimal montantCapital;

    @Column(name = "montant_interet", nullable = false, precision = 12, scale = 2)
    private BigDecimal montantInteret;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ContratPret getContratPret() { return contratPret; }
    public void setContratPret(ContratPret contratPret) { this.contratPret = contratPret; }
    public LocalDate getDateEcheance() { return dateEcheance; }
    public void setDateEcheance(LocalDate dateEcheance) { this.dateEcheance = dateEcheance; }
    public BigDecimal getMontantCapital() { return montantCapital; }
    public void setMontantCapital(BigDecimal montantCapital) { this.montantCapital = montantCapital; }
    public BigDecimal getMontantInteret() { return montantInteret; }
    public void setMontantInteret(BigDecimal montantInteret) { this.montantInteret = montantInteret; }

}
