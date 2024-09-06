package com.pw.gestorreservasvuelos.repositories;

import com.pw.gestorreservasvuelos.entities.Cliente;
import com.pw.gestorreservasvuelos.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Reserva save(Reserva reserva);

    Reserva findById(long id);

    List<Reserva> findAllByFechaReserva (LocalDate fechaReserva);

    List<Reserva> findAllByFechaReservaBetween(LocalDate fechaReserva1, LocalDate fechaReserva2);

    List<Reserva> findAllByCliente (Cliente cliente);

    List<Reserva> findAllByNumeroPasajeros (int numeroPasajeros);

    List<Reserva> findAllByNumeroPasajerosLessThanEqual (int numeroPasajeros);

    List<Reserva> findAllByNumeroPasajerosGreaterThanEqual (int numeroPasajeros);

}
