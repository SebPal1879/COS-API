package com.example.biblioteca;

import com.example.biblioteca.models.Rol;
import com.example.biblioteca.models.Usuario;
import com.example.biblioteca.models.UsuarioRol;
import com.example.biblioteca.services.UsuarioService;
import com.example.biblioteca.services.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BibliotecaApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	private UsuarioServiceImpl usuarioService;

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Usuario usuario = new Usuario();
//
//		usuario.setNombre("user7");
//		usuario.setDireccion("calle7");
//		usuario.setEmail("7777@7777.com");
//		usuario.setEstado("ACTIVO");
//		usuario.setCedula("77777777");
//		usuario.setPassword(passwordEncoder.encode("777777"));
//		usuario.setUsername("user7");
//		usuario.setEnabled(true);
//
//		Rol rol = new Rol();
//		rol.setRolId(1L);
//		rol.setNombre("ADMIN");
//
//		Set<UsuarioRol> usuarioRoles = new HashSet<>();
//		UsuarioRol usuarioRol = new UsuarioRol();
//		usuarioRol.setRol(rol);
//		usuarioRol.setUsuario(usuario);
//		usuarioRoles.add(usuarioRol);
//
//		Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario,usuarioRoles);
//		System.out.println(usuarioGuardado.getCedula());

	}
}
