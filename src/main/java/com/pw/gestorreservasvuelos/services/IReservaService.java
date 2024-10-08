package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Cliente;
import com.pw.gestorreservasvuelos.dto.ReservaDto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IReservaService {

    ReservaDto guardarReserva (ReservaDto reserva);

    Optional<ReservaDto> buscarReservaPorId (Long id);

    List<ReservaDto> buscarReservasPorIds (Collection<Long> ids);

    List<ReservaDto> buscarReservas ();

    List<ReservaDto> buscarReservasPorFechaReserva (LocalDate fechaReserva);

    List<ReservaDto> buscarReservasPorFechaReservaEntre (LocalDate fechaReserva1, LocalDate fechaReserva2);

    List<ReservaDto> buscarReservasPorCliente (Cliente cliente);

    List<ReservaDto> buscarReservasPorNumeroPasajeros (Integer numeroPasajeros);

    List<ReservaDto> buscarReservasPorNumeroPasajerosMenorOIgualQue (Integer numeroPasajeros);

    List<ReservaDto> buscarReservasPorNumeroPasajeroMayorOIgualQue (Integer numeroPasajeros);

    Optional<ReservaDto> actualizarReserva (Long id, ReservaDto reserva);

    void eliminarReserva (Long id);

}
