package com.compte_pret.repository;

import com.compte_pret.entity.TauxInteret;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;

@ApplicationScoped
public class TauxInteretRepository {
    @PersistenceContext
    EntityManager em;
    
    // save taux d'interet
    public void save(TauxInteret tauxInteret) {
        em.persist(tauxInteret);
    }

    // find latest taux d'interet
    public TauxInteret findLatest() {
        return em.createQuery("SELECT t FROM TauxInteret t ORDER BY t.dateDebut DESC", TauxInteret.class)
                 .setMaxResults(1)
                 .getSingleResult();
    }

    public TauxInteret findTauxInteretByDate(LocalDateTime date) {
        String jpql = "SELECT t FROM TauxInteret t WHERE t.dateDebut <= :date ORDER BY t.dateDebut DESC";
        return em.createQuery(jpql, TauxInteret.class)
                 .setParameter("date", date)
                 .setMaxResults(1)
                 .getSingleResult();
    }
}
