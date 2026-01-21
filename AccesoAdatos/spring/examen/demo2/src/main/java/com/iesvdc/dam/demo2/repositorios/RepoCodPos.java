package com.iesvdc.dam.demo2.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iesvdc.dam.demo2.modelos.CodPos;

@Repository
public interface RepoCodPos extends JpaRepository <CodPos, Integer> {
    List<CodPos> findAll();
    Optional<CodPos> findByCp(Integer cp);
}
