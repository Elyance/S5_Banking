
    // ...existing code...
package com.centralisateur.repository;

import jakarta.persistence.*;
import jakarta.enterprise.context.ApplicationScoped;

import com.centralisateur.dto.ClientWithStatusDTO;
import com.centralisateur.entity.Client;
import java.util.List;

@ApplicationScoped
public class ClientRepository {
    @PersistenceContext
    private EntityManager entityManager;


    // save a client
    public void save(Client client) {
        entityManager.persist(client);
    }

    //find all clients
    public List<Client> findAll() {
        String jpql = "SELECT c FROM Client c";
        return entityManager.createQuery(jpql, Client.class).getResultList();
    }

    public List<ClientWithStatusDTO> findAllClientsWithCurrentStatus() {
        TypedQuery<ClientWithStatusDTO> query = entityManager.createQuery(
            "SELECT new com.centralisateur.dto.ClientWithStatusDTO(" +
            "c.id, c.numeroClient, c.nom, c.prenom, c.dateNaissance, " +
            "c.email, c.telephone, c.adresse, c.profession, c.dateCreation, " +
            "s.libelle, m.dateMvt) " +
            "FROM Client c " +
            "INNER JOIN MvtClient m ON c.id = m.client.id " +
            "INNER JOIN StatutClient s ON m.statutClient.id = s.id " +
            "WHERE m.dateMvt = (SELECT MAX(m2.dateMvt) FROM MvtClient m2 WHERE m2.client.id = c.id)", 
            ClientWithStatusDTO.class
        );

        return query.getResultList();
    }

    public ClientWithStatusDTO findClientWithCurrentStatusById(Long id) {
        TypedQuery<ClientWithStatusDTO> query = entityManager.createQuery(
            "SELECT new com.centralisateur.dto.ClientWithStatusDTO(" +
            "c.id, c.numeroClient, c.nom, c.prenom, c.dateNaissance, " +
            "c.email, c.telephone, c.adresse, c.profession, c.dateCreation, " +
            "s.libelle, m.dateMvt) " +
            "FROM Client c " +
            "INNER JOIN MvtClient m ON c.id = m.client.id " +
            "INNER JOIN StatutClient s ON m.statutClient.id = s.id " +
            "WHERE c.id = :clientId AND m.dateMvt = (" +
            "SELECT MAX(m2.dateMvt) FROM MvtClient m2 WHERE m2.client.id = c.id)", 
            ClientWithStatusDTO.class
        );
        query.setParameter("clientId", id);
        
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Client non trouvé ou pas de statut
        }
    }

    // Compter le nombre total de clients
    public long count() {
        String jpql = "SELECT COUNT(c) FROM Client c";
        return entityManager.createQuery(jpql, Long.class).getSingleResult();
    }

    // find client by id
    public Client findById(Long id) {
        return entityManager.find(Client.class, id);
    }

    // update client
    public void update(Client client) {
        entityManager.merge(client);
    }

}