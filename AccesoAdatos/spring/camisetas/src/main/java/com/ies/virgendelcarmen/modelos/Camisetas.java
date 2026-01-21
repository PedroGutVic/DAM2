/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ies.virgendelcarmen.modelos;

import com.ies.virgendelcarmen.modelos.ENUM.TallasCamisetas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author Pedro Gutiérrez Vico
 */

@Entity
@Data
@NoArgsConstructor
public class Camisetas {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private TallasCamisetas talla;
    private String color;
    private String marca;
    private int stock;
    private double precio;
    private boolean activo;
}
