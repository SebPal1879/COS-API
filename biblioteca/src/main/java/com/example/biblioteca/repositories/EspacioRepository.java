package com.example.biblioteca.repositories;

import com.example.biblioteca.models.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EspacioRepository extends JpaRepository<Espacio, Long> {
    List<Espacio> findByTipo(String tipo);
    List<Espacio> findByDisponible(Boolean disponible);

}
