package com.example.biblioteca.graphql.suscriptions;

import com.example.biblioteca.models.Usuario;
import org.springframework.stereotype.Component;
import reactor.core.publisher.*;

@Component
public class UsuarioPublisher {

// En UsuarioPublisher.java

    private final FluxProcessor<Usuario, Usuario> usuarioActualizadoProcessor = DirectProcessor.<Usuario>create().serialize();
    private final FluxSink<Usuario> usuarioActualizadoSink = usuarioActualizadoProcessor.sink();
    private final FluxProcessor<Usuario, Usuario> usuarioEliminadoProcessor = DirectProcessor.<Usuario>create().serialize();
    private final FluxSink<Usuario> usuarioEliminadoSink = usuarioEliminadoProcessor.sink();

    private final FluxProcessor<Usuario, Usuario> usuarioRegistradoProcessor = DirectProcessor.<Usuario>create().serialize();
    private final FluxSink<Usuario> usuarioRegistradoSink = usuarioRegistradoProcessor.sink();


    public void publicarUsuarioActualizado(Usuario usuario) {
        usuarioActualizadoSink.next(usuario);
    }

    public void publicarUsuarioEliminado(Usuario usuario) {
        usuarioEliminadoSink.next(usuario);
    }

    public void publicarUsuarioRegistrado(Usuario usuario) {
        usuarioRegistradoSink.next(usuario);
    }

    public Flux<Usuario> getUsuarioActualizadoPublisher() {
        return usuarioActualizadoProcessor;
    }

    public Flux<Usuario> getUsuarioEliminadoPublisher() {
        return usuarioEliminadoProcessor;
    }

    public Flux<Usuario> getUsuarioRegistradoPublisher() {
        return usuarioRegistradoProcessor;
    }

}
