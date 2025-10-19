package com.compte_courant.service;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import com.compte_courant.repository.*;
import com.compte_courant.entity.*;
import com.compte_courant.dto.*;
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

    @Inject
    private HistoriqueDecouvertAutoriseRepository historiqueDecouvertAutoriseRepository;


    // creer un nouveau compte courant
    @Override
    @Transactional
    public void creerCompteCourant(Long idClient, LocalDateTime dateCreation) {

        CompteCourant compteCourant = new CompteCourant();
        compteCourant.setClientId(idClient);
        compteCourant.setSolde(BigDecimal.ZERO);
        compteCourant.setDateCreation(dateCreation);
        long count = compteCourantRepository.count();
        String numeroCompte = String.format("CC%05d", count + 1);
        compteCourant.setNumeroCompte(numeroCompte);
        // decouvert autorisé selon l'historique
        HistoriqueDecouvertAutorise hdh = historiqueDecouvertAutoriseRepository.findByDate(dateCreation);
        compteCourant.setDecouvertAutorise(hdh != null ? hdh.getValue() : BigDecimal.ZERO);
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
    public void faireTransaction(Long idCompte, Long idTypeOperation, BigDecimal montant, String description, LocalDateTime dateTransaction) {
        CompteCourant compte = compteCourantRepository.findById(idCompte);
        if (compte == null) {
            throw new IllegalArgumentException("Compte courant non trouvé");
        }
        TypeOperation typeOperation = typeOperationRepository.findById(idTypeOperation);
        if (typeOperation == null) {
            throw new IllegalArgumentException("Type d'opération non trouvé");
        }
        if (dateTransaction == null) {
            dateTransaction = LocalDateTime.now();
        }
        if (typeOperation.equals(typeOperationRepository.findById(2L))) {
            BigDecimal nouveauSolde = compte.getSolde().subtract(montant);
            if (nouveauSolde.compareTo(BigDecimal.valueOf(getDecouvertValueByDate(dateTransaction)).negate()) < 0) {
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
        transaction.setDateTransaction(dateTransaction);
        
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

    @Override
    public Long getDecouvertValueByDate(LocalDateTime date) {
        HistoriqueDecouvertAutorise hdh = historiqueDecouvertAutoriseRepository.findByDate(date);
        return hdh != null ? hdh.getValue().longValue() : 0L;
    }

    @Override
    public List<CompteCourantWithStatusDTO> getComptesByClientId(Long clientId) {
        return compteCourantRepository.findComptesByClientIdWithCurrentStatus(clientId);
    }

    @Override
    public CompteCourantWithStatusDTO getCompteById(Long compteId) {
        return compteCourantRepository.findCompteByIdWithCurrentStatus(compteId);
    }

    @Override
    public List<TransactionDTO> getTransactionsByCompteId(Long compteId) {
        List<TransactionDTO> result = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.getTransactionByCompteId(compteId);
    // récupérer le compte pour connaître le solde courant
    CompteCourant compte = compteCourantRepository.findById(compteId);
        BigDecimal soldeInitial = BigDecimal.ZERO;
        if (compte == null) {
            throw new IllegalArgumentException("Compte courant non trouvé");
        } else {
            soldeInitial = BigDecimal.ZERO;
        }

        BigDecimal current = soldeInitial;
        for (Transaction transaction : transactions) {
            TransactionDTO dto = new TransactionDTO();
            dto.setId(transaction.getId());
            dto.setCompteCourantId(transaction.getCompteCourantId());
            dto.setCompteCourantNumero(compte.getNumeroCompte());
            dto.setTypeOperationId(transaction.getTypeOperationId());
            dto.setTypeOperationLibelle(typeOperationRepository.findById(transaction.getTypeOperationId()).getLibelle());
            // solde avant
            dto.setSoldeAvantTransaction(current);
            // appliquer la transaction au solde courant
            TypeOperation to = typeOperationRepository.findById(transaction.getTypeOperationId());
            if (to != null && to.equals(typeOperationRepository.findById(1L))) {
                current = current.add(transaction.getMontant());
            } else {
                current = current.subtract(transaction.getMontant());
            }
            // solde après
            dto.setSoldeApresTransaction(current);
            dto.setMontant(transaction.getMontant());
            dto.setDescription(transaction.getDescription());
            dto.setDateTransaction(transaction.getDateTransaction());
            if (transaction.getDateTransaction() != null) {
                dto.setDateTransactionFormatted(transaction.getDateTransaction().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            } else {
                dto.setDateTransactionFormatted("");
            }
            result.add(dto);
        }
        return result;
    }

    @Override
    @Transactional
    public void mettreAJourDecouvertAutorise(BigDecimal nouveauDecouvert, LocalDateTime dateEffective) {
        // Créer une nouvelle entrée dans l'historique
        HistoriqueDecouvertAutorise hda = new HistoriqueDecouvertAutorise();
        hda.setValue(nouveauDecouvert);
        hda.setDateModification(dateEffective);
        historiqueDecouvertAutoriseRepository.save(hda);
    }
}
