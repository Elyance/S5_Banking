package com.compte_courant.dto;

import java.io.Serializable;

public class ActionRoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nomTable;
    private String action;
    private Integer role;

    public ActionRoleDTO() {}

    public ActionRoleDTO(Long id, String nomTable, String action, Integer role) {
        this.id = id;
        this.nomTable = nomTable;
        this.action = action;
        this.role = role;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomTable() { return nomTable; }
    public void setNomTable(String nomTable) { this.nomTable = nomTable; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Integer getRole() { return role; }
    public void setRole(Integer role) { this.role = role; }
}