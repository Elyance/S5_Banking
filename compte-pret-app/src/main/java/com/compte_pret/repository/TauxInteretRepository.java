package com.compte_pret.repository;

import com.compte_pret.entity.TauxInteret;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class TauxInteretRepository {
    @PersistenceContext
    EntityManager em;
    
    // save taux d'interet
    public void save(TauxInteret tauxInteret) {
        em.persist(tauxInteret);
    }
}
