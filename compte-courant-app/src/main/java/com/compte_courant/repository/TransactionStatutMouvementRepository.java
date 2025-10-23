package com.compte_courant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.compte_courant.entity.TransactionStatutMouvement;
import com.compte_courant.entity.Transaction;
import java.util.List;

@ApplicationScoped
public class TransactionStatutMouvementRepository {
    @PersistenceContext
    EntityManager em;

    //findByLastMovuvement for a transaction
    public TransactionStatutMouvement findByLastMouvement(Long transactionId) {
        String jpql = "SELECT tsm FROM TransactionStatutMouvement tsm WHERE tsm.transaction.id = :transactionId ORDER BY tsm.dateMouvement DESC";
        TypedQuery<TransactionStatutMouvement> query = em.createQuery(jpql, TransactionStatutMouvement.class);
        query.setParameter("transactionId", transactionId);
        query.setMaxResults(1);
        List<TransactionStatutMouvement> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    //save
    public void save(TransactionStatutMouvement tsm) {
        em.persist(tsm);
    }
}