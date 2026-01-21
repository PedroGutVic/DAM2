
package com.example.ud3.model;

import jakarta.persistence.*;

@Entity
public class CodPos {

    @Id
    private String cp;
    private String municipio;

    @ManyToOne
    private Provincia provincia;

    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }
    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }
    public Provincia getProvincia() { return provincia; }
    public void setProvincia(Provincia provincia) { this.provincia = provincia; }
}
