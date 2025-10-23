package com.compte_courant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_courant.entity.Transaction;

import java.util.List;

@ApplicationScoped
public class TransactionRepository {

    @PersistenceContext
    EntityManager em;

    // save
    public void save(Transaction transaction) {
        em.persist(transaction);
    }

    //getTransactions by compte id
    public List<Transaction> getTransactionByCompteId(Long compteId) {
        String jpql = "SELECT t FROM Transaction t WHERE t.compteCourantId = :compteId ORDER BY t.dateTransaction ASC";
        return em.createQuery(jpql, Transaction.class)
                 .setParameter("compteId", compteId)
                 .getResultList();
    }

    //findById
    public Transaction findById(Long id) {
        return em.find(Transaction.class, id);
    }

}
