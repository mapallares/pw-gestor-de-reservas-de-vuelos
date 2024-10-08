package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.dto.ReservaDto;
import com.pw.gestorreservasvuelos.exceptions.VueloNotFoundException;
import com.pw.gestorreservasvuelos.services.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping()
    public ResponseEntity<List<ReservaDto>> getAllReservas() {
        return ResponseEntity.ok(reservaService.buscarReservas());
    }

    @GetMapping("/id")
    public ResponseEntity<ReservaDto> getReservaById(@PathVariable Long id) {
        return reservaService.buscarReservaPorId(id)
                .map(reserva -> ResponseEntity.ok().body(reserva))
                .orElseThrow(VueloNotFoundException::new);
    }

    @PostMapping()
    public ResponseEntity<ReservaDto> createReserva(@RequestBody ReservaDto reserva) throws URISyntaxException {
        return createNewReserva(reserva);
    }

    @PutMapping("/id")
    public ResponseEntity<ReservaDto> updateReserva(@PathVariable Long id, @RequestBody ReservaDto newReserva) {
        Optional<ReservaDto> reservaUpadated = reservaService.actualizarReserva(id, newReserva);
        return reservaUpadated.map(reserva -> ResponseEntity.ok(reserva))
                .orElseGet(() -> {
                    return createNewReserva(newReserva);
                });
    }

    private ResponseEntity<ReservaDto> createNewReserva(ReservaDto reserva) {
        ReservaDto newReserva = reservaService.guardarReserva(reserva);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newReserva.id())
                .toUri();
        return ResponseEntity.created(location).body(newReserva);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }

}
