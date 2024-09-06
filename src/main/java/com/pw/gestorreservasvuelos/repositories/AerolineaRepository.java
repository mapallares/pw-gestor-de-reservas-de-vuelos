package com.pw.gestorreservasvuelos.repositories;

import com.pw.gestorreservasvuelos.entities.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AerolineaRepository extends JpaRepository<Aerolinea, Long> {

    Aerolinea save (Aerolinea aerolinea);

    Aerolinea findByCodigoAerolinea (String codigoAerolinea);

    Aerolinea findById (long id);

    List<Aerolinea> findAllByNombre (String nombre);

    List<Aerolinea> findAllByPaisOrigen (String paisOrigen);

    List<Aerolinea> findAllByNombreAndPaisOrigen (String nombre, String paisOrigen);

}
