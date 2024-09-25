package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Cliente;
import com.pw.gestorreservasvuelos.entities.Reserva;
import com.pw.gestorreservasvuelos.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService implements IReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public Reserva guardarReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public Optional<Reserva> buscarReservaPorId(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public List<Reserva> buscarReservasPorIds(Collection<Long> ids) {
        return reservaRepository.findByIdIn(ids);
    }

    @Override
    public List<Reserva> buscarReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> buscarReservasPorFechaReserva(LocalDate fechaReserva) {
        return reservaRepository.findAllByFechaReserva(fechaReserva);
    }

    @Override
    public List<Reserva> buscarReservasPorFechaReservaEntre(LocalDate fechaReserva1, LocalDate fechaReserva2) {
        return reservaRepository.findAllByFechaReservaBetween(fechaReserva1, fechaReserva2);
    }

    @Override
    public List<Reserva> buscarReservasPorCliente(Cliente cliente) {
        return reservaRepository.findAllByCliente(cliente);
    }

    @Override
    public List<Reserva> buscarReservasPorNumeroPasajeros(int numeroPasajeros) {
        return reservaRepository.findAllByNumeroPasajeros(numeroPasajeros);
    }

    @Override
    public List<Reserva> buscarReservasPorNumeroPasajerosMenorOIgualQue(int numeroPasajeros) {
        return reservaRepository.findAllByNumeroPasajerosLessThanEqual(numeroPasajeros);
    }

    @Override
    public List<Reserva> buscarReservasPorNumeroPasajeroMayorOIgualQue(int numeroPasajeros) {
        return reservaRepository.findAllByNumeroPasajerosGreaterThanEqual(numeroPasajeros);
    }

    @Override
    public Optional<Reserva> actualizarReserva(Long id, Reserva reserva) {
        return reservaRepository.findById(id).map(oldReserva -> {
            oldReserva.setCliente(reserva.getCliente());
            oldReserva.setFechaReserva(reserva.getFechaReserva());
            oldReserva.setNumeroPasajeros(reserva.getNumeroPasajeros());
            return reservaRepository.save(oldReserva);
        });
    }

    @Override
    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }

}
