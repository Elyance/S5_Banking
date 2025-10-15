package com.compte_pret.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_pret.entity.StatutEcheance;

@ApplicationScoped
public class StatutEcheanceRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici
    // find by id
    public StatutEcheance findById(Long id) {
        return em.find(StatutEcheance.class, id);
    }
}
