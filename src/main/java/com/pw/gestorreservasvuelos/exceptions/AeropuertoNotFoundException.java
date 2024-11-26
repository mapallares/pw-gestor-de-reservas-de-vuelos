package com.pw.gestorreservasvuelos.exceptions;

public class AeropuertoNotFoundException extends ResourceNotFoundException {

    public AeropuertoNotFoundException(String mensaje) {
        super(mensaje);
    }

}
