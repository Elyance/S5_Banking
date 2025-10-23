package com.compte_courant.remote;

import jakarta.ejb.Remote;
import com.compte_courant.dto.UtilisateurDTO;
import com.compte_courant.dto.DirectionDTO;
import com.compte_courant.dto.ActionRoleDTO;
import java.util.List;

@Remote
public interface AuthServiceRemote {
    boolean login(String login, String password);
    void logout();
    UtilisateurDTO getCurrentUser();
    boolean isLoggedIn();
    DirectionDTO getCurrentUserDirection();
    List<ActionRoleDTO> getActionRoles();
}