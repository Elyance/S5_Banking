package com.compte_pret.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "taux_interet")
public class TauxInteret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal valeur;

    @Column(name = "date_debut", nullable = false, unique = true)
    private LocalDateTime dateDebut;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getValeur() { return valeur; }
    public void setValeur(BigDecimal valeur) { this.valeur = valeur; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDateTime dateDebut) { this.dateDebut = dateDebut; }
}
