package com.example.biblioteca.services;

import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.graphql.InputUsuario;
import com.example.biblioteca.models.Usuario;
import com.example.biblioteca.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraphQLUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerUsuariosActivos() {
        return usuarioRepository.findByEstado("ACTIVO");
    }

    public MensajeResponse  registrarUsuario(Usuario usuario) {
        //usuario.setEstado("ACTIVO");
        usuarioRepository.save(usuario);
        return new MensajeResponse("Usuario registrado correctamente");
    }

    public MensajeResponse actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setDireccion(usuarioActualizado.getDireccion());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuarioRepository.save(usuario);
            return new MensajeResponse("Usuario actualizado correctamente");
        }).orElse(new MensajeResponse("Usuario no encontrado"));
    }

    public MensajeResponse eliminarUsuario(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setEstado("ELIMINADO");
            usuarioRepository.save(usuario);
            return new MensajeResponse("Usuario eliminado correctamente");
        }).orElse(new MensajeResponse("Usuario no encontrado"));
    }


}
