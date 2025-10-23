package com.change.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ChangeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String devise;
    private String dateDebut;
    private String dateFin;
    private BigDecimal change;

    public ChangeDTO() {}

    public ChangeDTO(String devise, String dateDebut, String dateFin, BigDecimal change) {
        this.devise = devise;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.change = change;
    }

    // Getters and setters
    public String getDevise() { return devise; }
    public void setDevise(String devise) { this.devise = devise; }

    public String getDateDebut() { return dateDebut; }
    public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }

    public String getDateFin() { return dateFin; }
    public void setDateFin(String dateFin) { this.dateFin = dateFin; }

    public BigDecimal getChange() { return change; }
    public void setChange(BigDecimal change) { this.change = change; }
}
