package com.pw.gestorreservasvuelos.repositories;

import com.pw.gestorreservasvuelos.entities.Cliente;
import com.pw.gestorreservasvuelos.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Reserva save (Reserva reserva);

    Optional<Reserva> findById (Long id);

    List<Reserva> findByIdIn (Collection<Long> ids);

    List<Reserva> findAllByFechaReserva (LocalDate fechaReserva);

    List<Reserva> findAllByFechaReservaBetween (LocalDate fechaReserva1, LocalDate fechaReserva2);

    List<Reserva> findAllByCliente (Cliente cliente);

    List<Reserva> findAllByNumeroPasajeros (Integer numeroPasajeros);

    List<Reserva> findAllByNumeroPasajerosLessThanEqual (Integer numeroPasajeros);

    List<Reserva> findAllByNumeroPasajerosGreaterThanEqual (Integer numeroPasajeros);

}
