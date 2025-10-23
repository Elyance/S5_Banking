package com.compte_courant.dto;

import java.io.Serializable;

public class UtilisateurDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String login;
    private Integer role;

    public UtilisateurDTO() {}

    public UtilisateurDTO(Long id, String login, Integer role) {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public Integer getRole() { return role; }
    public void setRole(Integer role) { this.role = role; }
}