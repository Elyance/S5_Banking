package com.compte_courant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.compte_courant.entity.StatutTransaction;

@ApplicationScoped
public class StatutTransactionRepository {
    @PersistenceContext
    EntityManager em;

    public StatutTransaction findById(Long id) {
        return em.find(StatutTransaction.class, id);
    }

    public void save(StatutTransaction statutTransaction) {
        em.persist(statutTransaction);
    }
}