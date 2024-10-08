package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.dto.AeropuertoDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IAeropuertoService {

    AeropuertoDto guardarAeropuerto (AeropuertoDto aeropuerto);

    Optional<AeropuertoDto> buscarAeropuertoPorNombre (String nombre);

    Optional<AeropuertoDto> buscarAeropuertoPorId (Long id);

    List<AeropuertoDto> buscarAeropuestosPorIds (Collection<Long> ids);

    List<AeropuertoDto> buscarAeropuertos ();

    List<AeropuertoDto> buscarAeropuertosPorPais (String pais);

    List<AeropuertoDto> buscarAeropuertosPorCiudad (String ciudad);

    List<AeropuertoDto> buscarAeropuertosPorPaisOCiudad (String pais, String ciudad);

    List<AeropuertoDto> buscarAeropuertosPorPaisYCiudad (String pais, String ciudad);

    List<AeropuertoDto> buscarAeropuertosPorNombre (String nombre);

    Optional<AeropuertoDto> actualizarAeropuerto (Long id, AeropuertoDto aeropuerto);

    void eliminarAeropuerto (Long id);

}
