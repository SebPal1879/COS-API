package com.example.biblioteca.controllers;

import com.example.biblioteca.repositories.EspacioRepository;
import com.example.biblioteca.models.Espacio;
import com.example.biblioteca.services.EspacioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //Clase controladora de un servicio REST
@RequestMapping("/espacios")
@CrossOrigin(origins = "http://localhost:4200")
public class EspacioController {

    @Autowired
    EspacioService espacioService;

    @Autowired
    private EspacioRepository espacioRepository;

    @PostMapping("/guardar")
    public Espacio guardarEspacio(@RequestBody Espacio espacio) throws Exception {
        return espacioRepository.save(espacio);
    }

    @GetMapping("/{id}")
    public Optional<Espacio> obtenerEspacio(@PathVariable("id") Long id){
        return espacioRepository.findById(id);
    }

    @GetMapping("/disponibles")
    public List<Espacio> obtenerEspaciosDisponibles() {
        return espacioRepository.findByDisponible(true);
    }

    @GetMapping("/ocupados")
    public List<Espacio> obtenerEspaciosOcupados() {
        return espacioRepository.findByDisponible(false);
    }

    @PutMapping("/actualizar/{id}")
    public Optional<Espacio> actualizarEspacio(@PathVariable Long id, @RequestBody Espacio espacio) {
        return espacioService.actualizarEspacio(id, espacio);
    }

    @DeleteMapping("/eliminar/{id}")
    public Optional<Espacio> eliminarEspacio(@PathVariable Long id) {
        return espacioService.eliminarEspacio(id);
    }

    /*@GetMapping("/autenticar")
    public Usuario autenticar(String email, String password){
        return usuarioService.autenticarUsuario(email, password);
    }*/

}