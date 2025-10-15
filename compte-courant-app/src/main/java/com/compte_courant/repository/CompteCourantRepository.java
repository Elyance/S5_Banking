package com.compte_courant.repository;

import java.util.List;

import com.compte_courant.entity.CompteCourant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.enterprise.context.ApplicationScoped;
import com.compte_courant.dto.CompteCourantWithStatusDTO;


@ApplicationScoped
public class CompteCourantRepository {

    @PersistenceContext
    EntityManager em;
    // save compte courant

    public void save(CompteCourant compteCourant) {
        em.persist(compteCourant);
    }
    // compter les comptes courants 
    public Long count() {
        return em.createQuery("SELECT COUNT(c) FROM CompteCourant c", Long.class)
                .getSingleResult();
    }

    public List<CompteCourantWithStatusDTO> findAllComptesWithCurrentStatus() {
        String jpql = "SELECT new com.compte_courant.dto.CompteCourantWithStatusDTO(" +
            "cc.id, cc.numeroCompte, cc.clientId, cc.solde, cc.decouvertAutorise, " +
            "cc.dateCreation, sc.id, sc.libelle, sccm.dateMvt) " + // Ajoutez sc.id et sccm.dateMvt
            "FROM CompteCourant cc " +
            "INNER JOIN StatutCompteCourantMvt sccm ON cc.id = sccm.compteCourant.id " + // Utilisez sccm.compteCourant.id
            "INNER JOIN StatutCompte sc ON sccm.statutCompte.id = sc.id " + // Utilisez sccm.statutCompte.id
            "WHERE sccm.dateMvt = (" +
            "    SELECT MAX(sccm2.dateMvt) " +
            "    FROM StatutCompteCourantMvt sccm2 " +
            "    WHERE sccm2.compteCourant.id = cc.id" + // Utilisez sccm2.compteCourant.id
            ")";

        return em.createQuery(jpql, CompteCourantWithStatusDTO.class)
            .getResultList();
    }

    public CompteCourant findById(Long id) {
        return em.find(CompteCourant.class, id);
    }

}
