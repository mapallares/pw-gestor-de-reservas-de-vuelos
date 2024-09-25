package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Aeropuerto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IAeropuertoService {

    Aeropuerto guardarAeropuerto (Aeropuerto aeropuerto);

    Optional<Aeropuerto> buscarAeropuertoPorNombre (String nombre);

    Optional<Aeropuerto> buscarAeropuertoPorId (Long id);

    List<Aeropuerto> buscarAeropuestosPorIds (Collection<Long> ids);

    List<Aeropuerto> buscarAeropuertos ();

    List<Aeropuerto> buscarAeropuertosPorPais (String pais);

    List<Aeropuerto> buscarAeropuertosPorCiudad (String ciudad);

    List<Aeropuerto> buscarAeropuertosPorPaisOCiudad (String pais, String ciudad);

    List<Aeropuerto> buscarAeropuertosPorPaisYCiudad (String pais, String ciudad);

    List<Aeropuerto> buscarAeropuertosPorNombre (String nombre);

    Optional<Aeropuerto> actualizarAeropuerto (Long id, Aeropuerto aeropuerto);

    void eliminarAeropuerto (Long id);

}
