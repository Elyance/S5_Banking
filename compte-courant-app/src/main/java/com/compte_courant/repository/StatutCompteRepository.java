package com.compte_courant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.compte_courant.entity.StatutCompte;

@ApplicationScoped
public class StatutCompteRepository {
    @PersistenceContext
    EntityManager em;
    // find by id
    public StatutCompte findById(Long id) {
        return em.find(StatutCompte.class, id);
    }
}
