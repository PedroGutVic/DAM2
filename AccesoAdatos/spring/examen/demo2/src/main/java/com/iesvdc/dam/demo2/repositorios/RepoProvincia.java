package com.iesvdc.dam.demo2.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iesvdc.dam.demo2.modelos.Provincia;

@Repository
public interface RepoProvincia extends JpaRepository<Provincia, Integer> {
    List<Provincia> findByComunidad(String comunidad);
    Optional<Provincia> findByCodigo(int codigo);
}
