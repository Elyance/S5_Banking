package com.compte_courant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_courant.entity.*;

@ApplicationScoped
public class StatutCompteCourantMvtRepository {
   @PersistenceContext
   EntityManager em;

    // save
    public void save(StatutCompteCourantMvt statutCompteCourantMvt) {
        em.persist(statutCompteCourantMvt);
    }
    public StatutCompteCourantMvt findLastByCompteCourantId(Long compteCourantId) {
        return em.createQuery(
            "SELECT m FROM StatutCompteCourantMvt m WHERE m.compteCourantId = :compteCourantId ORDER BY m.dateMvt DESC",
            StatutCompteCourantMvt.class)
            .setParameter("compteCourantId", compteCourantId)
            .setMaxResults(1)
            .getResultStream()
            .findFirst()
            .orElse(null);
    }
}
