package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Aerolinea;
import com.pw.gestorreservasvuelos.repositories.AerolineaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class AerolineaService implements IAerolineaService {

    private AerolineaRepository aerolineaRepository;

    AerolineaService(AerolineaRepository aerolineaRepository) {
        this.aerolineaRepository = aerolineaRepository;
    }

    @Override
    public Aerolinea guardarAerolinea(Aerolinea aerolinea) {
        return aerolineaRepository.save(aerolinea);
    }

    @Override
    public Optional<Aerolinea> buscarAerolineaPorId(Long id) {
        return aerolineaRepository.findById(id);
    }

    @Override
    public List<Aerolinea> buscarAerolineasPorIds(Collection<Long> ids) {
        return aerolineaRepository.findByIdIn(ids);
    }

    @Override
    public Optional<Aerolinea> buscarAerolineaPorCodigo(String codigo) {
        return aerolineaRepository.findByCodigoAerolinea(codigo);
    }

    @Override
    public List<Aerolinea> buscarAerolineas() {
        return aerolineaRepository.findAll();
    }

    @Override
    public List<Aerolinea> buscarAerolineasPorNombre(String nombre) {
        return aerolineaRepository.findAllByNombre(nombre);
    }

    @Override
    public List<Aerolinea> buscarAerolineasPorPaisOrigen(String paisOrigen) {
        return aerolineaRepository.findAllByPaisOrigen(paisOrigen);
    }

    @Override
    public List<Aerolinea> buscarAerolineasPorNombreYPaisOrigen(String nombre, String paisOrigen) {
        return aerolineaRepository.findAllByNombreAndPaisOrigen(nombre, paisOrigen);
    }

    @Override
    public Optional<Aerolinea> actualizarAerolinea(Long id, Aerolinea aerolinea) {
        return aerolineaRepository.findById(id).map(oldAerolinea -> {
            oldAerolinea.setNombre(aerolinea.getNombre());
            oldAerolinea.setCodigoAerolinea(aerolinea.getCodigoAerolinea());
            oldAerolinea.setPaisOrigen(aerolinea.getPaisOrigen());
           return aerolineaRepository.save(oldAerolinea);
        });
    }

}
