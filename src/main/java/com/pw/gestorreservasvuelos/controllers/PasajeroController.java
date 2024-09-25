package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.entities.Pasajero;
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
    public ResponseEntity<List<Pasajero>> getAllPasajeros() {
        return ResponseEntity.ok(pasajeroService.buscarPasajeros());
    }

    @GetMapping("/id")
    public ResponseEntity<Pasajero> getPasajeroById(@PathVariable Long id) {
        return pasajeroService.buscarPasajeroPorId(id)
                .map(pasajero -> ResponseEntity.ok().body(pasajero))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Pasajero> createPasajero(@RequestBody Pasajero pasajero) throws URISyntaxException {
        return createNewPasajero(pasajero);
    }

    @PutMapping("/id")
    public ResponseEntity<Pasajero> upatatePasajero(@PathVariable Long id, @RequestBody Pasajero newPasajero) {
        Optional<Pasajero> pasajeroUpadated = pasajeroService.actualizarPasajero(id, newPasajero);
        return pasajeroUpadated.map(pasajero -> ResponseEntity.ok(pasajero))
                .orElseGet(() -> {
                    return createNewPasajero(newPasajero);
                });
    }

    private ResponseEntity<Pasajero> createNewPasajero(Pasajero pasajero) {
        Pasajero newPasajero = pasajeroService.guardarPasajero(pasajero);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPasajero.getId())
                .toUri();
        return ResponseEntity.created(location).body(newPasajero);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deletePasajero(@PathVariable Long id) {
        pasajeroService.eliminarPasajero(id);
        return ResponseEntity.noContent().build();
    }

}
