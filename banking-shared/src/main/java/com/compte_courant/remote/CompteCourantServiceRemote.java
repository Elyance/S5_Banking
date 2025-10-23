package com.compte_courant.remote;


import jakarta.ejb.Remote;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import com.compte_courant.dto.CompteCourantWithStatusDTO;
import java.util.Map;

import java.time.LocalDateTime;
import com.compte_courant.dto.TransactionDTO;

@Remote
public interface CompteCourantServiceRemote {
    @Transactional
    void creerCompteCourant(Long idClient, LocalDateTime dateCreation);

    List<CompteCourantWithStatusDTO> getAllComptesWithStatus();

    @Transactional
    void faireTransaction(Long idCompte, Long idTypeOperation, BigDecimal montant,String description, LocalDateTime dateTransaction);

    Map<Long, String> getAllTypeOperations();

    Long getDecouvertValueByDate(LocalDateTime date);

    List<CompteCourantWithStatusDTO> getComptesByClientId(Long clientId);

    CompteCourantWithStatusDTO getCompteById(Long compteId);

    List<TransactionDTO> getTransactionsByCompteId(Long compteId);

    @Transactional
    void mettreAJourDecouvertAutorise(BigDecimal nouveauDecouvert, LocalDateTime dateEffective);

    @Transactional
    void validerTransaction(Long idTransaction);

    @Transactional
    void rejeterTransaction(Long idTransaction);

    boolean isValidateOrNot(Long idTransaction);

}
