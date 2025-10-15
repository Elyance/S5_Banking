package com.compte_pret.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "mvt_statut_compte_pret")
public class MvtStatutComptePret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "compte_pret_id", nullable = false)
    private ComptePret comptePret;

    @ManyToOne
    @JoinColumn(name = "statut_compte_pret_id", nullable = false)
    private StatutComptePret statutComptePret;

    @Column(name = "date_changement")
    private OffsetDateTime dateChangement;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ComptePret getComptePret() { return comptePret; }
    public void setComptePret(ComptePret comptePret) { this.comptePret = comptePret; }
    public StatutComptePret getStatutComptePret() { return statutComptePret; }
    public void setStatutComptePret(StatutComptePret statutComptePret) { this.statutComptePret = statutComptePret; }
    public OffsetDateTime getDateChangement() { return dateChangement; }
    public void setDateChangement(OffsetDateTime dateChangement) { this.dateChangement = dateChangement; }
}
