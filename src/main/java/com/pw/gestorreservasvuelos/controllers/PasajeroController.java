package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.dto.PasajeroDto;
import com.pw.gestorreservasvuelos.dto.PasajeroRequestDto;
import com.pw.gestorreservasvuelos.dto.ReservaDto;
import com.pw.gestorreservasvuelos.exceptions.PasajeroAlreadyExistException;
import com.pw.gestorreservasvuelos.exceptions.PasajeroNotFoundException;
import com.pw.gestorreservasvuelos.exceptions.ReservaNotFoundException;
import com.pw.gestorreservasvuelos.services.PasajeroService;
import com.pw.gestorreservasvuelos.services.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pasajeros")
public class PasajeroController {

    private final PasajeroService pasajeroService;
    private final ReservaService reservaService;

    public PasajeroController(PasajeroService pasajeroService, ReservaService reservaService) {
        this.pasajeroService = pasajeroService;
        this.reservaService = reservaService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping()
    public ResponseEntity<List<PasajeroDto>> getAllPasajeros() {
        return ResponseEntity.ok(pasajeroService.buscarPasajeros());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<PasajeroDto> getPasajeroById(@PathVariable Long id) {
        return pasajeroService.buscarPasajeroPorId(id)
                .map(pasajero -> ResponseEntity.ok().body(pasajero))
                .orElseThrow(() -> new PasajeroNotFoundException("Pasajero con id <" + id + "> no encontrado"));
    }

    @CrossOrigin(origins = "*")
    @PostMapping()
    public ResponseEntity<PasajeroDto> createPasajero(@RequestBody PasajeroRequestDto request) throws URISyntaxException {
        ReservaDto reserva = reservaService.buscarReservaPorId(request.reservaId())
                .orElseThrow(() -> new ReservaNotFoundException("Reserva con id <" + request.reservaId() + "> no existe"));
        PasajeroDto pasajero = new PasajeroDto(null,
                request.nombre(),
                request.apellido(),
                request.fechaNacimiento(),
                request.identificacion(),
                request.email());
        PasajeroDto newPasajero = pasajeroService.guardarPasajero(pasajero);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPasajero.id())
                .toUri();
        PasajeroDto pasajeroReserva = pasajeroService.actualizarPasajero(newPasajero.id(), newPasajero, reserva)
                .orElseThrow(() -> new PasajeroNotFoundException("Pasajero con id <" + newPasajero.id() + "> no encontrado"));
        return ResponseEntity.created(location).body(newPasajero);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<PasajeroDto> updatePasajero(@PathVariable Long id, @RequestBody PasajeroRequestDto request) {
        ReservaDto reserva = reservaService.buscarReservaPorId(request.reservaId())
                .orElseThrow(() -> new ReservaNotFoundException("Reserva con id <" + request.reservaId() + "> no existe"));
        PasajeroDto newPasajero = new PasajeroDto(id,
                request.nombre(),
                request.apellido(),
                request.fechaNacimiento(),
                request.identificacion(),
                request.email());
        Optional<PasajeroDto> pasajeroUpadated = pasajeroService.actualizarPasajero(id, newPasajero, reserva);
        return pasajeroUpadated.map(pasajero -> ResponseEntity.ok(pasajero))
                .orElseThrow(() -> new PasajeroNotFoundException("Pasajero con id <" + id + "> no encontrado"));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePasajero(@PathVariable Long id) {
        if(!pasajeroService.buscarPasajeroPorId(id).isPresent()) {
            throw new PasajeroNotFoundException("Pasajero con id <" + id + "> no encontrado");
        }
        pasajeroService.eliminarPasajero(id);
        return ResponseEntity.noContent().build();
    }

}