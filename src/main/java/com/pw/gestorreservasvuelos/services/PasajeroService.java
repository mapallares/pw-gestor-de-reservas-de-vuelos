package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.dto.PasajeroDto;
import com.pw.gestorreservasvuelos.dto.PasajeroMapper;
import com.pw.gestorreservasvuelos.entities.Reserva;
import com.pw.gestorreservasvuelos.repositories.PasajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PasajeroService implements IPasajeroService {

    @Autowired
    private PasajeroRepository pasajeroRepository;
    private final PasajeroMapper pasajeroMapper;

    PasajeroService(PasajeroRepository pasajeroRepository, PasajeroMapper pasajeroMapper) {
        this.pasajeroRepository = pasajeroRepository;
        this.pasajeroMapper = pasajeroMapper;
    }

    @Override
    public PasajeroDto guardarPasajero(PasajeroDto pasajero) {
        return pasajeroMapper.pasajeroToPasajeroDtoWithoutId(pasajeroRepository.save(pasajeroMapper.pasajeroDtoWithoutIdToPasajero(pasajero)));
    }

    @Override
    public Optional<PasajeroDto> buscarPasajeroPorIdentificacion(String identificacion) {
        return pasajeroRepository.findByIdentificacion(identificacion).map(pasajero -> pasajeroMapper.pasajeroToPasajeroDto(pasajero));
    }

    @Override
    public Optional<PasajeroDto> buscarPasajeroPorId(Long id) {
        return pasajeroRepository.findById(id).map(pasajero -> pasajeroMapper.pasajeroToPasajeroDto(pasajero));
    }

    @Override
    public List<PasajeroDto> buscarPasajerosPorIds(Collection<Long> ids) {
        List<PasajeroDto> pasajeros = new ArrayList<>();
        pasajeroRepository.findByIdIn(ids).forEach(pasajero -> pasajeros.add(pasajeroMapper.pasajeroToPasajeroDtoWithoutId(pasajero)));
        return pasajeros;
    }

    @Override
    public List<PasajeroDto> buscarPasajeros() {
        List<PasajeroDto> pasajeros = new ArrayList<>();
        pasajeroRepository.findAll().forEach(pasajero -> pasajeros.add(pasajeroMapper.pasajeroToPasajeroDtoWithoutId(pasajero)));
        return pasajeros;
    }

    @Override
    public List<PasajeroDto> buscarPasajerosPorNombre(String nombre) {
        List<PasajeroDto> pasajeros = new ArrayList<>();
        pasajeroRepository.findAllByNombre(nombre).forEach(pasajero -> pasajeros.add(pasajeroMapper.pasajeroToPasajeroDtoWithoutId(pasajero)));
        return pasajeros;
    }

    @Override
    public List<PasajeroDto> buscarPasajerosPorApellido(String apellido) {
        List<PasajeroDto> pasajeros = new ArrayList<>();
        pasajeroRepository.findAllByApellido(apellido).forEach(pasajero -> pasajeros.add(pasajeroMapper.pasajeroToPasajeroDtoWithoutId(pasajero)));
        return pasajeros;
    }

    @Override
    public List<PasajeroDto> buscarPasajerosPorNombreOApellido(String nombre, String apellido) {
        List<PasajeroDto> pasajeros = new ArrayList<>();
        pasajeroRepository.findAllByNombreOrApellido(nombre, apellido).forEach(pasajero -> pasajeros.add(pasajeroMapper.pasajeroToPasajeroDtoWithoutId(pasajero)));
        return pasajeros;
    }

    @Override
    public List<PasajeroDto> buscarPasajerosPorNombreYApellido(String nombre, String apellido) {
        List<PasajeroDto> pasajeros = new ArrayList<>();
        pasajeroRepository.findAllByNombreAndApellido(nombre, apellido).forEach(pasajero -> pasajeros.add(pasajeroMapper.pasajeroToPasajeroDtoWithoutId(pasajero)));
        return pasajeros;
    }

    @Override
    public List<PasajeroDto> buscarPasajerosPorReserva(Reserva reserva) {
        List<PasajeroDto> pasajeros = new ArrayList<>();
        pasajeroRepository.findAllByReserva(reserva).forEach(pasajero -> pasajeros.add(pasajeroMapper.pasajeroToPasajeroDtoWithoutId(pasajero)));
        return pasajeros;
    }

    @Override
    public Optional<PasajeroDto> actualizarPasajero(Long id, PasajeroDto pasajero) {
        return pasajeroRepository.findById(id).map(oldPasajero -> {
            oldPasajero.setNombre(pasajero.nombre());
            oldPasajero.setApellido(pasajero.apellido());
            oldPasajero.setFechaNacimiento(pasajero.fechaNacimiento());
            oldPasajero.setIdentificacion(pasajero.identificacion());
            oldPasajero.setCorreoElectronico(pasajero.correoElectronico());
            return pasajeroMapper.pasajeroToPasajeroDto(pasajeroRepository.save(oldPasajero));
        });
    }

    @Override
    public void eliminarPasajero(Long id) {
        pasajeroRepository.deleteById(id);
    }

}
