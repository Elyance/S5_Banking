package com.centralisateur.repository;

import jakarta.persistence.*;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClientRepository {
    @PersistenceContext
    private EntityManager entityManager;
}
