package com.compte_pret.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import com.compte_pret.dto.*;
import com.compte_pret.entity.*;
import com.compte_pret.repository.*;

import jakarta.transaction.Transactional;
import com.compte_pret.remote.ComptePretServiceRemote;

@Stateless
public class ComptePretService implements ComptePretServiceRemote {
    @Inject
    private TauxInteretRepository tauxInteretRepository;

    @Inject
    private ComptePretRepository comptePretRepository;

    @Inject
    private StatutComptePretRepository statutComptePretRepository;

    @Inject
    private MvtStatutComptePretRepository mvtStatutComptePretRepository;

    @Inject
    private ContratPretRepository contratPretRepository;

    @Inject
    private TypePaiementRepository typePaiementRepository;

    @Inject
    private EcheanceRepository echeanceRepository;

    @Inject
    private StatutEcheanceRepository statutEcheanceRepository;

    @Inject
    private MvtStatutEcheanceRepository mvtStatutEcheanceRepository;


    // Méthode pour ajouter un nouveau taux d'intérêt
    @Override
    @Transactional
    public void ajouterTauxInteret(BigDecimal valeur, LocalDateTime dateDebut) {
        TauxInteret tauxInteret = new TauxInteret();
        tauxInteret.setValeur(valeur);
        tauxInteret.setDateDebut(dateDebut);

        tauxInteretRepository.save(tauxInteret);
    }

    @Override
    @Transactional
    public void creerComptePret(Long clientId, BigDecimal soldeRestantDu, LocalDateTime dateCreation) {
        ComptePret comptePret = new ComptePret();
        comptePret.setClientId(clientId);
        long count = comptePretRepository.count();
        String numeroCompte = String.format("CC%05d", count + 1);
        comptePret.setNumeroCompte(numeroCompte);
        comptePret.setSoldeRestantDu(soldeRestantDu);
        comptePret.setDateCreation(dateCreation);

        comptePretRepository.save(comptePret);

        MvtStatutComptePret mvt = new MvtStatutComptePret();
        mvt.setComptePret(comptePret);
        mvt.setStatutComptePret(statutComptePretRepository.findById(1L));
        mvt.setDateChangement(LocalDateTime.now());

        mvtStatutComptePretRepository.save(mvt);
    }

    @Override
    public List<ComptePretWithStatusDTO> listerComptesPret() {
        List<ComptePretWithStatusDTO> comptes = comptePretRepository.findAllComptesWithCurrentStatus();
        return comptes;
    }

    @Override
    public Map<Long, String> listerTypesPaiement() {
        Map<Long, String> typesPaiement = new HashMap<>();
        List<TypePaiement> liste = typePaiementRepository.findAll();
        for (TypePaiement type : liste) {
            typesPaiement.put(type.getId(), type.getLibelle());
        }
        return typesPaiement;
    }

    @Override
    @Transactional
    public void preter(Long comptePretId, BigDecimal montant, Long typePaiementId, int duree, LocalDateTime datePret) {
        ComptePret comptePret = comptePretRepository.findById(comptePretId);
        if (comptePret == null) {
            throw new IllegalArgumentException("Compte prêt non trouvé avec l'ID: " + comptePretId);
        }

        // Mettre à jour le solde restant dû
        comptePret.setSoldeRestantDu(comptePret.getSoldeRestantDu().add(montant));
        comptePretRepository.save(comptePret);

        // Récupérer le type de paiement pour la périodicité
        TypePaiement typePaiement = typePaiementRepository.findById(typePaiementId);
        if (typePaiement == null) {
            throw new IllegalArgumentException("Type de paiement non trouvé avec l'ID: " + typePaiementId);
        }
        int periodicite = typePaiement.getValeur(); // ex: 1=mensuel, 2=bimestriel, etc.

        // Créer un nouveau contrat de prêt
        ContratPret contratPret = new ContratPret();
        contratPret.setComptePret(comptePret);
        contratPret.setMontantEmprunte(montant);
        contratPret.setDateDebut(LocalDate.from(datePret));
        contratPret.setDureeTotaleMois(duree);
        contratPret.setDateFinTheorique(contratPret.getDateDebut().plusMonths(duree));
        contratPret.setTypePaiement(typePaiement);

        contratPretRepository.save(contratPret);

        // Créer les échéances en fonction de la périodicité
        List<Echeance> echeances = creationEcheancesAvecPeriodicite(contratPret, periodicite);
        for (Echeance echeance : echeances) {
            echeance.setContratPret(contratPret);
            echeanceRepository.save(echeance);

            MvtStatutEcheance mvtStatutEcheance = new MvtStatutEcheance();
            mvtStatutEcheance.setEcheance(echeance);
            mvtStatutEcheance.setStatutEcheance(statutEcheanceRepository.findById(2L));
            mvtStatutEcheance.setDateChangement(LocalDateTime.now());
            mvtStatutEcheanceRepository.save(mvtStatutEcheance);
        }
    }

    // Nouvelle méthode pour générer les échéances selon la périodicité
    public List<Echeance> creationEcheancesAvecPeriodicite(ContratPret contratPret, int periodicite) {
        List<Echeance> echeances = new ArrayList<>();

        TauxInteret tauxInteret = tauxInteretRepository.findTauxInteretByDate(contratPret.getDateDebut().atStartOfDay());
        if (tauxInteret == null) {
            throw new IllegalStateException("Aucun taux d'intérêt disponible.");
        }

        BigDecimal montantEmprunte = contratPret.getMontantEmprunte();
        int duree = contratPret.getDureeTotaleMois();
        int nbEcheances = duree / periodicite;
        if (duree % periodicite != 0) nbEcheances++;
        BigDecimal capital = montantEmprunte.divide(BigDecimal.valueOf(nbEcheances), 2, RoundingMode.HALF_UP);
        BigDecimal tauxPeriode = tauxInteret.getValeur().divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(periodicite));

        LocalDate dateEcheance = contratPret.getDateDebut().plusMonths(periodicite);

        for (int i = 0; i < nbEcheances; i++) {
            Echeance echeance = new Echeance();
            echeance.setDateEcheance(dateEcheance);
            echeance.setMontantCapital(capital);
            echeance.setMontantInteret(capital.multiply(tauxPeriode));
            echeances.add(echeance);

            dateEcheance = dateEcheance.plusMonths(periodicite);

        }
        return echeances;
    }

    @Override
    public List<ComptePretWithStatusDTO> findComptesByClientId(Long clientId) {
        List<ComptePretWithStatusDTO> comptes = new ArrayList<>();
        for (ComptePretWithStatusDTO compte : comptePretRepository.findByClientId(clientId)) {
            compte.setTauxInteret(findTauxInteretByDate(compte.getDateCreation()));
            comptes.add(compte);
        }
        return comptes;
    }

    @Override
    public BigDecimal findTauxInteretByDate(LocalDateTime date) {
        TauxInteret tauxInteret = tauxInteretRepository.findTauxInteretByDate(date);
        return tauxInteret != null ? tauxInteret.getValeur() : BigDecimal.ZERO;
    }

    @Override
    public List<ContratPretDTO> findContratsByComptePretId(Long comptePretId) {
        List<ContratPretDTO> contratsDTO = new ArrayList<>();
        List<ContratPretDTO> contrats = contratPretRepository.findByComptePretId(comptePretId);
        for (ContratPretDTO contrat : contrats) {
            // Set tauxInteret
            LocalDateTime dateDebut = LocalDate.parse(contrat.getDateDebutContrat()).atStartOfDay();
            contrat.setTauxInteret(findTauxInteretByDate(dateDebut));

            // Get mensualite from first echeance of the contrat
            List<Echeance> echeances = echeanceRepository.findByContratPretId(contrat.getId());
            if (!echeances.isEmpty()) {
                Echeance firstEcheance = echeances.get(0);
                BigDecimal mensualite = firstEcheance.getMontantCapital().add(firstEcheance.getMontantInteret());
                contrat.setMensualite(mensualite);

                // montantTotalArembourser = mensualite * nbEcheances
                contrat.setMontantTotalArembourser(mensualite.multiply(BigDecimal.valueOf(echeances.size())));

                // montantRembourse : sum of paid echeances
                BigDecimal montantRembourse = BigDecimal.ZERO;
                for (Echeance e : echeances) {
                    if (isEcheancePayee(e.getId())) {
                        montantRembourse = montantRembourse.add(e.getMontantCapital().add(e.getMontantInteret()));
                    }
                }
                contrat.setMontantRembourse(montantRembourse);
            } else {
                contrat.setMensualite(BigDecimal.ZERO);
                contrat.setMontantTotalArembourser(BigDecimal.ZERO);
                contrat.setMontantRembourse(BigDecimal.ZERO);
            }

            // Set montantRestantDu = total - rembourse
            contrat.setMontantRestantDu(contrat.getMontantTotalArembourser().subtract(contrat.getMontantRembourse()));

            contratsDTO.add(contrat);
        }
        return contratsDTO;
    }

    @Override
    @Transactional
    public void payerEcheance(Long echeanceId) {
        Echeance echeance = echeanceRepository.findById(echeanceId);
        if (echeance == null) {
            throw new IllegalArgumentException("Échéance non trouvée avec l'ID: " + echeanceId);
        }

        // Check if already paid
        if (isEcheancePayee(echeanceId)) {
            throw new IllegalStateException("Échéance déjà payée");
        }

        // Update comptePret soldeRestantDu
        ComptePret comptePret = echeance.getContratPret().getComptePret();
        BigDecimal montant = echeance.getMontantCapital().add(echeance.getMontantInteret());
        comptePret.setSoldeRestantDu(comptePret.getSoldeRestantDu().subtract(montant));
        comptePretRepository.save(comptePret);

        // Create new MvtStatutEcheance with PAYÉ status (id=2)
        MvtStatutEcheance mvt = new MvtStatutEcheance();
        mvt.setEcheance(echeance);
        mvt.setStatutEcheance(statutEcheanceRepository.findById(2L)); // PAYÉ
        mvt.setDateChangement(LocalDateTime.now());
        mvtStatutEcheanceRepository.save(mvt);
    }

    private boolean isEcheancePayee(Long echeanceId) {
        // Check if the echeance has a 'PAYÉ' status
        MvtStatutEcheance mvt = mvtStatutEcheanceRepository.findLatestByEcheanceId(echeanceId);
        return mvt != null && mvt.getStatutEcheance().getId().equals(1L);
    }

    @Override
    public List<EcheanceDTO> findEcheancesByContratId(Long contratId) {
        return echeanceRepository.findEcheancesDTOByContratPretId(contratId);
    }
}
