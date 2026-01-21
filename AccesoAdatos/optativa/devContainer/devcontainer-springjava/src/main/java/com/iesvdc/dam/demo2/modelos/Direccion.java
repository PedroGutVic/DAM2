package com.iesvdc.dam.demo2.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 50)
    private String tipoVia;
    @Column(nullable = false, length = 150)
    private String nombreVia;
    @Column(nullable = false)
    private int numero;
    @ManyToOne(optional = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    private CodPos codPos;
}
