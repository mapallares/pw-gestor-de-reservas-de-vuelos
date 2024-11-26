package com.pw.gestorreservasvuelos.exceptions;

public class PasajeroNotFoundException extends ResourceNotFoundException {

    public PasajeroNotFoundException(String mensaje) {
        super(mensaje);
    }

}
