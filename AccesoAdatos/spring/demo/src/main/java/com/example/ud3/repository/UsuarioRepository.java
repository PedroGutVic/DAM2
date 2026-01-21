
package com.example.ud3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ud3.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
