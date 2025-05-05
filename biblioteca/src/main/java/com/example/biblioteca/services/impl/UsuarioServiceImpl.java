package com.example.biblioteca.services.impl;

import com.example.biblioteca.models.Usuario;
import com.example.biblioteca.models.UsuarioRol;
import com.example.biblioteca.repositories.RolRepository;
import com.example.biblioteca.repositories.UsuarioRepository;
import com.example.biblioteca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioServiceImpl extends UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    public Usuario guardarUsuario(Usuario usuario, Set <UsuarioRol> usuarioRoles) throws Exception{
        Usuario usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());

        if (usuarioLocal != null) {
            System.out.println("El usuario ya existe");
            throw new Exception("El usuario ya está presente");
        } else {
            for (UsuarioRol usuarioRol:usuarioRoles){
                rolRepository.save(usuarioRol.getRol());
            }
            usuario.getUsuarioRoles().addAll(usuarioRoles);
            usuarioLocal = usuarioRepository.save(usuario);
        }
        return usuarioLocal;
    }

    public Usuario obtenerUsuario(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public void eliminarUsuario2(Long usuarioId){
        usuarioRepository.deleteById(usuarioId);
    }
}
