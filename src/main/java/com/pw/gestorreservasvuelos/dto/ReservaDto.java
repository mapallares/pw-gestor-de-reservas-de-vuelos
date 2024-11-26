package com.pw.gestorreservasvuelos.dto;

import java.time.LocalDate;
import java.util.List;

public record ReservaDto(Long id,
                         ClienteDto cliente,
                         LocalDate fechaReserva,
                         Integer numeroPasajeros,
                         List<PasajeroDto> pasajeros,
                         List<VueloDto> vuelos) {
}
