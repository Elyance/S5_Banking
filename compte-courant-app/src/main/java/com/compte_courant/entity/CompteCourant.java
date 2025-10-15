package com.compte_courant.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "compte_courant")
public class CompteCourant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_compte", unique = true, nullable = false, length = 20)
    private String numeroCompte;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal solde = BigDecimal.ZERO;

    @Column(name = "decouvert_autorise", precision = 15, scale = 2)
    private BigDecimal decouvertAutorise = BigDecimal.ZERO;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    // Getters and setters
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
}