package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Aerolinea;
import com.pw.gestorreservasvuelos.entities.Aeropuerto;
import com.pw.gestorreservasvuelos.dto.VueloDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface IVueloService {

    VueloDto guardarVuelo (VueloDto vuelo);

    Optional<VueloDto> buscarVueloPorId (Long id);

    List<VueloDto> buscarVuelosPorIds (List<Long> ids);

    List<VueloDto> buscarVuelos ();

    List<VueloDto> buscarVuelosPorFechaSalida (LocalDate fechaSalida);

    List<VueloDto> buscarVuelosPorFechaSalidaYHoraSalida (LocalDate fechaSalida, LocalTime horaSalida);

    List<VueloDto> buscarVuelosPorOrigen (Aeropuerto origen);

    List<VueloDto> buscarVuelosPorDestino (Aeropuerto destino);

    List<VueloDto> buscarVuelosPorOrigenYDestino (Aeropuerto origen, Aeropuerto destino);

    List<VueloDto> buscarVuelosPorOrigenYDestinoYFechaSalida (Aeropuerto origen, Aeropuerto destino, LocalDate fechaSalida);

    List<VueloDto> buscarVuelosPorAerolineaYOrigenYDestinoYFechaSalida (Aerolinea aerolinea, Aeropuerto origen, Aeropuerto destino, LocalDate fechaSalida);

    List<VueloDto> buscarVuelosPorAerolinea (Aerolinea aerolinea);

    List<VueloDto> buscarVuelosPorCapacidad (Integer capacidad);

    List<VueloDto> buscarVuelosPorDuracion (Integer duracion);

    List<VueloDto> buscarVuelosPorFechaSalidaEntre (LocalDate fechaSalida1, LocalDate fechaSalida2);

    List<VueloDto> buscarVuelosPorAerolineaYFechaSalida (Aerolinea aerolinea, LocalDate fechaSalida);

    List<VueloDto> buscarVuelosPorAerolineaYFechaSalidaEntre (Aerolinea aerolinea, LocalDate fechaSalida1, LocalDate fechaSalida2);

    Optional<VueloDto> actualizarVuelo (Long id, VueloDto vuelo);

    void eliminarVuelo (Long id);

}
