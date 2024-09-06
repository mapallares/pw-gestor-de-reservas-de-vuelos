package com.pw.gestorreservasvuelos.repositories;

import com.pw.gestorreservasvuelos.entities.Aeropuerto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Long> {

    Aeropuerto save (Aeropuerto aeropuerto);

    Aeropuerto findByNombre(String nombre);

    Aeropuerto findById(long id);

    List<Aeropuerto> findAllByPais (String pais);

    List<Aeropuerto> findAllByCiudad (String ciudad);

    List<Aeropuerto> findAllByPaisOrCiudad (String pais, String ciudad);

    List<Aeropuerto> findAllByPaisAndCiudad (String pais, String ciudad);

    List<Aeropuerto> findAllByNombre (String nombre);

}
