package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.dto.AeropuertoDto;
import com.pw.gestorreservasvuelos.dto.AeropuertoMapper;
import com.pw.gestorreservasvuelos.repositories.AeropuertoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AeropuertoService implements IAeropuertoService {

    @Autowired
    private AeropuertoRepository aeropuertoRepository;
    private final AeropuertoMapper aeropuertoMapper;

    AeropuertoService(AeropuertoRepository aeropuertoRepository, AeropuertoMapper aeropuertoMapper) {
        this.aeropuertoRepository = aeropuertoRepository;
        this.aeropuertoMapper = aeropuertoMapper;
    }

    @Override
    public AeropuertoDto guardarAeropuerto(AeropuertoDto aeropuerto) {
        return aeropuertoMapper.aeropuertoToAeropuertoDtoWithoutId(aeropuertoRepository.save(aeropuertoMapper.aeropuertoDtoWithoutIdToAeropuerto(aeropuerto)));
    }

    @Override
    public Optional<AeropuertoDto> buscarAeropuertoPorNombre(String nombre) {
        return aeropuertoRepository.findByNombre(nombre).map(aeropuerto -> aeropuertoMapper.aeropuertoToAeropuertoDto(aeropuerto));
    }

    @Override
    public Optional<AeropuertoDto> buscarAeropuertoPorId(Long id) {
        return aeropuertoRepository.findById(id).map(aeropuerto -> aeropuertoMapper.aeropuertoToAeropuertoDto(aeropuerto));
    }

    @Override
    public List<AeropuertoDto> buscarAeropuestosPorIds(Collection<Long> ids) {
        List<AeropuertoDto> aeropuertos = new ArrayList<>();
        aeropuertoRepository.findByIdIn(ids).forEach(aeropuerto -> aeropuertos.add(aeropuertoMapper.aeropuertoToAeropuertoDtoWithoutId(aeropuerto)));
        return aeropuertos;
    }

    @Override
    public List<AeropuertoDto> buscarAeropuertos() {
        List<AeropuertoDto> aeropuertos = new ArrayList<>();
        aeropuertoRepository.findAll().forEach(aeropuerto -> aeropuertos.add(aeropuertoMapper.aeropuertoToAeropuertoDtoWithoutId(aeropuerto)));
        return aeropuertos;
    }

    @Override
    public List<AeropuertoDto> buscarAeropuertosPorPais(String pais) {
        List<AeropuertoDto> aeropuertos = new ArrayList<>();
        aeropuertoRepository.findAllByPais(pais).forEach(aeropuerto -> aeropuertos.add(aeropuertoMapper.aeropuertoToAeropuertoDtoWithoutId(aeropuerto)));
        return aeropuertos;
    }

    @Override
    public List<AeropuertoDto> buscarAeropuertosPorCiudad(String ciudad) {
        List<AeropuertoDto> aeropuertos = new ArrayList<>();
        aeropuertoRepository.findAllByCiudad(ciudad).forEach(aeropuerto -> aeropuertos.add(aeropuertoMapper.aeropuertoToAeropuertoDtoWithoutId(aeropuerto)));
        return aeropuertos;
    }

    @Override
    public List<AeropuertoDto> buscarAeropuertosPorPaisOCiudad(String pais, String ciudad) {
        List<AeropuertoDto> aeropuertos = new ArrayList<>();
        aeropuertoRepository.findAllByPaisOrCiudad(pais, ciudad).forEach(aeropuerto -> aeropuertos.add(aeropuertoMapper.aeropuertoToAeropuertoDtoWithoutId(aeropuerto)));
        return aeropuertos;
    }

    @Override
    public List<AeropuertoDto> buscarAeropuertosPorPaisYCiudad(String pais, String ciudad) {
        List<AeropuertoDto> aeropuertos = new ArrayList<>();
        aeropuertoRepository.findAllByPaisAndCiudad(pais, ciudad).forEach(aeropuerto -> aeropuertos.add(aeropuertoMapper.aeropuertoToAeropuertoDtoWithoutId(aeropuerto)));
        return aeropuertos;
    }

    @Override
    public List<AeropuertoDto> buscarAeropuertosPorNombre(String nombre) {
        List<AeropuertoDto> aeropuertos = new ArrayList<>();
        aeropuertoRepository.findAllByNombre(nombre).forEach(aeropuerto -> aeropuertos.add(aeropuertoMapper.aeropuertoToAeropuertoDtoWithoutId(aeropuerto)));
        return aeropuertos;
    }

    @Override
    public Optional<AeropuertoDto> actualizarAeropuerto(Long id, AeropuertoDto aeropuerto) {
        return aeropuertoRepository.findById(id).map(oldAeropuerto -> {
            oldAeropuerto.setNombre(aeropuerto.nombre());
            oldAeropuerto.setCiudad(aeropuerto.ciudad());
            oldAeropuerto.setPais(aeropuerto.pais());
            return aeropuertoMapper.aeropuertoToAeropuertoDto(aeropuertoRepository.save(oldAeropuerto));
        });
    }

    @Override
    public void eliminarAeropuerto(Long id) {
        aeropuertoRepository.deleteById(id);
    }

}