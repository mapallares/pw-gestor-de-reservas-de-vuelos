package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Pasajero;
import com.pw.gestorreservasvuelos.entities.Reserva;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IPasajeroService {

    Pasajero guardarPasajero (Pasajero pasajero);

    Optional<Pasajero> buscarPasajeroPorIdentificacion (String identificacion);

    Optional<Pasajero> buscarPasajeroPorId (Long id);

    List<Pasajero> buscarPasajerosPorIds (Collection<Long> ids);

    List<Pasajero> buscarPasajeros ();

    List<Pasajero> buscarPasajerosPorNombre (String nombre);

    List<Pasajero> buscarPasajerosPorApellido (String apellido);

    List<Pasajero> buscarPasajerosPorNombreOApellido (String nombre, String apellido);

    List<Pasajero> buscarPasajerosPorNombreYApellido (String nombre, String apellido);

    List<Pasajero> buscarPasajerosPorReserva (Reserva reserva);

    Optional<Pasajero> actualizarPasajero (Long id, Pasajero pasajero);

}
