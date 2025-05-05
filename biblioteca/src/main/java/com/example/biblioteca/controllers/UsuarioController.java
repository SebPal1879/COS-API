package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.models.Rol;
import com.example.biblioteca.models.Usuario;
import com.example.biblioteca.models.UsuarioRol;
import com.example.biblioteca.services.UsuarioService;
import com.example.biblioteca.services.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.biblioteca.repositories.UsuarioRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController //Clase controladora de un servicio REST
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    UsuarioServiceImpl usuarioService2;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/")
    public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception {
        Set<UsuarioRol> usuarioRoles = new HashSet<>();

        Rol rol = new Rol();
        rol.setRolId(2L);
        rol.setNombre("NORMAL");

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);

        usuarioRoles.add(usuarioRol);
        return usuarioService2.guardarUsuario(usuario,usuarioRoles);
    }

    @GetMapping("/{username}")
    public Usuario obtenerUsuario(@PathVariable("username") String username){
        Usuario usuarioLocal = usuarioRepository.findByUsername(username);
        return usuarioLocal;
    }

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
