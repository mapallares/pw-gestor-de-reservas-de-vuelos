package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.entities.Aeropuerto;
import com.pw.gestorreservasvuelos.services.AeropuertoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/aeropuerto")
public class AeropuertoController {

    private final AeropuertoService aeropuertoService;

    public AeropuertoController(AeropuertoService aeropuertoService) {
        this.aeropuertoService = aeropuertoService;
    }

    @GetMapping()
    public ResponseEntity<List<Aeropuerto>> getAllAeropuertos() {
        return ResponseEntity.ok(aeropuertoService.buscarAeropuertos());
    }

    @GetMapping("/id")
    public ResponseEntity<Aeropuerto> getAeropuertoById(@PathVariable Long id) {
        return aeropuertoService.buscarAeropuertoPorId(id)
                .map(aeropuerto -> ResponseEntity.ok().body(aeropuerto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Aeropuerto> createAeropuerto(@RequestBody Aeropuerto aeropuerto) throws URISyntaxException {
        return createNewAeropuerto(aeropuerto);
    }

    @PutMapping("/id")
    public ResponseEntity<Aeropuerto> upatateAeropuerto(@PathVariable Long id, @RequestBody Aeropuerto newAeropuerto) {
        Optional<Aeropuerto> aeropuertoUpadated = aeropuertoService.actualizarAeropuerto(id, newAeropuerto);
        return aeropuertoUpadated.map(aeropuerto -> ResponseEntity.ok(aeropuerto))
                .orElseGet(() -> {
                    return createNewAeropuerto(newAeropuerto);
                });
    }

    private ResponseEntity<Aeropuerto> createNewAeropuerto(Aeropuerto aeropuerto) {
        Aeropuerto newAeropuerto = aeropuertoService.guardarAeropuerto(aeropuerto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAeropuerto.getId())
                .toUri();
        return ResponseEntity.created(location).body(newAeropuerto);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteAeropuerto(@PathVariable Long id) {
        aeropuertoService.eliminarAeropuerto(id);
        return ResponseEntity.noContent().build();
    }

}
