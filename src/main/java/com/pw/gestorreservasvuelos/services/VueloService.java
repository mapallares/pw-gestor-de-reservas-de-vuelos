package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.dto.AerolineaMapper;
import com.pw.gestorreservasvuelos.dto.AeropuertoMapper;
import com.pw.gestorreservasvuelos.dto.VueloMapper;
import com.pw.gestorreservasvuelos.entities.Aerolinea;
import com.pw.gestorreservasvuelos.entities.Aeropuerto;
import com.pw.gestorreservasvuelos.dto.VueloDto;
import com.pw.gestorreservasvuelos.repositories.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VueloService implements IVueloService {

    @Autowired
    private VueloRepository vueloRepository;
    private final VueloMapper vueloMapper;
    private final AeropuertoMapper aeropuertoMapper;
    private final AerolineaMapper aerolineaMapper;

    VueloService(VueloRepository vueloRepository, VueloMapper vueloMapper, AeropuertoMapper aeropuertoMapper, AerolineaMapper aerolineaMapper) {
        this.vueloRepository = vueloRepository;
        this.vueloMapper = vueloMapper;
        this.aeropuertoMapper = aeropuertoMapper;
        this.aerolineaMapper = aerolineaMapper;
    }

    @Override
    public VueloDto guardarVuelo(VueloDto vuelo) {
        return vueloMapper.vueloToVueloDtoWithoutId(vueloRepository.save(vueloMapper.vueloDtoWithoutIdToVuelo(vuelo)));
    }

    @Override
    public Optional<VueloDto> buscarVueloPorId(Long id) {
        return vueloRepository.findById(id).map(vuelo -> vueloMapper.vueloToVueloDto(vuelo));
    }

    @Override
    public List<VueloDto> buscarVuelosPorIds(List<Long> ids) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findByIdIn(ids).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelos() {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAll().forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorFechaSalida(LocalDate fechaSalida) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByFechaSalida(fechaSalida).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorFechaSalidaYHoraSalida(LocalDate fechaSalida, LocalTime horaSalida) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByFechaSalidaAndHoraSalida(fechaSalida, horaSalida).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorOrigen(Aeropuerto origen) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByOrigen(origen).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorDestino(Aeropuerto destino) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByDestino(destino).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorOrigenYDestino(Aeropuerto origen, Aeropuerto destino) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByOrigenAndDestino(origen, destino).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorOrigenYDestinoYFechaSalida(Aeropuerto origen, Aeropuerto destino, LocalDate fechaSalida) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByOrigenAndDestinoAndFechaSalida(origen, destino, fechaSalida).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorAerolineaYOrigenYDestinoYFechaSalida(Aerolinea aerolinea, Aeropuerto origen, Aeropuerto destino, LocalDate fechaSalida) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByAerolineaAndOrigenAndDestinoAndFechaSalida(aerolinea, origen, destino, fechaSalida).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorAerolinea(Aerolinea aerolinea) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByAerolinea(aerolinea).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorCapacidad(Integer capacidad) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByCapacidad(capacidad).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorDuracion(Integer duracion) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByDuracion(duracion).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorFechaSalidaEntre(LocalDate fechaSalida1, LocalDate fechaSalida2) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByFechaSalidaBetween(fechaSalida1, fechaSalida2).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorAerolineaYFechaSalida(Aerolinea aerolinea, LocalDate fechaSalida) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByAerolineaAndFechaSalida(aerolinea, fechaSalida).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public List<VueloDto> buscarVuelosPorAerolineaYFechaSalidaEntre(Aerolinea aerolinea, LocalDate fechaSalida1, LocalDate fechaSalida2) {
        List<VueloDto> vuelos = new ArrayList<>();
        vueloRepository.findAllByAerolineaAndFechaSalidaBetween(aerolinea, fechaSalida1, fechaSalida2).forEach(vuelo -> vuelos.add(vueloMapper.vueloToVueloDto(vuelo)));
        return vuelos;
    }

    @Override
    public Optional<VueloDto> actualizarVuelo(Long id, VueloDto vuelo) {
        return vueloRepository.findById(id).map(oldVuelo -> {
            oldVuelo.setOrigen(aeropuertoMapper.aeropuertoDtoToAeropuerto(vuelo.origen()));
            oldVuelo.setDestino(aeropuertoMapper.aeropuertoDtoToAeropuerto(vuelo.destino()));
            oldVuelo.setAerolinea(aerolineaMapper.aerolineaDtoToAerolinea(vuelo.aerolinea()));
            oldVuelo.setFechaSalida(vuelo.fechaSalida());
            oldVuelo.setCapacidad(vuelo.capacidad());
            oldVuelo.setDuracion(vuelo.duracion());
            return vueloMapper.vueloToVueloDto(vueloRepository.save(oldVuelo));
        });
    }

    @Override
    public void eliminarVuelo(Long id) {
        vueloRepository.deleteById(id);
    }

}
