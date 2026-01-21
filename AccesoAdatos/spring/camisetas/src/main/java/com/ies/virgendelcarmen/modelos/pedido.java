/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ies.virgendelcarmen.modelos;

import java.sql.Date;

import com.ies.virgendelcarmen.modelos.ENUM.Estado;

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
public class pedido {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private Date fechaPedido;
    private Estado estado;
    private int idUsuario;
    private double total;
    
}
