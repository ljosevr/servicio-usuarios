package com.example.muruna.serviciousuarios.infrastructure.adapters.input.exceptions;

public class UsuarioException extends Exception {

    public UsuarioException(String message) {
        super(message);
    }

    public UsuarioException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioException(Throwable cause) {
        super(cause);
    }

    public UsuarioException() {
        super();
    }
}

