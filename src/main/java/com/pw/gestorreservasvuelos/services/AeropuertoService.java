package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Aeropuerto;
import com.pw.gestorreservasvuelos.repositories.AerolineaRepository;
import com.pw.gestorreservasvuelos.repositories.AeropuertoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AeropuertoService implements IAeropuertoService {

    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    AeropuertoService(AeropuertoRepository aeropuertoRepository) {
        this.aeropuertoRepository = aeropuertoRepository;
    }

    @Override
    public Aeropuerto guardarAeropuerto(Aeropuerto aeropuerto) {
        return aeropuertoRepository.save(aeropuerto);
    }

    @Override
    public Optional<Aeropuerto> buscarAeropuertoPorNombre(String nombre) {
        return aeropuertoRepository.findByNombre(nombre);
    }

    @Override
    public Optional<Aeropuerto> buscarAeropuertoPorId(Long id) {
        return aeropuertoRepository.findById(id);
    }

    @Override
    public List<Aeropuerto> buscarAeropuestosPorIds(Collection<Long> ids) {
        return aeropuertoRepository.findByIdIn(ids);
    }

    @Override
    public List<Aeropuerto> buscarAeropuestos() {
        return aeropuertoRepository.findAll();
    }

    @Override
    public List<Aeropuerto> buscarAeropuertosPorPais(String pais) {
        return aeropuertoRepository.findAllByPais(pais);
    }

    @Override
    public List<Aeropuerto> buscarAeropuertosPorCiudad(String ciudad) {
        return aeropuertoRepository.findAllByCiudad(ciudad);
    }

    @Override
    public List<Aeropuerto> buscarAeropuertosPorPaisOCiudad(String pais, String ciudad) {
        return aeropuertoRepository.findAllByPaisOrCiudad(pais, ciudad);
    }

    @Override
    public List<Aeropuerto> buscarAeropuertosPorPaisYCiudad(String pais, String ciudad) {
        return aeropuertoRepository.findAllByPaisAndCiudad(pais, ciudad);
    }

    @Override
    public List<Aeropuerto> buscarAeropuertosPorNombre(String nombre) {
        return aeropuertoRepository.findAllByNombre(nombre);
    }

    @Override
    public Optional<Aeropuerto> actualizarAeropuerto(Long id, Aeropuerto aeropuerto) {
        return aeropuertoRepository.findById(id).map(oldAeropuerto -> {
            oldAeropuerto.setNombre(aeropuerto.getNombre());
            oldAeropuerto.setCiudad(aeropuerto.getCiudad());
            oldAeropuerto.setPais(aeropuerto.getPais());
            return aeropuertoRepository.save(oldAeropuerto);
        });
    }

}
