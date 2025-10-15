package com.centralisateur.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdminBanqueRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
}
