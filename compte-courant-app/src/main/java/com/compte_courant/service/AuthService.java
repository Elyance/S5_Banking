package com.compte_courant.service;

import jakarta.ejb.Stateful;
import jakarta.ejb.Remove;
import jakarta.inject.Inject;
import com.compte_courant.entity.Utilisateur;
import com.compte_courant.entity.Direction;
import com.compte_courant.repository.UtilisateurRepository;
import com.compte_courant.repository.DirectionRepository;
import com.compte_courant.repository.ActionRoleRepository;
import com.compte_courant.remote.AuthServiceRemote;
import com.compte_courant.dto.UtilisateurDTO;
import com.compte_courant.dto.DirectionDTO;
import com.compte_courant.dto.ActionRoleDTO;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
public class AuthService implements AuthServiceRemote {
    @Inject
    UtilisateurRepository utilisateurRepository;

    @Inject
    DirectionRepository directionRepository;

    @Inject
    ActionRoleRepository actionRoleRepository;

    private Utilisateur currentUser;

    @Override
    public boolean login(String login, String password) {
        Utilisateur utilisateur = utilisateurRepository.findByLogin(login);
        if (utilisateur != null && utilisateur.getMdp().equals(password)) {
            this.currentUser = utilisateur;
            return true;
        }
        return false;
    }

    @Override
    @Remove
    public void logout() {
        this.currentUser = null;
    }

    @Override
    public UtilisateurDTO getCurrentUser() {
        if (this.currentUser != null) {
            return new UtilisateurDTO(this.currentUser.getId(), this.currentUser.getLogin(), this.currentUser.getRole());
        }
        return null;
    }

    @Override
    public boolean isLoggedIn() {
        return this.currentUser != null;
    }

    @Override
    public DirectionDTO getCurrentUserDirection() {
        if (this.currentUser != null && this.currentUser.getIdDirection() != null) {
            Direction dir = directionRepository.findById(this.currentUser.getIdDirection());
            if (dir != null) {
                return new DirectionDTO(dir.getId(), dir.getLibelle(), dir.getNiveau());
            }
        }
        return null;
    }

    @Override
    public List<ActionRoleDTO> getActionRoles() {
        return actionRoleRepository.findAll().stream()
                .map(ar -> new ActionRoleDTO(ar.getId(), ar.getNomTable(), ar.getAction(), ar.getRole()))
                .collect(Collectors.toList());
    }
}
