package com.example.biblioteca.graphql.suscriptions;

import com.example.biblioteca.models.Usuario;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
public class UsuarioSubscriptionResolver {

    private final UsuarioPublisher usuarioPublisher;

    public UsuarioSubscriptionResolver(UsuarioPublisher usuarioPublisher) {
        this.usuarioPublisher = usuarioPublisher;
    }

// En UsuarioSubscriptionResolver.java

    public Flux<Usuario> usuarioActualizado() {
        return usuarioPublisher.getUsuarioActualizadoPublisher();
    }

    public Flux<Usuario> usuarioEliminado() {
        return usuarioPublisher.getUsuarioEliminadoPublisher();
    }


}
