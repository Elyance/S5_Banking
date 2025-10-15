package com.centralisateur.service;

import com.centralisateur.repository.AdminBanqueRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AdminService {
    @Inject
    private AdminBanqueRepository adminBanqueRepository;

    // Login - retourne un administrateur par son email et son mot de passe
    public boolean login(String email, String password) {
        return adminBanqueRepository.getByPasswordAndEmail(email, password) != null;
    }
}