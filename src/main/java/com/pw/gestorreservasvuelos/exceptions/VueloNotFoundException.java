package com.pw.gestorreservasvuelos.exceptions;

public class VueloNotFoundException extends ResourceNotFoundException {

    public VueloNotFoundException(String mensaje) {
        super(mensaje);
    }

}
