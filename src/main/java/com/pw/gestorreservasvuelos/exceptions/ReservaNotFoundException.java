package com.pw.gestorreservasvuelos.exceptions;

public class ReservaNotFoundException extends ResourceNotFoundException {

    public ReservaNotFoundException(String mensaje) {
        super(mensaje);
    }

}
