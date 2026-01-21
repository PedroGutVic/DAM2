package com.iesvdc.dam.demo2.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iesvdc.dam.demo2.modelos.Usuario;


@Repository
public interface RepoUsuario extends JpaRepository<Usuario, Long>{

    Optional<Usuario>  findByUsername(String username);
    Optional<Usuario>  findByEmail(String email);
    Optional<Usuario>  findByTelefono(int telefono);
    List<Usuario> findById(Integer id);
    
}
