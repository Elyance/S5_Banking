package com.compte_pret.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_pret.entity.MvtStatutEcheance;

@ApplicationScoped
public class MvtStatutEcheanceRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici
    public void save(MvtStatutEcheance mvtStatutEcheance) {
        em.persist(mvtStatutEcheance);
    }
}
