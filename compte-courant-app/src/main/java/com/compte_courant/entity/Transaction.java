package com.compte_courant.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "compte_courant_id", nullable = false)
    private Long compteCourantId;

    @Column(name = "type_operation_id", nullable = false)
    private Long typeOperationId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montant;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_transaction")
    private LocalDateTime dateTransaction;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCompteCourantId() { return compteCourantId; }
    public void setCompteCourantId(Long compteCourantId) { this.compteCourantId = compteCourantId; }
    public Long getTypeOperationId() { return typeOperationId; }
    public void setTypeOperationId(Long typeOperationId) { this.typeOperationId = typeOperationId; }
    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDateTransaction() { return dateTransaction; }
    public void setDateTransaction(LocalDateTime dateTransaction) { this.dateTransaction = dateTransaction; }
}