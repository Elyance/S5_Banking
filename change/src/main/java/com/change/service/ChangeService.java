package com.change.service;

import com.change.dto.ChangeDTO;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import jakarta.ejb.Stateless;
import com.change.remote.ChangeServiceRemote;


@Stateless
public class ChangeService implements ChangeServiceRemote {
    @Override
    public List<ChangeDTO> loadChangeFromCSV() {
        List<ChangeDTO> changes = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("change.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            String line;
            boolean firstLine = true;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String devise = parts[0];
                    LocalDate dateDebut = LocalDate.parse(parts[1], formatter);
                    LocalDate dateFin = LocalDate.parse(parts[2], formatter);
                    BigDecimal change = new BigDecimal(parts[3]);

                    ChangeDTO dto = new ChangeDTO(devise, dateDebut.toString(), dateFin.toString(), change);
                    changes.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return changes;
    }

    @Override
    public ChangeDTO rechercherChange(String devise, LocalDate date) {
        System.out.println("Recherche de change pour devise: " + devise + ", date: " + date);
        List<ChangeDTO> changes = loadChangeFromCSV();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (ChangeDTO changeDTO : changes) {
            // Parse les dates String en LocalDate pour la comparaison
            LocalDate debut = LocalDate.parse(changeDTO.getDateDebut(), formatter);
            LocalDate fin = LocalDate.parse(changeDTO.getDateFin(), formatter);
            System.out.println("Vérification change: " + changeDTO + ", debut: " + debut + ", fin: " + fin);
            if (changeDTO.getDevise().equals(devise) && !date.isBefore(debut) && !date.isAfter(fin)) {
                System.out.println("Change trouvé: " + changeDTO);
                return changeDTO;
            }
        }
        System.out.println("Aucun change trouvé pour devise: " + devise);
        return null;
    }

    @Override
    public BigDecimal calculate(BigDecimal montant, String devise, LocalDate date) {
        System.out.println("Calculating for montant: " + montant + ", devise: " + devise);
        if ("Ariary".equals(devise)) {
            System.out.println("Devise is Ariary, returning montant as is: " + montant);
            return montant;
        }
        ChangeDTO changeDTO = rechercherChange(devise, date);
        if (changeDTO != null) {
            BigDecimal result = montant.multiply(changeDTO.getChange());
            System.out.println("Change found: " + changeDTO + ", result: " + result);
            return result;
        } else {
            System.out.println("Devise not found: " + devise);
            throw new RuntimeException("Devise '" + devise + "' not found in change data");
        }
    }

    @Override
    public List<String> getListeDevises() {
        List<ChangeDTO> changes = loadChangeFromCSV();
        System.out.println("Loaded changes: " + changes);
        List<String> devises = new ArrayList<>();
        devises.add("Ariary");
        for (ChangeDTO change : changes) {
            if (!devises.contains(change.getDevise())) {
                devises.add(change.getDevise());
            }
        }
        return devises;
    }
}