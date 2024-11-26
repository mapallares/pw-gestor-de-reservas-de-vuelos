package com.pw.gestorreservasvuelos.dto;

import java.time.LocalDate;

public record ClienteDto(Long id,
                         String nombre,
                         String apellido,
                         LocalDate fechaNacimiento,
                         String direccion,
                         String telefono,
                         String email,
                         String username,
                         String password) {
}