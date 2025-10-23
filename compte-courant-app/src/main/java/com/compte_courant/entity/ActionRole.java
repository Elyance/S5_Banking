package com.compte_courant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "action_role")
public class ActionRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nomTable", nullable = false, length = 100)
    private String nomTable;

    @Column(nullable = false, length = 50)
    private String action;

    @Column(nullable = false)
    private Integer role;

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