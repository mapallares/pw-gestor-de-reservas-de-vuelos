package com.pw.gestorreservasvuelos.dto;

import java.time.LocalDate;
import java.util.Set;

public record SignupRequest (String nombre,
                             String apellido,
                             LocalDate fechaNacimiento,
                             String direccion,
                             String telefono,
                             String email,
                             String username,
                             String password,
                             Set<String> roles){

}
