package com.pw.gestorreservasvuelos.exceptions;

public class ClienteAlreadyExistException extends ResourceAlreadyExistException {

    public ClienteAlreadyExistException(String message) {
        super(message);
    }

}
