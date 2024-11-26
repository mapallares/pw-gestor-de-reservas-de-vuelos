package com.pw.gestorreservasvuelos.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record VueloDto(Long id,
                       AeropuertoDto origen,
                       AeropuertoDto destino,
                       AerolineaDto aerolinea,
                       LocalDate fechaSalida,
                       LocalTime horaSalida,
                       Integer duracion,
                       Integer capacidad) {
}