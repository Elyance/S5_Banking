package com.change.remote;

import jakarta.ejb.Remote;
import java.time.LocalDate;
import java.util.List;
import com.change.dto.ChangeDTO;
import java.math.BigDecimal;


@Remote
public interface ChangeServiceRemote {
    List<ChangeDTO> loadChangeFromCSV();
    ChangeDTO rechercherChange(String devise, LocalDate date);
    List<String> getListeDevises();
    BigDecimal calculate(BigDecimal montant, String devise, LocalDate date);
} 