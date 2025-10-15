package com.centralisateur.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.compte_courant.remote.CompteCourantServiceRemote;

import com.compte_courant.dto.CompteCourantWithStatusDTO;
import java.util.List;
import java.util.Map;

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

    public void creerCompteCourant(Long idClient, java.math.BigDecimal soldeInitial, java.math.BigDecimal decouvertAutorise) {
        getRemote().creerCompteCourant(idClient, soldeInitial, decouvertAutorise);
    }

    public List<CompteCourantWithStatusDTO> getAllComptesWithStatus() {
        return getRemote().getAllComptesWithStatus();
    }

    public void faireTransaction(Long idCompte, Long idTypeOperation, java.math.BigDecimal montant, String description) {
        getRemote().faireTransaction(idCompte, idTypeOperation, montant, description);
    }

    public Map<Long, String> getAllTypeOperations() {
        return getRemote().getAllTypeOperations();
    }

}
