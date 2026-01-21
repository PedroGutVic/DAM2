
package com.example.ud3.model;

import jakarta.persistence.*;

@Entity
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoVia;
    private String nombreVia;
    private String numero;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private CodPos codPos;

    public Long getId() { return id; }
    public String getTipoVia() { return tipoVia; }
    public void setTipoVia(String tipoVia) { this.tipoVia = tipoVia; }
    public String getNombreVia() { return nombreVia; }
    public void setNombreVia(String nombreVia) { this.nombreVia = nombreVia; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public CodPos getCodPos() { return codPos; }
    public void setCodPos(CodPos codPos) { this.codPos = codPos; }
}
