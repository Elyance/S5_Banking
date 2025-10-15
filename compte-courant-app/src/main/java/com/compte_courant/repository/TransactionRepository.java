package com.compte_courant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_courant.entity.Transaction;

@ApplicationScoped
public class TransactionRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Transaction transaction) {
        em.persist(transaction);
    }
}
