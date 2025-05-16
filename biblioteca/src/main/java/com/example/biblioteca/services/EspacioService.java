package com.example.biblioteca.services;

import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.models.Espacio;
import com.example.biblioteca.repositories.EspacioRepository;
import com.example.biblioteca.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspacioService {
    @Autowired
    private EspacioRepository espacioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public void guardarEspacio(Espacio espacio) {
        espacioRepository.save(espacio);
    }

    public Optional<Espacio> actualizarEspacio(Long id, Espacio espacioActualizar){
        return espacioRepository.findById(id).map(espacio -> {
                    espacio.setTipo(espacioActualizar.getTipo());
                    espacio.setDisponible(espacioActualizar.getDisponible());
                    System.out.println("espacio +" + espacioActualizar.getTipo());
                    return  espacioRepository.save(espacio);

        });
    }

    public Optional<Espacio> eliminarEspacio(Long id) {
        Optional<Espacio> espacioOptional = espacioRepository.findById(id);
        espacioOptional.ifPresent(espacio -> espacioRepository.delete(espacio));
        return Optional.empty();

    }
}
