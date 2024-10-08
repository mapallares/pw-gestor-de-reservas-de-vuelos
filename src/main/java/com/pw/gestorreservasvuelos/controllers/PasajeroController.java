package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.dto.PasajeroDto;
import com.pw.gestorreservasvuelos.exceptions.PasajeroNotFoundException;
import com.pw.gestorreservasvuelos.services.PasajeroService;
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

    public PasajeroController(PasajeroService pasajeroService) {
        this.pasajeroService = pasajeroService;
    }

    @GetMapping()
    public ResponseEntity<List<PasajeroDto>> getAllPasajeros() {
        return ResponseEntity.ok(pasajeroService.buscarPasajeros());
    }

    @GetMapping("/id")
    public ResponseEntity<PasajeroDto> getPasajeroById(@PathVariable Long id) {
        return pasajeroService.buscarPasajeroPorId(id)
                .map(pasajero -> ResponseEntity.ok().body(pasajero))
                .orElseThrow(PasajeroNotFoundException::new);
    }

    @PostMapping()
    public ResponseEntity<PasajeroDto> createPasajero(@RequestBody PasajeroDto pasajero) throws URISyntaxException {
        return createNewPasajero(pasajero);
    }

    @PutMapping("/id")
    public ResponseEntity<PasajeroDto> updatePasajero(@PathVariable Long id, @RequestBody PasajeroDto newPasajero) {
        Optional<PasajeroDto> pasajeroUpadated = pasajeroService.actualizarPasajero(id, newPasajero);
        return pasajeroUpadated.map(pasajero -> ResponseEntity.ok(pasajero))
                .orElseGet(() -> {
                    return createNewPasajero(newPasajero);
                });
    }

    private ResponseEntity<PasajeroDto> createNewPasajero(PasajeroDto pasajero) {
        PasajeroDto newPasajero = pasajeroService.guardarPasajero(pasajero);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPasajero.id())
                .toUri();
        return ResponseEntity.created(location).body(newPasajero);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deletePasajero(@PathVariable Long id) {
        pasajeroService.eliminarPasajero(id);
        return ResponseEntity.noContent().build();
    }

}