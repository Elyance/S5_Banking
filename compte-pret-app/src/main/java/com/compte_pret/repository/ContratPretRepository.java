package com.compte_pret.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class ContratPretRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici
}
