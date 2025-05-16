package com.example.biblioteca.services;

import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.models.Espacio;
import com.example.biblioteca.models.Reserva;
import com.example.biblioteca.repositories.EspacioRepository;
import com.example.biblioteca.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {
    @Autowired
    private EspacioRepository espacioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public void guardarReserva(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    public Optional<Reserva> actualizarReserva(Long id, Reserva reservaActualizada){
        return reservaRepository.findById(id).map(reserva -> {
            reserva.setEspacio(reservaActualizada.getEspacio());
            reserva.setPlaca(reservaActualizada.getPlaca());
            reserva.setHoraLlegada(reservaActualizada.getHoraLlegada());
            reserva.setHoraSalida(reservaActualizada.getHoraSalida());
            return  reservaRepository.save(reserva);

        });
    }

    public Optional<Reserva> eliminarReserva(Long id) {

        Optional<Reserva> reservaOptional = reservaRepository.findById(id);
        reservaOptional.ifPresent(reserva -> reservaRepository.delete(reserva));
        return Optional.empty();
    }
}
