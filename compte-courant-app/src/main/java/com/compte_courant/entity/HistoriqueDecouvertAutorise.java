package com.compte_courant.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique_devouvert_autorise")
public class HistoriqueDecouvertAutorise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value", nullable = false, precision = 15, scale = 2)
    private BigDecimal value;

    @Column(name = "date_modification", nullable = false)
    private LocalDateTime dateModification;

    // Constructors
    public HistoriqueDecouvertAutorise() {}

    public HistoriqueDecouvertAutorise(BigDecimal value) {
        this.value = value;
        this.dateModification = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    @Override
    public String toString() {
        return "HistoriqueDecouvertAutorise{" +
                "id=" + id +
                ", value=" + value +
                ", dateModification=" + dateModification +
                '}';
    }
}