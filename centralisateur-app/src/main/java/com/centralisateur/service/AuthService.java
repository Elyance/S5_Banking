package com.centralisateur.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.compte_courant.remote.AuthServiceRemote;
import com.compte_courant.dto.UtilisateurDTO;
import com.compte_courant.dto.DirectionDTO;
import com.compte_courant.dto.ActionRoleDTO;
import java.util.List;

public class AuthService {
    private AuthServiceRemote authServiceRemote;

    private AuthServiceRemote getRemote() {
        if (authServiceRemote == null) {
            try {
                authServiceRemote = (AuthServiceRemote) new InitialContext().lookup(
                    "java:global/compte-courant-app/AuthService!com.compte_courant.remote.AuthServiceRemote"
                );
            } catch (NamingException e) {
                throw new RuntimeException("Erreur lors du lookup EJB distant", e);
            }
        }
        return authServiceRemote;
    }

    public boolean login(String login, String password) {
        return getRemote().login(login, password);
    }

    public void logout() {
        getRemote().logout();
    }

    public UtilisateurDTO getCurrentUser() {
        return getRemote().getCurrentUser();
    }

    public boolean isLoggedIn() {
        return getRemote().isLoggedIn();
    }

    public DirectionDTO getCurrentUserDirection() {
        return getRemote().getCurrentUserDirection();
    }

    public List<ActionRoleDTO> getActionRoles() {
        return getRemote().getActionRoles();
    }
}