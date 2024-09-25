package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Pasajero;
import com.pw.gestorreservasvuelos.entities.Reserva;
import com.pw.gestorreservasvuelos.repositories.PasajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PasajeroService implements IPasajeroService {

    @Autowired
    private PasajeroRepository pasajeroRepository;

    PasajeroService(PasajeroRepository pasajeroRepository) {
        this.pasajeroRepository = pasajeroRepository;
    }

    @Override
    public Pasajero guardarPasajero(Pasajero pasajero) {
        return pasajeroRepository.save(pasajero);
    }

    @Override
    public Optional<Pasajero> buscarPasajeroPorIdentificacion(String identificacion) {
        return pasajeroRepository.findByIdentificacion(identificacion);
    }

    @Override
    public Optional<Pasajero> buscarPasajeroPorId(Long id) {
        return pasajeroRepository.findById(id);
    }

    @Override
    public List<Pasajero> buscarPasajerosPorIds(Collection<Long> ids) {
        return pasajeroRepository.findByIdIn(ids);
    }

    @Override
    public List<Pasajero> buscarPasajeros() {
        return pasajeroRepository.findAll();
    }

    @Override
    public List<Pasajero> buscarPasajerosPorNombre(String nombre) {
        return pasajeroRepository.findAllByNombre(nombre);
    }

    @Override
    public List<Pasajero> buscarPasajerosPorApellido(String apellido) {
        return pasajeroRepository.findAllByApellido(apellido);
    }

    @Override
    public List<Pasajero> buscarPasajerosPorNombreOApellido(String nombre, String apellido) {
        return pasajeroRepository.findAllByNombreOrApellido(nombre, apellido);
    }

    @Override
    public List<Pasajero> buscarPasajerosPorNombreYApellido(String nombre, String apellido) {
        return pasajeroRepository.findAllByNombreAndApellido(nombre, apellido);
    }

    @Override
    public List<Pasajero> buscarPasajerosPorReserva(Reserva reserva) {
        return pasajeroRepository.findAllByReserva(reserva);
    }

    @Override
    public Optional<Pasajero> actualizarPasajero(Long id, Pasajero pasajero) {
        return pasajeroRepository.findById(id).map(oldPasajero -> {
            oldPasajero.setNombre(pasajero.getNombre());
            oldPasajero.setApellido(pasajero.getApellido());
            oldPasajero.setIdentificacion(pasajero.getIdentificacion());
            oldPasajero.setCorreoElectronico(pasajero.getCorreoElectronico());
            return pasajeroRepository.save(oldPasajero);
        });
    }

    @Override
    public void eliminarPasajero(Long id) {
        pasajeroRepository.deleteById(id);
    }

}
