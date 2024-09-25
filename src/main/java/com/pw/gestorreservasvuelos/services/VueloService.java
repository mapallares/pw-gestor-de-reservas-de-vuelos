package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Aerolinea;
import com.pw.gestorreservasvuelos.entities.Aeropuerto;
import com.pw.gestorreservasvuelos.entities.Vuelo;
import com.pw.gestorreservasvuelos.repositories.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class VueloService implements IVueloService {

    @Autowired
    private VueloRepository vueloRepository;

    VueloService(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    @Override
    public Vuelo guardarVuelo(Vuelo vuelo) {
        return vueloRepository.save(vuelo);
    }

    @Override
    public Optional<Vuelo> buscarVueloPorId(Long id) {
        return vueloRepository.findById(id);
    }

    @Override
    public List<Vuelo> buscarVuelosPorIds(List<Long> ids) {
        return vueloRepository.findByIdIn(ids);
    }

    @Override
    public List<Vuelo> buscarVuelos() {
        return vueloRepository.findAll();
    }

    @Override
    public List<Vuelo> buscarVuelosPorFechaSalida(LocalDate fechaSalida) {
        return vueloRepository.findAllByFechaSalida(fechaSalida);
    }

    @Override
    public List<Vuelo> buscarVuelosPorFechaSalidaYHoraSalida(LocalDate fechaSalida, LocalTime horaSalida) {
        return vueloRepository.findAllByFechaSalidaAndHoraSalida(fechaSalida, horaSalida);
    }

    @Override
    public List<Vuelo> buscarVuelosPorOrigen(Aeropuerto origen) {
        return List.of();
    }

    @Override
    public List<Vuelo> buscarVuelosPorDestino(Aeropuerto destino) {
        return vueloRepository.findAllByDestino(destino);
    }

    @Override
    public List<Vuelo> buscarVuelosPorOrigenYDestino(Aeropuerto origen, Aeropuerto destino) {
        return vueloRepository.findAllByOrigenAndDestino(origen, destino);
    }

    @Override
    public List<Vuelo> buscarVuelosPorOrigenYDestinoYFechaSalida(Aeropuerto origen, Aeropuerto destino, LocalDate fechaSalida) {
        return vueloRepository.findAllByOrigenAndDestinoAndFechaSalida(origen, destino, fechaSalida);
    }

    @Override
    public List<Vuelo> buscarVuelosPorAerolineaYOrigenYDestinoYFechaSalida(Aerolinea aerolinea, Aeropuerto origen, Aeropuerto destino, LocalDate fechaSalida) {
        return vueloRepository.findAllByAerolineaAndOrigenAndDestinoAndFechaSalida(aerolinea, origen, destino, fechaSalida);
    }

    @Override
    public List<Vuelo> buscarVuelosPorAerolinea(Aerolinea aerolinea) {
        return vueloRepository.findAllByAerolinea(aerolinea);
    }

    @Override
    public List<Vuelo> buscarVuelosPorCapacidad(int capacidad) {
        return vueloRepository.findAllByCapacidad(capacidad);
    }

    @Override
    public List<Vuelo> buscarVuelosPorDuracion(int duracion) {
        return vueloRepository.findAllByDuracion(duracion);
    }

    @Override
    public List<Vuelo> buscarVuelosPorFechaSalidaEntre(LocalDate fechaSalida1, LocalDate fechaSalida2) {
        return vueloRepository.findAllByFechaSalidaBetween(fechaSalida1, fechaSalida2);
    }

    @Override
    public List<Vuelo> buscarVuelosPorAerolineaYFechaSalida(Aerolinea aerolinea, LocalDate fechaSalida) {
        return vueloRepository.findAllByAerolineaAndFechaSalida(aerolinea, fechaSalida);
    }

    @Override
    public List<Vuelo> buscarVuelosPorAerolineaYFechaSalidaEntre(Aerolinea aerolinea, LocalDate fechaSalida1, LocalDate fechaSalida2) {
        return vueloRepository.findAllByAerolineaAndFechaSalidaBetween(aerolinea, fechaSalida1, fechaSalida2);
    }

    @Override
    public Optional<Vuelo> actualizarVuelo(Long id, Vuelo vuelo) {
        return vueloRepository.findById(id).map(oldVuelo -> {
            oldVuelo.setOrigen(vuelo.getOrigen());
            oldVuelo.setDestino(vuelo.getDestino());
            oldVuelo.setAerolinea(vuelo.getAerolinea());
            oldVuelo.setFechaSalida(vuelo.getFechaSalida());
            oldVuelo.setCapacidad(vuelo.getCapacidad());
            oldVuelo.setDuracion(vuelo.getDuracion());
            return vueloRepository.save(oldVuelo);
        });
    }

    @Override
    public void eliminarVuelo(Long id) {
        vueloRepository.deleteById(id);
    }

}
