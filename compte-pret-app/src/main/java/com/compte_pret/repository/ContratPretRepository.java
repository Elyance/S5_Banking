package com.compte_pret.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_pret.entity.ContratPret;


@ApplicationScoped
public class ContratPretRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici
    // save
    public void save(ContratPret contratPret) {
        em.persist(contratPret);
    }
}
