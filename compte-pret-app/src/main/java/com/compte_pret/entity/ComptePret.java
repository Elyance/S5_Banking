package com.compte_pret.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "compte_pret")
public class ComptePret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_compte", nullable = false, unique = true, length = 30)
    private String numeroCompte;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "solde_restant_du", nullable = false, precision = 15, scale = 2)
    private BigDecimal soldeRestantDu;

    @Column(name = "date_creation")
    private OffsetDateTime dateCreation;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumeroCompte() { return numeroCompte; }
    public void setNumeroCompte(String numeroCompte) { this.numeroCompte = numeroCompte; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public BigDecimal getSoldeRestantDu() { return soldeRestantDu; }
    public void setSoldeRestantDu(BigDecimal soldeRestantDu) { this.soldeRestantDu = soldeRestantDu; }
    public OffsetDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(OffsetDateTime dateCreation) { this.dateCreation = dateCreation; }
}
