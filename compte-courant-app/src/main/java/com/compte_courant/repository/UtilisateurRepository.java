package com.compte_courant.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.compte_courant.entity.Utilisateur;

import jakarta.persistence.NoResultException;

@ApplicationScoped
public class UtilisateurRepository {
    @PersistenceContext
    EntityManager em;

    public Utilisateur findByLogin(String login) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.login = :login", Utilisateur.class)
                     .setParameter("login", login)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; // or handle it as per your application's requirement
        }
    }
    


}