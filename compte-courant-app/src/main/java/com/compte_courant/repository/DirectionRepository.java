package com.compte_courant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_courant.entity.Direction;

@ApplicationScoped
public class DirectionRepository {
    @PersistenceContext
    EntityManager em;

    public Direction findById(Long id) {
        return em.find(Direction.class, id);
    }

    


}