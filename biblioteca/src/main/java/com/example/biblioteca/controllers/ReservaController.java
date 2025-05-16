package com.example.biblioteca.controllers;

import com.example.biblioteca.models.Reserva;
import com.example.biblioteca.repositories.EspacioRepository;
import com.example.biblioteca.models.Espacio;
import com.example.biblioteca.repositories.ReservaRepository;
import com.example.biblioteca.services.EspacioService;
import com.example.biblioteca.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController //Clase controladora de un servicio REST
@RequestMapping("/reservas")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservaController {

    @Autowired
    ReservaService reservaService;

    @Autowired
    EspacioService espacioService;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EspacioRepository espacioRepository;

    @PostMapping("/guardar")
    public Reserva guardarReserva(@RequestBody Map<String,String> map) throws Exception {
        Reserva reserva = new Reserva();
        reserva.setPlaca(map.get("placa"));
        reserva.setHoraLlegada(LocalDateTime.now());
        Optional<Espacio> espacioOpt = espacioRepository.findById(Long.parseLong(map.get("espacio")));
        Espacio espacio = espacioOpt.get();
        reserva.setEspacio(espacio);
        espacio.setDisponible(false);
        System.out.println("reserva.getPlaca() + reserva.getEspacio() + reserva.getHoraLlegada() + reserva.getHoraSalida() = " + reserva.getPlaca() + reserva.getEspacio() + reserva.getHoraLlegada() + reserva.getHoraSalida());
        espacioService.actualizarEspacio(espacio.getId(),espacio);
        return reservaRepository.save(reserva);
    }

    @GetMapping("/{id}")
    public Optional<Reserva> obtenerReserva(@PathVariable("id") Long id){
        return reservaRepository.findById(id);
    }

    @PutMapping("/actualizar/{id}")
    public Optional<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Map<String,String> map) {
        Reserva reserva = new Reserva();
        Optional<Espacio> espacioOpt = espacioRepository.findById(Long.parseLong(map.get("espacio")));
        Espacio espacio = espacioOpt.get();

        Optional<Reserva> reservaExistente = reservaRepository.findById(id);
        reserva = reservaExistente.get();
//        if (reservaExistente.isPresent()){
//            System.out.println("No existe la reserva");
//            return Optional.empty();
//        }
        if(reserva.getEspacio().getId() != Long.parseLong(map.get("espacio"))){
            List<Espacio> ocupados = espacioRepository.findByDisponible(false);
            for(Espacio a: ocupados){
                if (a.getId() == Long.parseLong(map.get("espacio"))){
                    System.out.println("El espacio está ocupado");
                    return Optional.empty();
                }
            }
            Optional<Espacio> espacioLiberado = espacioRepository.findById(reserva.getEspacio().getId());
            Espacio espacioLibre = espacioLiberado.get();
            espacioLibre.setDisponible(true);
            espacioService.actualizarEspacio(reserva.getEspacio().getId(),espacioLibre);
        }
        reserva.setEspacio(espacio);
        espacio.setDisponible(false);

        reserva.setPlaca(map.get("placa"));
        reserva.setHoraLlegada(reservaRepository.findById(id).get().getHoraLlegada());
        espacioService.actualizarEspacio(espacio.getId(), espacio);
        return reservaService.actualizarReserva(id, reserva);
    }

    @DeleteMapping("/eliminar/{id}")
    public Optional<Reserva> eliminarReserva(@PathVariable Long id) {
        return reservaService.eliminarReserva(id);
    }

}