package com.centralisateur.service;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import com.change.dto.ChangeDTO;
import com.change.remote.ChangeServiceRemote;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@ApplicationScoped
public class ChangeService {
    private ChangeServiceRemote changeService;

    @PostConstruct
    public void init() {
        System.out.println("Initialisation ChangeService...");
        
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            props.put(Context.PROVIDER_URL, "http-remoting://localhost:6660"); // Port remoting WildFly
            props.put(Context.SECURITY_PRINCIPAL, "elyance"); // Remplacez si nécessaire
            props.put(Context.SECURITY_CREDENTIALS, "wiwi"); // Remplacez si nécessaire
            InitialContext ctx = new InitialContext(props);
            ChangeServiceRemote service = (ChangeServiceRemote) ctx.lookup("ejb:/change/ChangeService!com.change.remote.ChangeServiceRemote");
            this.changeService = service;
            System.out.println("ChangeService initialisé avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthodes pour interagir avec le service de change distant
    public List<ChangeDTO> loadChangeFromCSV() {
        if (changeService == null) {
            System.err.println("ChangeService non initialisé");
            return new ArrayList<>();
        }
        return changeService.loadChangeFromCSV();
    }

    public ChangeDTO rechercherChange(String path, String devise, LocalDate date) {
        if (changeService == null) {
            System.err.println("ChangeService non initialisé");
            return null;
        }
        return changeService.rechercherChange(devise, date);
    }

    public List<String> getListeDevises() {
        if (changeService == null) {
            System.err.println("ChangeService non initialisé");
            return new ArrayList<>();
        }
        return changeService.getListeDevises();
    }

    public BigDecimal calculate(String path, BigDecimal montant, String devise, LocalDate date) {
        if (changeService == null) {
            System.err.println("ChangeService non initialisé - retour du montant original");
            return montant;
        }
        return changeService.calculate(montant, devise, date);
    }
    
    public boolean isInitialized() {
        return changeService != null;
    }
}