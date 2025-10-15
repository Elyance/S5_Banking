package com.compte_courant.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import com.compte_courant.repository.*;
import com.compte_courant.entity.*;
import com.compte_courant.dto.CompteCourantWithStatusDTO;
import com.compte_courant.remote.CompteCourantServiceRemote;
import java.util.*;



@Stateless
public class CompteCourantService implements CompteCourantServiceRemote {
    @Inject
    private CompteCourantRepository compteCourantRepository;


    @Inject 
    private StatutCompteCourantMvtRepository statutCompteCourantMvtRepository;

    @Inject 
    private StatutCompteRepository statutCompteRepository;

    @Inject 
    private TypeOperationRepository typeOperationRepository;

    @Inject 
    private TransactionRepository transactionRepository;


    // creer un nouveau compte courant
    @Override
    @Transactional
    public void creerCompteCourant(Long idClient, BigDecimal soldeInitial, BigDecimal decouvertAutorise) {
        CompteCourant compteCourant = new CompteCourant();
        compteCourant.setClientId(idClient);
        compteCourant.setSolde(soldeInitial);
        compteCourant.setDecouvertAutorise(decouvertAutorise);
        compteCourant.setDateCreation(LocalDateTime.now());
        long count = compteCourantRepository.count();
        String numeroCompte = String.format("CC%05d", count + 1);
        compteCourant.setNumeroCompte(numeroCompte);
        compteCourantRepository.save(compteCourant);

        StatutCompteCourantMvt mvtCompteCourant =  new StatutCompteCourantMvt();
        mvtCompteCourant.setCompteCourant(compteCourant);
        mvtCompteCourant.setStatutCompte(statutCompteRepository.findById(1L));
        mvtCompteCourant.setDateMvt(LocalDateTime.now());
        statutCompteCourantMvtRepository.save(mvtCompteCourant);
    }

    // lister tous les comptes courants avec leur statut
    @Override
    public List<CompteCourantWithStatusDTO> getAllComptesWithStatus() {
        return compteCourantRepository.findAllComptesWithCurrentStatus();
    }

    @Override
    @Transactional
    public void faireTransaction(Long idCompte, Long idTypeOperation, BigDecimal montant, String description) {
        CompteCourant compte = compteCourantRepository.findById(idCompte);
        if (compte == null) {
            throw new IllegalArgumentException("Compte courant non trouvé");
        }
        TypeOperation typeOperation = typeOperationRepository.findById(idTypeOperation);
        if (typeOperation == null) {
            throw new IllegalArgumentException("Type d'opération non trouvé");
        }
        if (typeOperation.equals(typeOperationRepository.findById(2L))) {
            BigDecimal nouveauSolde = compte.getSolde().subtract(montant);
            if (nouveauSolde.compareTo(compte.getDecouvertAutorise().negate()) < 0) {
                throw new IllegalArgumentException("Solde insuffisant pour cette opération");
            }
            compte.setSolde(nouveauSolde);
        } else if (typeOperation.equals(typeOperationRepository.findById(1L))) {
            compte.setSolde(compte.getSolde().add(montant));
        } else {
            throw new IllegalArgumentException("Type d'opération inconnu");
        }
        compteCourantRepository.save(compte);

        Transaction transaction = new Transaction();
        transaction.setCompteCourantId(idCompte);
        transaction.setTypeOperationId(idTypeOperation);
        transaction.setMontant(montant);
        transaction.setDescription(description);
        transaction.setDateTransaction(LocalDateTime.now());
        
        // Enregistrer la transaction
        transactionRepository.save(transaction);
    }

    @Override
    public Map<Long, String> getAllTypeOperations() {
        List<TypeOperation> typeOperations = typeOperationRepository.findAll();
        Map<Long, String> result = new HashMap<>();
        for (TypeOperation typeOperation : typeOperations) {
            result.put(typeOperation.getId(), typeOperation.getLibelle());
        }
        return result;
    }
}
