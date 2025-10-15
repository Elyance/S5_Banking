package com.compte_pret.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class MvtStatutEcheanceRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici
}
