package com.centralisateur.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.*;
import java.util.List;

import com.centralisateur.dto.ClientWithStatusDTO;

import com.centralisateur.repository.*;
import com.centralisateur.entity.*;

@ApplicationScoped
public class ClientService {
    
    @Inject
    ClientRepository clientRepository;

    @Inject
    MvtClientRepository mvtClientRepository;

    @Inject
    StatutClientRepository statutClientRepository;

    public List<ClientWithStatusDTO> getClientsWithCurrentStatus() {
        return clientRepository.findAllClientsWithCurrentStatus();
    }

    @Transactional
    public void saveClient(Client client) {
        // Enregistrer le client
        clientRepository.save(client);

        MvtClient mvtClient = new MvtClient();
        mvtClient.setClient(client);
        mvtClient.setStatutClient(statutClientRepository.findById(1L));
        mvtClient.setDateMvt(LocalDateTime.now());
        // Enregistrer le mouvement initial avec le statut "Actif"
        mvtClientRepository.save(mvtClient);
    }

    public long countClients() {
        return clientRepository.count();
    }

    public Client findClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Transactional
    public void updateClient(Client client) {
        clientRepository.update(client);
    }

    @Transactional
    public void changeClientStatus(Long clientId, Long statutId) {
        Client client = clientRepository.findById(clientId);
        StatutClient statutClient = statutClientRepository.findById(statutId);
        if (client != null && statutClient != null) {
            MvtClient mvtClient = new MvtClient();
            mvtClient.setClient(client);
            mvtClient.setStatutClient(statutClient);
            mvtClient.setDateMvt(LocalDateTime.now());
            mvtClientRepository.save(mvtClient);
        }
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

}
