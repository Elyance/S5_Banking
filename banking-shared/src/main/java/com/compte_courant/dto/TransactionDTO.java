package com.compte_courant.dto;

import java.io.Serializable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long compteCourantId;
    private String compteCourantNumero;
    private Long typeOperationId;
    private String typeOperationLibelle;
    private BigDecimal montant;
    private BigDecimal soldeAvantTransaction;
    private BigDecimal soldeApresTransaction;
    private String description;
    private LocalDateTime dateTransaction;
    private String dateTransactionFormatted;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompteCourantId() {
        return compteCourantId;
    }

    public void setCompteCourantId(Long compteCourantId) {
        this.compteCourantId = compteCourantId;
    }

    public Long getTypeOperationId() {
        return typeOperationId;
    }

    public void setTypeOperationId(Long typeOperationId) {
        this.typeOperationId = typeOperationId;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDateTime dateTransaction) {
        this.dateTransaction = dateTransaction;
    }    
    public String getDateTransactionFormatted() {
        return dateTransactionFormatted;
    }
    public void setDateTransactionFormatted(String dateTransactionFormatted) {
        this.dateTransactionFormatted = dateTransactionFormatted;
    }
    public String getCompteCourantNumero() {
        return compteCourantNumero;
    }
    public void setCompteCourantNumero(String compteCourantNumero) {
        this.compteCourantNumero = compteCourantNumero;
    }
    public String getTypeOperationLibelle() {
        return typeOperationLibelle;
    }
    public void setTypeOperationLibelle(String typeOperationLibelle) {
        this.typeOperationLibelle = typeOperationLibelle;
    }
    public BigDecimal getSoldeAvantTransaction() {
        return soldeAvantTransaction;
    }
    public void setSoldeAvantTransaction(BigDecimal soldeAvantTransaction) {
        this.soldeAvantTransaction = soldeAvantTransaction;
    }
    public BigDecimal getSoldeApresTransaction() {
        return soldeApresTransaction;
    }
    public void setSoldeApresTransaction(BigDecimal soldeApresTransaction) {
        this.soldeApresTransaction = soldeApresTransaction;
    }
}
