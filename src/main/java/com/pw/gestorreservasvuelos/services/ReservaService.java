package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.dto.ReservaMapper;
import com.pw.gestorreservasvuelos.entities.Cliente;
import com.pw.gestorreservasvuelos.dto.ReservaDto;
import com.pw.gestorreservasvuelos.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService implements IReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;

    ReservaService(ReservaRepository reservaRepository, ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
    }

    @Override
    public ReservaDto guardarReserva(ReservaDto reserva) {
        return reservaMapper.reservaToReservaDtoWithoutId(reservaRepository.save(reservaMapper.reservaDtoWithoutIdToReserva(reserva)));
    }

    @Override
    public Optional<ReservaDto> buscarReservaPorId(Long id) {
        return reservaRepository.findById(id).map(reserva -> reservaMapper.reservaToReservaDto(reserva));
    }

    @Override
    public List<ReservaDto> buscarReservasPorIds(Collection<Long> ids) {
        List<ReservaDto> reservas = new ArrayList<>();
        reservaRepository.findByIdIn(ids).forEach(reserva -> reservas.add(reservaMapper.reservaToReservaDtoWithoutId(reserva)));
        return reservas;
    }

    @Override
    public List<ReservaDto> buscarReservas() {
        List<ReservaDto> reservas = new ArrayList<>();
        reservaRepository.findAll().forEach(reserva -> reservas.add(reservaMapper.reservaToReservaDtoWithoutId(reserva)));
        return reservas;
    }

    @Override
    public List<ReservaDto> buscarReservasPorFechaReserva(LocalDate fechaReserva) {
        List<ReservaDto> reservas = new ArrayList<>();
        reservaRepository.findAllByFechaReserva(fechaReserva).forEach(reserva -> reservas.add(reservaMapper.reservaToReservaDtoWithoutId(reserva)));
        return reservas;
    }

    @Override
    public List<ReservaDto> buscarReservasPorFechaReservaEntre(LocalDate fechaReserva1, LocalDate fechaReserva2) {
        List<ReservaDto> reservas = new ArrayList<>();
        reservaRepository.findAllByFechaReservaBetween(fechaReserva1, fechaReserva2).forEach(reserva -> reservas.add(reservaMapper.reservaToReservaDtoWithoutId(reserva)));
        return reservas;
    }

    @Override
    public List<ReservaDto> buscarReservasPorCliente(Cliente cliente) {
        List<ReservaDto> reservas = new ArrayList<>();
        reservaRepository.findAllByCliente(cliente).forEach(reserva -> reservas.add(reservaMapper.reservaToReservaDtoWithoutId(reserva)));
        return reservas;
    }

    @Override
    public List<ReservaDto> buscarReservasPorNumeroPasajeros(Integer numeroPasajeros) {
        List<ReservaDto> reservas = new ArrayList<>();
        reservaRepository.findAllByNumeroPasajeros(numeroPasajeros).forEach(reserva -> reservas.add(reservaMapper.reservaToReservaDtoWithoutId(reserva)));
        return reservas;
    }

    @Override
    public List<ReservaDto> buscarReservasPorNumeroPasajerosMenorOIgualQue(Integer numeroPasajeros) {
        List<ReservaDto> reservas = new ArrayList<>();
        reservaRepository.findAllByNumeroPasajerosLessThanEqual(numeroPasajeros).forEach(reserva -> reservas.add(reservaMapper.reservaToReservaDtoWithoutId(reserva)));
        return reservas;
    }

    @Override
    public List<ReservaDto> buscarReservasPorNumeroPasajeroMayorOIgualQue(Integer numeroPasajeros) {
        List<ReservaDto> reservas = new ArrayList<>();
        reservaRepository.findAllByNumeroPasajerosGreaterThanEqual(numeroPasajeros).forEach(reserva -> reservas.add(reservaMapper.reservaToReservaDtoWithoutId(reserva)));
        return reservas;
    }

    @Override
    public Optional<ReservaDto> actualizarReserva(Long id, ReservaDto reserva) {
        return reservaRepository.findById(id).map(oldReserva -> {
            oldReserva.setFechaReserva(reserva.fechaReserva());
            oldReserva.setNumeroPasajeros(reserva.numeroPasajeros());
            return reservaMapper.reservaToReservaDto(reservaRepository.save(oldReserva));
        });
    }

    @Override
    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

}
