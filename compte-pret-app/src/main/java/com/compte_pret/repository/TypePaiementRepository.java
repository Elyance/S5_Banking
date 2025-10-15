package com.compte_pret.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.compte_pret.entity.TypePaiement;
import java.util.List;

@ApplicationScoped
public class TypePaiementRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici
    // findAll
    public List<TypePaiement> findAll() {
        return em.createQuery("SELECT t FROM TypePaiement t", TypePaiement.class).getResultList();
    }

    // findById
    public TypePaiement findById(Long id) {
        return em.find(TypePaiement.class, id);
    }
}
