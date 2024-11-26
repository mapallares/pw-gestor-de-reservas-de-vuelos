package com.pw.gestorreservasvuelos.exceptions;

public class ClienteNotFoundException extends ResourceNotFoundException {

    public ClienteNotFoundException(String mensaje) {
        super(mensaje);
    }

}
