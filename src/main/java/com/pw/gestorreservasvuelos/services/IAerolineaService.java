package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.dto.AerolineaDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IAerolineaService {

    AerolineaDto guardarAerolinea (AerolineaDto aerolinea);

    Optional<AerolineaDto> buscarAerolineaPorId (Long id);

    List<AerolineaDto> buscarAerolineasPorIds (Collection<Long> ids);

    Optional<AerolineaDto> buscarAerolineaPorCodigo (String codigo);

    List<AerolineaDto> buscarAerolineas ();

    List<AerolineaDto> buscarAerolineasPorNombre (String nombre);

    List<AerolineaDto> buscarAerolineasPorPaisOrigen (String paisOrigen);

    List<AerolineaDto> buscarAerolineasPorNombreYPaisOrigen (String nombre, String paisOrigen);

    Optional<AerolineaDto> actualizarAerolinea (Long id, AerolineaDto aerolinea);

    void eliminarAerolinea (Long id);

}
