package com.compte_pret.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_pret.entity.Echeance;

@ApplicationScoped
public class EcheanceRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici
    public void save(Echeance echeance) {
        em.persist(echeance);
    }
}
