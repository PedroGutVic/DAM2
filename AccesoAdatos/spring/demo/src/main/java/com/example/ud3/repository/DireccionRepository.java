
package com.example.ud3.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ud3.model.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    List<Direccion> findByUsuarioId(Long idUsuario);
}
