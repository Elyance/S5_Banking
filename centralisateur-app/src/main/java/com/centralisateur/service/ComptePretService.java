package com.centralisateur.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.compte_pret.remote.ComptePretServiceRemote;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ComptePretService {
    private ComptePretServiceRemote comptePretServiceRemote;

    private ComptePretServiceRemote getRemote() {
        if (comptePretServiceRemote == null) {
            try {
                comptePretServiceRemote = (ComptePretServiceRemote) new InitialContext().lookup(
                    "java:global/compte-pret-app/ComptePretService!com.compte_pret.remote.ComptePretServiceRemote"
                );
            } catch (NamingException e) {
                throw new RuntimeException("Erreur lors du lookup EJB distant", e);
            }
        }
        return comptePretServiceRemote;
    }

    public void ajouterTauxInteret(BigDecimal valeur, LocalDateTime dateDebut) {
        getRemote().ajouterTauxInteret(valeur, dateDebut);
    }
}
