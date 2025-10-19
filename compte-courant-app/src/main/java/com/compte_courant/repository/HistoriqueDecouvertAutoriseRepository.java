package com.compte_courant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.compte_courant.entity.HistoriqueDecouvertAutorise;
import java.time.LocalDateTime;

@ApplicationScoped
public class HistoriqueDecouvertAutoriseRepository {

    @PersistenceContext
    EntityManager em;

    public HistoriqueDecouvertAutorise findByDate(LocalDateTime date) {
        String jpql = "SELECT h FROM HistoriqueDecouvertAutorise h WHERE h.dateModification <= :date ORDER BY h.dateModification DESC";
        return em.createQuery(jpql, HistoriqueDecouvertAutorise.class)
                 .setParameter("date", date)
                 .setMaxResults(1)
                 .getSingleResult();
    }

    public void save(HistoriqueDecouvertAutorise hda) {
        em.persist(hda);
    }
}