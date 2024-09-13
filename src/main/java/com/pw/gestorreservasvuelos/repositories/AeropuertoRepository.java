package com.pw.gestorreservasvuelos.repositories;

import com.pw.gestorreservasvuelos.entities.Aeropuerto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Long> {

    Aeropuerto save (Aeropuerto aeropuerto);

    Optional<Aeropuerto> findByNombre (String nombre);

    Optional<Aeropuerto> findById (Long id);

    List<Aeropuerto> findByIdIn (Collection<Long> ids);

    List<Aeropuerto> findAllByPais (String pais);

    List<Aeropuerto> findAllByCiudad (String ciudad);

    List<Aeropuerto> findAllByPaisOrCiudad (String pais, String ciudad);

    List<Aeropuerto> findAllByPaisAndCiudad (String pais, String ciudad);

    List<Aeropuerto> findAllByNombre (String nombre);

}
