package com.compte_courant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_statut_mouvement")
public class TransactionStatutMouvement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_statut_transaction", nullable = false)
    private StatutTransaction statutTransaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_transaction", nullable = false)
    private Transaction transaction;

    @Column(name = "date_mouvement", nullable = false)
    private LocalDateTime dateMouvement;

    // Constructors
    public TransactionStatutMouvement() {}

    public TransactionStatutMouvement(StatutTransaction statutTransaction, Transaction transaction, LocalDateTime dateMouvement) {
        this.statutTransaction = statutTransaction;
        this.transaction = transaction;
        this.dateMouvement = dateMouvement;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public StatutTransaction getStatutTransaction() { return statutTransaction; }
    public void setStatutTransaction(StatutTransaction statutTransaction) { this.statutTransaction = statutTransaction; }

    public Transaction getTransaction() { return transaction; }
    public void setTransaction(Transaction transaction) { this.transaction = transaction; }

    public LocalDateTime getDateMouvement() { return dateMouvement; }
    public void setDateMouvement(LocalDateTime dateMouvement) { this.dateMouvement = dateMouvement; }
}