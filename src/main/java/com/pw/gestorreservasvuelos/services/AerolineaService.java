package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.dto.AerolineaDto;
import com.pw.gestorreservasvuelos.dto.AerolineaMapper;
import com.pw.gestorreservasvuelos.repositories.AerolineaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AerolineaService implements IAerolineaService {

    @Autowired
    private AerolineaRepository aerolineaRepository;
    private final AerolineaMapper aerolineaMapper;

    AerolineaService(AerolineaRepository aerolineaRepository, AerolineaMapper aerolineaMapper) {
        this.aerolineaRepository = aerolineaRepository;
        this.aerolineaMapper = aerolineaMapper;
    }

    @Override
    public AerolineaDto guardarAerolinea(AerolineaDto aerolinea) {
        return aerolineaMapper.aerolineaToAerolineaDtoWithoutId(aerolineaRepository.save(aerolineaMapper.aerolineaDtoWithoutIdToAerolinea(aerolinea)));
    }

    @Override
    public Optional<AerolineaDto> buscarAerolineaPorId(Long id) {
        return aerolineaRepository.findById(id).map(aerolinea -> aerolineaMapper.aerolineaToAerolineaDto(aerolinea));
    }

    @Override
    public List<AerolineaDto> buscarAerolineasPorIds(Collection<Long> ids) {
        List<AerolineaDto> aerolineas = new ArrayList<>();
        aerolineaRepository.findByIdIn(ids).forEach(aerolinea -> aerolineas.add(aerolineaMapper.aerolineaToAerolineaDtoWithoutId(aerolinea)));
        return aerolineas;
    }

    @Override
    public Optional<AerolineaDto> buscarAerolineaPorCodigo(String codigo) {
        return aerolineaRepository.findByCodigoAerolinea(codigo).map(aerolinea -> aerolineaMapper.aerolineaToAerolineaDto(aerolinea));
    }

    @Override
    public List<AerolineaDto> buscarAerolineas() {
        List<AerolineaDto> aerolineas = new ArrayList<>();
        aerolineaRepository.findAll().forEach(aerolinea -> aerolineas.add(aerolineaMapper.aerolineaToAerolineaDto(aerolinea)));
        return aerolineas;
    }

    @Override
    public List<AerolineaDto> buscarAerolineasPorNombre(String nombre) {
        List<AerolineaDto> aerolineas = new ArrayList<>();
        aerolineaRepository.findAllByNombre(nombre).forEach(aerolinea -> aerolineas.add(aerolineaMapper.aerolineaToAerolineaDto(aerolinea)));
        return aerolineas;
    }

    @Override
    public List<AerolineaDto> buscarAerolineasPorPaisOrigen(String paisOrigen) {
        List<AerolineaDto> aerolineas = new ArrayList<>();
        aerolineaRepository.findAllByPaisOrigen(paisOrigen).forEach(aerolinea -> aerolineas.add(aerolineaMapper.aerolineaToAerolineaDto(aerolinea)));
        return aerolineas;
    }

    @Override
    public List<AerolineaDto> buscarAerolineasPorNombreYPaisOrigen(String nombre, String paisOrigen) {
        List<AerolineaDto> aerolineas = new ArrayList<>();
        aerolineaRepository.findAllByNombreAndPaisOrigen(nombre, paisOrigen).forEach(aerolinea -> aerolineas.add(aerolineaMapper.aerolineaToAerolineaDto(aerolinea)));
        return aerolineas;
    }

    @Override
    public Optional<AerolineaDto> actualizarAerolinea(Long id, AerolineaDto aerolinea) {
        return aerolineaRepository.findById(id).map(oldAerolinea -> {
            oldAerolinea.setNombre(aerolinea.nombre());
            oldAerolinea.setCodigoAerolinea(aerolinea.codigoAerolinea());
            oldAerolinea.setPaisOrigen(aerolinea.paisOrigen());
           return aerolineaMapper.aerolineaToAerolineaDto(aerolineaRepository.save(oldAerolinea));
        });
    }

    @Override
    public void eliminarAerolinea(Long id) {
        aerolineaRepository.deleteById(id);
    }

}
