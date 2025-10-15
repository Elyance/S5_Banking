package com.compte_pret.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contrat_pret")
public class ContratPret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compte_pret_id", nullable = false)
    private ComptePret comptePret;

    @Column(name = "montant_emprunte", nullable = false, precision = 15, scale = 2)
    private BigDecimal montantEmprunte;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "duree_totale_mois", nullable = false)
    private Integer dureeTotaleMois;

    @ManyToOne
    @JoinColumn(name = "type_paiement_id", nullable = false)
    private TypePaiement typePaiement;

    @Column(name = "date_fin_theorique", nullable = false)
    private LocalDate dateFinTheorique;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ComptePret getComptePret() { return comptePret; }
    public void setComptePret(ComptePret comptePret) { this.comptePret = comptePret; }
    public BigDecimal getMontantEmprunte() { return montantEmprunte; }
    public void setMontantEmprunte(BigDecimal montantEmprunte) { this.montantEmprunte = montantEmprunte; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public Integer getDureeTotaleMois() { return dureeTotaleMois; }
    public void setDureeTotaleMois(Integer dureeTotaleMois) { this.dureeTotaleMois = dureeTotaleMois; }
    public TypePaiement getTypePaiement() { return typePaiement; }
    public void setTypePaiement(TypePaiement typePaiement) { this.typePaiement = typePaiement; }
    public LocalDate getDateFinTheorique() { return dateFinTheorique; }
    public void setDateFinTheorique(LocalDate dateFinTheorique) { this.dateFinTheorique = dateFinTheorique; }
}
