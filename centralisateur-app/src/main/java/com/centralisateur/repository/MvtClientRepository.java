package com.centralisateur.repository;

import jakarta.persistence.*;

import com.centralisateur.entity.MvtClient;
import com.centralisateur.entity.StatutClient;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MvtClientRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
    // enregister un mouvement
    public void save(MvtClient mvtClient) {
        entityManager.persist(mvtClient);
    }

    
    
}
