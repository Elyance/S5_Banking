package com.compte_courant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_courant.entity.TypeOperation;

import java.util.List;

@ApplicationScoped
public class TypeOperationRepository {
    @PersistenceContext
    EntityManager em;

    public TypeOperation findById(Long id) {
        return em.find(TypeOperation.class, id);
    }

    // find All
    public List<TypeOperation> findAll() {
        return em.createQuery("SELECT t FROM TypeOperation t", TypeOperation.class)
                .getResultList();
    }
}
