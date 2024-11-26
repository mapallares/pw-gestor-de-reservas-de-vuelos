package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.dto.PasajeroDto;
import com.pw.gestorreservasvuelos.dto.ReservaDto;
import com.pw.gestorreservasvuelos.entities.Reserva;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IPasajeroService {

    PasajeroDto guardarPasajero (PasajeroDto pasajero);

    Optional<PasajeroDto> buscarPasajeroPorIdentificacion (String identificacion);

    Optional<PasajeroDto> buscarPasajeroPorId (Long id);

    List<PasajeroDto> buscarPasajerosPorIds (Collection<Long> ids);

    List<PasajeroDto> buscarPasajeros ();

    List<PasajeroDto> buscarPasajerosPorNombre (String nombre);

    List<PasajeroDto> buscarPasajerosPorApellido (String apellido);

    List<PasajeroDto> buscarPasajerosPorNombreOApellido (String nombre, String apellido);

    List<PasajeroDto> buscarPasajerosPorNombreYApellido (String nombre, String apellido);

    List<PasajeroDto> buscarPasajerosPorReserva (Reserva reserva);

    Optional<PasajeroDto> actualizarPasajero (Long id, PasajeroDto pasajero, ReservaDto reserva);

    void eliminarPasajero (Long id);

}
