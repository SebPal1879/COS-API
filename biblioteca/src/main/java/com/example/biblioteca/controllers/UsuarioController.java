package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.models.Usuario;
import com.example.biblioteca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Clase controladora de un servicio REST
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> obtenerUsuariosActivos() {
        return usuarioService.obtenerUsuariosActivos();
    }

    @PostMapping("/registrar")
    public ResponseEntity<MensajeResponse> registrarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<MensajeResponse> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MensajeResponse> eliminarUsuario(@PathVariable Long id) {
        return usuarioService.eliminarUsuario(id);
    }

    /*@GetMapping("/autenticar")
    public Usuario autenticar(String email, String password){
        return usuarioService.autenticarUsuario(email, password);
    }*/


}
