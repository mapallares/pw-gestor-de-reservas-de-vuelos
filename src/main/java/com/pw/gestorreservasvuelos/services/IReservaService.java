package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Cliente;
import com.pw.gestorreservasvuelos.entities.Reserva;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IReservaService {

    Reserva guardarReserva (Reserva reserva);

    Optional<Reserva> buscarReservaPorId (Long id);

    List<Reserva> buscarReservasPorIds (Collection<Long> ids);

    List<Reserva> buscarReservas ();

    List<Reserva> buscarReservasPorFechaReserva (LocalDate fechaReserva);

    List<Reserva> buscarReservasPorFechaReservaEntre (LocalDate fechaReserva1, LocalDate fechaReserva2);

    List<Reserva> buscarReservasPorCliente (Cliente cliente);

    List<Reserva> buscarReservasPorNumeroPasajeros (int numeroPasajeros);

    List<Reserva> buscarReservasPorNumeroPasajerosMenorOIgualQue (int numeroPasajeros);

    List<Reserva> buscarReservasPorNumeroPasajeroMayorOIgualQue (int numeroPasajeros);

    Optional<Reserva> actualizarReserva (Long id, Reserva reserva);

}
