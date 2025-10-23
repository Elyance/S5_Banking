package com.compte_courant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_courant.entity.ActionRole;
import java.util.List;

@ApplicationScoped
public class ActionRoleRepository {
    @PersistenceContext
    EntityManager em;

    public ActionRole findById(Long id) {
        return em.find(ActionRole.class, id);
    }

    public List<ActionRole> findByRole(Integer role) {
        return em.createQuery("SELECT ar FROM ActionRole ar WHERE ar.role = :role", ActionRole.class)
                 .setParameter("role", role)
                 .getResultList();
    }

    // findAll
    public List<ActionRole> findAll() {
        return em.createQuery("SELECT ar FROM ActionRole ar", ActionRole.class)
                 .getResultList();
    }

    //findByNomTableAndAction
    public ActionRole findByNomTableAndAction(String nomTable, String action) {
        List<ActionRole> results = em.createQuery("SELECT ar FROM ActionRole ar WHERE ar.nomTable = :nomTable AND ar.action = :action", ActionRole.class)
                 .setParameter("nomTable", nomTable)
                 .setParameter("action", action)
                 .getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}