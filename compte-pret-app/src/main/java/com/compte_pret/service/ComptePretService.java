package com.compte_pret.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import com.compte_pret.entity.TauxInteret;
import com.compte_pret.repository.TauxInteretRepository;

import jakarta.transaction.Transactional;
import com.compte_pret.remote.ComptePretServiceRemote;

@Stateless
public class ComptePretService implements ComptePretServiceRemote {
    @Inject
    private TauxInteretRepository tauxInteretRepository;

    // Méthode pour ajouter un nouveau taux d'intérêt
    @Override
    @Transactional
    public void ajouterTauxInteret(BigDecimal valeur, LocalDateTime dateDebut) {
        TauxInteret tauxInteret = new TauxInteret();
        tauxInteret.setValeur(valeur);
        tauxInteret.setDateDebut(dateDebut);

        tauxInteretRepository.save(tauxInteret);
    }
}
