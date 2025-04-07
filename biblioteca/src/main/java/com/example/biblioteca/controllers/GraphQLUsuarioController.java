package com.example.biblioteca.controllers;

import com.example.biblioteca.dto.MensajeResponse;
import com.example.biblioteca.models.Usuario;
import com.example.biblioteca.graphql.InputUsuario;
import com.example.biblioteca.services.GraphQLUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQLUsuarioController {

    @Autowired
    GraphQLUsuarioService graphQLUsuarioService;

    @QueryMapping(name = "usuariosActivos")
    public List<Usuario> obtenerUsuariosActivos() {
        return graphQLUsuarioService.obtenerUsuariosActivos();
    }

    @MutationMapping
    public MensajeResponse registrarUsuario(@Argument ("UsuarioInput")InputUsuario inputUsuario) {
        Usuario usuario = new Usuario();
        usuario.setNombre(inputUsuario.getNombre());
        usuario.setEmail(inputUsuario.getEmail());
        usuario.setDireccion(inputUsuario.getDireccion());
        usuario.setCedula(inputUsuario.getCedula());
        usuario.setEstado(inputUsuario.getEstado() != null ? inputUsuario.getEstado() : "ACTIVO");
        return graphQLUsuarioService.registrarUsuario(usuario);
    }

    @MutationMapping
    public MensajeResponse actualizarUsuario(@Argument Long id, @Argument("UsuarioInput") InputUsuario inputUsuario) {
        Usuario usuario = new Usuario();
        usuario.setNombre(inputUsuario.getNombre());
        usuario.setEmail(inputUsuario.getEmail());
        usuario.setDireccion(inputUsuario.getDireccion());
        usuario.setCedula(inputUsuario.getCedula());
        usuario.setEstado(inputUsuario.getEstado());
        //usuario.setEstado(inputUsuario.getEstado() == null ? inputUsuario.getEstado() : "ACTIVO");
        return graphQLUsuarioService.actualizarUsuario(id, usuario);
    }

    @MutationMapping
    public MensajeResponse eliminarUsuario(@Argument Long id) {
        return graphQLUsuarioService.eliminarUsuario(id);
    }

}
