package com.iesvdc.dam.demo2.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CodPos {
    @Id
    private Integer cp;
    @Column(nullable = false, length = 50)
    private String municipio;
    @ManyToOne(optional = false)
    private Provincia provincia;
}
