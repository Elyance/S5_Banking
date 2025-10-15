package com.compte_pret.remote;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.ejb.Remote;
import jakarta.transaction.Transactional;

@Remote
public interface ComptePretServiceRemote {
    @Transactional
    void ajouterTauxInteret(BigDecimal valeur, LocalDateTime dateDebut);
}
