package com.compte_pret.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.compte_pret.entity.StatutComptePret;

@ApplicationScoped
public class StatutComptePretRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici

    // findById
    public StatutComptePret findById(Long id) {
        return em.find(StatutComptePret.class, id);
    }
}
