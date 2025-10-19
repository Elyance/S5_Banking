package com.compte_pret.remote;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.ejb.Remote;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;

import com.compte_pret.dto.*;


@Remote
public interface ComptePretServiceRemote {
    @Transactional
    void ajouterTauxInteret(BigDecimal valeur, LocalDateTime dateDebut);

    @Transactional
    void creerComptePret(Long clientId, BigDecimal soldeRestantDu, LocalDateTime dateCreation);

    List<ComptePretWithStatusDTO> listerComptesPret();

    Map<Long, String> listerTypesPaiement();

    @Transactional
    void preter(Long comptePretId, BigDecimal montant, Long typePaiementId, int duree, LocalDateTime datePret);

    List<ComptePretWithStatusDTO> findComptesByClientId(Long clientId);

    BigDecimal findTauxInteretByDate(LocalDateTime date);

    List<ContratPretDTO> findContratsByComptePretId(Long comptePretId);

    List<EcheanceDTO> findEcheancesByContratId(Long contratId);

    @Transactional
    void payerEcheance(Long echeanceId);


}
