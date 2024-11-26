package com.pw.gestorreservasvuelos.dto;

import java.time.LocalDate;
import java.util.List;

public record ReservaRequestDto(Long id,
                                Long clienteId,
                                LocalDate fechaReserva,
                                Integer numeroPasajeros,
                                List<Long> vuelosIds) {
}
