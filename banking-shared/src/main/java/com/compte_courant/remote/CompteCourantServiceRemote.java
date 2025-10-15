package com.compte_courant.remote;


import jakarta.ejb.Remote;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import com.compte_courant.dto.CompteCourantWithStatusDTO;
import java.util.Map;
@Remote
public interface CompteCourantServiceRemote {
    @Transactional
    void creerCompteCourant(Long idClient, BigDecimal soldeInitial, BigDecimal decouvertAutorise);

    List<CompteCourantWithStatusDTO> getAllComptesWithStatus();

    @Transactional
    void faireTransaction(Long idCompte, Long idTypeOperation, BigDecimal montant,String description);

    Map<Long, String> getAllTypeOperations();
}
