package com.centralisateur.service;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;

import com.change.dto.ChangeDTO;
import com.change.remote.ChangeServiceRemote;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

@ApplicationScoped
public class ChangeService {
    @EJB(beanName = "ChangeServiceRemote", lookup = "java:global/change/ChangeService!com.change.remote.ChangeServiceRemote")
    private ChangeServiceRemote changeService;

    // Méthodes pour interagir avec le service de change distant
    public List<ChangeDTO> loadChangeFromCSV() {
        return changeService.loadChangeFromCSV();
    }

    public ChangeDTO rechercherChange(String devise, java.time.LocalDate date) {
        return changeService.rechercherChange(devise, date);
    }

    public List<String> getListeDevises() {
        return changeService.getListeDevises();
    }

    public BigDecimal calculate(BigDecimal montant, String devise, LocalDate date) {
        return changeService.calculate(montant, devise,date);
    }
}
