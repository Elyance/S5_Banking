package com.compte_courant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "statut_compte_courant_mvt")
public class StatutCompteCourantMvt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_courant_id", nullable = false)
    private CompteCourant compteCourant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statut_compte_id", nullable = false)
    private StatutCompte statutCompte;

    @Column(name = "date_mvt")
    private LocalDateTime dateMvt;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public CompteCourant getCompteCourant() { return compteCourant; }
    public void setCompteCourant(CompteCourant compteCourant) { this.compteCourant = compteCourant; }
    public StatutCompte getStatutCompte() { return statutCompte; }
    public void setStatutCompte(StatutCompte statutCompte) { this.statutCompte = statutCompte; }
    public LocalDateTime getDateMvt() { return dateMvt; }
    public void setDateMvt(LocalDateTime dateMvt) { this.dateMvt = dateMvt; }
}