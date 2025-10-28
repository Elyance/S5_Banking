package com.centralisateur.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.compte_courant.remote.CompteCourantServiceRemote;

import com.compte_courant.dto.*;
import java.util.List;
import java.util.Map;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CompteCourantService {
    private CompteCourantServiceRemote remote;

    private CompteCourantServiceRemote getRemote() {
        if (remote == null) {
            try {
                remote = (CompteCourantServiceRemote) new InitialContext().lookup(
                    "java:global/compte-courant-app/CompteCourantService!com.compte_courant.remote.CompteCourantServiceRemote"
                );
            } catch (NamingException e) {
                throw new RuntimeException("Erreur lors du lookup EJB distant", e);
            }
        }
        return remote;
    }

    public void creerCompteCourant(Long idClient, LocalDateTime dateCreation) {
        getRemote().creerCompteCourant(idClient, dateCreation);
    }

    public List<CompteCourantWithStatusDTO> getAllComptesWithStatus() {
        return getRemote().getAllComptesWithStatus();
    }

    public void faireTransaction(Long idCompte, Long idTypeOperation, BigDecimal montant, String description, LocalDateTime dateTransaction) {
        getRemote().faireTransaction(idCompte, idTypeOperation, montant, description, dateTransaction);
    }

    public Map<Long, String> getAllTypeOperations() {
        return getRemote().getAllTypeOperations();
    }

    public Long getDecouvertValueByDate(LocalDateTime date) {
        return getRemote().getDecouvertValueByDate(date);
    }

    public List<CompteCourantWithStatusDTO> getComptesByClientId(Long clientId) {
        return getRemote().getComptesByClientId(clientId);
    }

    public CompteCourantWithStatusDTO getCompteById(Long compteId) {
        return getRemote().getCompteById(compteId);
    }

    public List<TransactionDTO> getTransactionsByCompteId(Long compteId) {
        return getRemote().getTransactionsByCompteId(compteId);
    }

    public void mettreAJourDecouvertAutorise(BigDecimal nouveauDecouvert, LocalDateTime dateEffective) {
        getRemote().mettreAJourDecouvertAutorise(nouveauDecouvert, dateEffective);
    }

    public void validerTransaction(Long idTransaction) {
        getRemote().validerTransaction(idTransaction);
    }
}
