package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.dto.ClienteDto;
import com.pw.gestorreservasvuelos.dto.ReservaDto;
import com.pw.gestorreservasvuelos.dto.ReservaRequestDto;
import com.pw.gestorreservasvuelos.dto.VueloDto;
import com.pw.gestorreservasvuelos.entities.Cliente;
import com.pw.gestorreservasvuelos.exceptions.ClienteNotFoundException;
import com.pw.gestorreservasvuelos.exceptions.ReservaNotFoundException;
import com.pw.gestorreservasvuelos.services.ClienteService;
import com.pw.gestorreservasvuelos.services.ReservaService;
import com.pw.gestorreservasvuelos.services.VueloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final ClienteService clienteService;
    private final VueloService vueloService;

    public ReservaController(ReservaService reservaService, ClienteService clienteService, VueloService vueloService) {
        this.reservaService = reservaService;
        this.clienteService = clienteService;
        this.vueloService = vueloService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping()
    public ResponseEntity<List<ReservaDto>> getAllReservas() {
        return ResponseEntity.ok(reservaService.buscarReservas());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> getReservaById(@PathVariable Long id) {
        return reservaService.buscarReservaPorId(id)
                .map(reserva -> ResponseEntity.ok().body(reserva))
                .orElseThrow(() -> new ReservaNotFoundException("Reserva con id <" + id + "> no encontrada"));
    }

    @CrossOrigin(origins = "*")
    @PostMapping()
    public ResponseEntity<ReservaDto> createReserva(@RequestBody ReservaRequestDto request) throws URISyntaxException {
        ClienteDto cliente = clienteService.buscarClientePorId(request.clienteId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con id <" + request.clienteId() + "> no encontrado"));
        List<VueloDto> vuelos = vueloService.buscarVuelosPorIds(request.vuelosIds());
        ReservaDto reserva = new ReservaDto(null,
                cliente,
                request.fechaReserva(),
                request.numeroPasajeros(),
                null, vuelos);
        return createNewReserva(reserva);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDto> updateReserva(@PathVariable Long id, @RequestBody ReservaRequestDto request) {
        ClienteDto cliente = clienteService.buscarClientePorId(request.clienteId())
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con id <" + request.clienteId() + "> no encontrado"));
        List<VueloDto> vuelos = vueloService.buscarVuelosPorIds(request.vuelosIds());
        ReservaDto newReserva = new ReservaDto(id,
                cliente,
                request.fechaReserva(),
                request.numeroPasajeros(),
                null, vuelos);
        Optional<ReservaDto> reservaUpadated = reservaService.actualizarReserva(id, newReserva);
        return reservaUpadated.map(reserva -> ResponseEntity.ok(reserva))
                .orElseThrow(() -> new ReservaNotFoundException("Reserva con id <" + id + "> no encontrada"));
    }

    private ResponseEntity<ReservaDto> createNewReserva(ReservaDto reserva) {
        ReservaDto newReserva = reservaService.guardarReserva(reserva);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newReserva.id())
                .toUri();
        return ResponseEntity.created(location).body(newReserva);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        if(!reservaService.buscarReservaPorId(id).isPresent()) {
            throw new ReservaNotFoundException("Reserva con id <" + id + "> no encontrada");
        }
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }

}
