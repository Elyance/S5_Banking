package com.compte_courant.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String login;

    @Column(nullable = false, length = 255)
    private String mdp;

    @Column(name = "id_direction")
    private Long idDirection;

    @Column(nullable = false)
    private Integer role;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getMdp() { return mdp; }
    public void setMdp(String mdp) { this.mdp = mdp; }

    public Long getIdDirection() { return idDirection; }
    public void setIdDirection(Long idDirection) { this.idDirection = idDirection; }

    public Integer getRole() { return role; }
    public void setRole(Integer role) { this.role = role; }
}