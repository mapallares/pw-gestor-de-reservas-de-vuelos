package com.pw.gestorreservasvuelos.dto;

import java.time.LocalDate;

public record PasajeroRequestDto(Long id,
                                 String nombre,
                                 String apellido,
                                 LocalDate fechaNacimiento,
                                 String identificacion,
                                 String email,
                                 Long reservaId) {
}
