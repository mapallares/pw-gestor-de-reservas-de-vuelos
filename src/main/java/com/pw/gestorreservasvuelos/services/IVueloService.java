package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Aerolinea;
import com.pw.gestorreservasvuelos.entities.Aeropuerto;
import com.pw.gestorreservasvuelos.entities.Vuelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IVueloService {

    Vuelo guardarVuelo (Vuelo vuelo);

    Optional<Vuelo> buscarVueloPorId (Long id);

    List<Vuelo> buscarVuelosPorIds (List<Long> ids);

    List<Vuelo> buscarVuelos ();

    List<Vuelo> buscarVuelosPorFechaSalida (LocalDate fechaSalida);

    List<Vuelo> buscarVuelosPorFechaSalidaYHoraSalida (LocalDate fechaSalida, LocalTime horaSalida);

    List<Vuelo> buscarVuelosPorOrigen (Aeropuerto origen);

    List<Vuelo> buscarVuelosPorDestino (Aeropuerto destino);

    List<Vuelo> buscarVuelosPorOrigenYDestino (Aeropuerto origen, Aeropuerto destino);

    List<Vuelo> buscarVuelosPorOrigenYDestinoYFechaSalida (Aeropuerto origen, Aeropuerto destino, LocalDate fechaSalida);

    List<Vuelo> buscarVuelosPorAerolineaYOrigenYDestinoYFechaSalida (Aerolinea aerolinea, Aeropuerto origen, Aeropuerto destino, LocalDate fechaSalida);

    List<Vuelo> buscarVuelosPorAerolinea (Aerolinea aerolinea);

    List<Vuelo> buscarVuelosPorCapacidad (int capacidad);

    List<Vuelo> buscarVuelosPorDuracion (int duracion);

    List<Vuelo> buscarVuelosPorFechaSalidaEntre (LocalDate fechaSalida1, LocalDate fechaSalida2);

    List<Vuelo> buscarVuelosPorAerolineaYFechaSalida (Aerolinea aerolinea, LocalDate fechaSalida);

    List<Vuelo> buscarVuelosPorAerolineaYFechaSalidaEntre (Aerolinea aerolinea, LocalDate fechaSalida1, LocalDate fechaSalida2);

    Optional<Vuelo> actualizarVuelo (Long id, Vuelo vuelo);

    void eliminarVuelo (Long id);

}
