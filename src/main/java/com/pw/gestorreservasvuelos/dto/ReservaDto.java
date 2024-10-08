package com.pw.gestorreservasvuelos.dto;

import java.time.LocalDate;

public record ReservaDto(Long id,
                         LocalDate fechaReserva,
                         Integer numeroPasajeros) {
}
