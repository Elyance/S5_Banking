package com.centralisateur.repository;

import jakarta.persistence.*;

import jakarta.enterprise.context.ApplicationScoped;
import com.centralisateur.entity.StatutClient;
import java.util.List;

@ApplicationScoped
public class StatutClientRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
    // find statut by id
    public StatutClient findById(Long id) {
        return entityManager.find(StatutClient.class, id);
    }

    // find all statuts
    public List<StatutClient> findAll() {
        return entityManager.createQuery("SELECT s FROM StatutClient s", StatutClient.class).getResultList();
    }
}
