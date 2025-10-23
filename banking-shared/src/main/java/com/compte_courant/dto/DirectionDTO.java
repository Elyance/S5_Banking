package com.compte_courant.dto;

import java.io.Serializable;

public class DirectionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String libelle;
    private Integer niveau;

    public DirectionDTO() {}

    public DirectionDTO(Long id, String libelle, Integer niveau) {
        this.id = id;
        this.libelle = libelle;
        this.niveau = niveau;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public Integer getNiveau() { return niveau; }
    public void setNiveau(Integer niveau) { this.niveau = niveau; }
}