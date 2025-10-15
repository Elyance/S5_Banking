package com.compte_pret.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.compte_pret.entity.MvtStatutComptePret;

@ApplicationScoped
public class MvtStatutComptePretRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici
    //SAVE
    public void save(MvtStatutComptePret mvt) {
        em.persist(mvt);
    }
}
