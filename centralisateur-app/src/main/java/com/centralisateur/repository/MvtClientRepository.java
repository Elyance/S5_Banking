package com.centralisateur.repository;

import jakarta.persistence.*;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MvtClientRepository {
    @PersistenceContext
    private EntityManager entityManager;
    
}
