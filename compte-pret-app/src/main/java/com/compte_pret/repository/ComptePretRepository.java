package com.compte_pret.repository;

import java.util.List;

import com.compte_pret.dto.ComptePretWithStatusDTO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_pret.entity.ComptePret;

@ApplicationScoped
public class ComptePretRepository {
    @PersistenceContext
    EntityManager em;
    // Ajoutez vos méthodes ici

    // save
    public void save(Object entity) {
        em.persist(entity);
    }
    // count
    public long count() {
        return em.createQuery("SELECT COUNT(c) FROM ComptePret c", Long.class).getSingleResult();
    }

    public List<ComptePretWithStatusDTO> findAllComptesWithCurrentStatus() {
    String jpql = "SELECT new com.compte_pret.dto.ComptePretWithStatusDTO(" +
            "cp.id, cp.numeroCompte, cp.clientId, cp.soldeRestantDu, " +
            "cp.dateCreation, scp.libelle, mscp.dateChangement) " +
            "FROM ComptePret cp " +
            "INNER JOIN MvtStatutComptePret mscp ON cp.id = mscp.comptePret.id " +
            "INNER JOIN StatutComptePret scp ON mscp.statutComptePret.id = scp.id " +
            "WHERE mscp.dateChangement = (" +
            "    SELECT MAX(mscp2.dateChangement) " +
            "    FROM MvtStatutComptePret mscp2 " +
            "    WHERE mscp2.comptePret.id = cp.id" +
            ") " +
            "ORDER BY cp.dateCreation DESC";

        return em.createQuery(jpql, ComptePretWithStatusDTO.class)
            .getResultList();
    }

    // findById
    public ComptePret findById(Long id) {
        return em.find(ComptePret.class, id);
    }
}