package com.pw.gestorreservasvuelos.repositories;

import com.pw.gestorreservasvuelos.entities.Aerolinea;
import com.pw.gestorreservasvuelos.entities.Aeropuerto;
import com.pw.gestorreservasvuelos.entities.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {

    Vuelo save(Vuelo vuelo);

    Vuelo findById (long id);

    List<Vuelo> findAllByFechaSalida(LocalDate fechaSalida);

    List<Vuelo> findAllByFechaSalidaAndHoraSalida(LocalDate fechaSalida, LocalTime horaSalida);

    List<Vuelo> findAllByOrigen(Aeropuerto origen);

    List<Vuelo> findAllByDestino(Aeropuerto destino);

    List<Vuelo> findAllByOrigenAndDestino (Aeropuerto origen, Aeropuerto destino);

    List<Vuelo> findAllByOrigenAndDestinoAndFechaSalida (Aeropuerto origen, Aeropuerto destino, LocalDate fechaSalida);

    List<Vuelo> findAllByAerolineaAndOrigenAndDestinoAndFechaSalida (Aerolinea aerolinea, Aeropuerto origen, Aeropuerto destino, LocalDate fechaSalida);

    List<Vuelo> findAllByAerolinea(Aerolinea aerolinea);

    List<Vuelo> findAllByCapacidad (int capacidad);

    List<Vuelo> findAllByDuracion (int duracion);

    List<Vuelo> findAllByFechaSalidaBetween(LocalDate fechaSalida1, LocalDate fechaSalida2);

    List<Vuelo> findAllByAerolineaAndFechaSalida(Aerolinea aerolinea, LocalDate fechaSalida);

    List<Vuelo> findAllByAerolineaAndFechaSalidaBetween (Aerolinea aerolinea, LocalDate fechaSalida1, LocalDate fechaSalida2);


}
