package com.pw.gestorreservasvuelos.repositories;

import com.pw.gestorreservasvuelos.entities.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AerolineaRepository extends JpaRepository<Aerolinea, Long> {

    Aerolinea save (Aerolinea aerolinea);

    Optional<Aerolinea> findByCodigoAerolinea (String codigoAerolinea);

    Optional<Aerolinea> findById (Long id);

    List<Aerolinea> findByIdIn (Collection<Long> ids);

    List<Aerolinea> findAllByNombre (String nombre);

    List<Aerolinea> findAllByPaisOrigen (String paisOrigen);

    List<Aerolinea> findAllByNombreAndPaisOrigen (String nombre, String paisOrigen);

}
