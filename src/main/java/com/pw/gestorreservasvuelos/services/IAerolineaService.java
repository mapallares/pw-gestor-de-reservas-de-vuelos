package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Aerolinea;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IAerolineaService {

    Aerolinea guardarAerolinea (Aerolinea aerolinea);

    Optional<Aerolinea> buscarAerolineaPorId (Long id);

    List<Aerolinea> buscarAerolineasPorIds (Collection<Long> ids);

    Optional<Aerolinea> buscarAerolineaPorCodigo (String codigo);

    List<Aerolinea> buscarAerolineas ();

    List<Aerolinea> buscarAerolineasPorNombre (String nombre);

    List<Aerolinea> buscarAerolineasPorPaisOrigen (String paisOrigen);

    List<Aerolinea> buscarAerolineasPorNombreYPaisOrigen (String nombre, String paisOrigen);

    Optional<Aerolinea> actualizarAerolinea (Long id, Aerolinea aerolinea);

    void eliminarAerolinea (Long id);

}
