package com.compte_pret.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mvt_statut_echeance")
public class MvtStatutEcheance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "echeance_id", nullable = false)
    private Echeance echeance;

    @ManyToOne
    @JoinColumn(name = "statut_echeance_id", nullable = false)
    private StatutEcheance statutEcheance;

    @Column(name = "date_changement")
    private LocalDateTime dateChangement;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Echeance getEcheance() { return echeance; }
    public void setEcheance(Echeance echeance) { this.echeance = echeance; }
    public StatutEcheance getStatutEcheance() { return statutEcheance; }
    public void setStatutEcheance(StatutEcheance statutEcheance) { this.statutEcheance = statutEcheance; }
    public LocalDateTime getDateChangement() { return dateChangement; }
    public void setDateChangement(LocalDateTime dateChangement) { this.dateChangement = dateChangement; }
}
