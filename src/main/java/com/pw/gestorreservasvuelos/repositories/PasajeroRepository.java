package com.pw.gestorreservasvuelos.repositories;

import com.pw.gestorreservasvuelos.entities.Pasajero;
import com.pw.gestorreservasvuelos.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {

    Pasajero save (Pasajero pasajero);

    Optional<Pasajero> findByIdentificacion (String identificacion);

    Optional<Pasajero> findById (Long id);

    List<Pasajero> findByIdIn (Collection<Long> ids);

    List<Pasajero> findAllByReserva (Reserva reserva);

    List<Pasajero> findAllByNombre (String nombre);

    List<Pasajero> findAllByApellido (String apellido);

    List<Pasajero> findAllByNombreOrApellido (String nombre, String apellido);

    List<Pasajero> findAllByNombreAndApellido (String nombre, String apellido);

}
