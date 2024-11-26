package com.pw.gestorreservasvuelos.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record VueloRequestDto(Long id,
                              Long origenId,
                              Long destinoId,
                              Long aerolineaId,
                              LocalDate fechaSalida,
                              LocalTime horaSalida,
                              Integer duracion,
                              Integer capacidad) {
}