package com.example.biblioteca;

import com.example.biblioteca.models.*;
import com.example.biblioteca.repositories.ReservaRepository;
import com.example.biblioteca.services.UsuarioService;
import com.example.biblioteca.services.impl.UsuarioServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.biblioteca.services.EspacioService;
import com.example.biblioteca.services.ReservaService;

import com.example.biblioteca.repositories.EspacioRepository;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class BibliotecaApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EspacioRepository espacioRepository;

	@Autowired
	private UsuarioServiceImpl usuarioService;

	@Autowired
	private EspacioService espacioService;

	@Autowired
	private ReservaService reservaService;

	@Autowired
	private ReservaRepository reservaRepository;

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		Espacio espacio = new Espacio();
		espacio.setDisponible(true);
		espacio.setTipo("Carro");

		espacioService.guardarEspacio(espacio);*/

		/*
		Reserva reserva = new Reserva();
		reserva.setPlaca("ABC-123");
		reserva.setHoraLlegada(LocalDateTime.now());
		Optional<Espacio> espacioOpt = espacioRepository.findById(1L);
		Espacio espacio = espacioOpt.get();
		reserva.setEspacio(espacio);

		reservaService.guardarReserva(reserva);*/
/*
		Optional<Reserva> reserva = reservaRepository.findById(1L);
		Reserva reserva1 = reserva.get();

		reserva1.setHoraSalida(LocalDateTime.now());

		reservaService.actualizarReserva(1L, reserva1);*/
	}
}
