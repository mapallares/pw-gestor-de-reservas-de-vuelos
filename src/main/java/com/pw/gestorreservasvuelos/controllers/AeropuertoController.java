package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.dto.AeropuertoDto;
import com.pw.gestorreservasvuelos.exceptions.AeropuertoNotFoundException;
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
    public ResponseEntity<List<AeropuertoDto>> getAllAeropuertos() {
        return ResponseEntity.ok(aeropuertoService.buscarAeropuertos());
    }

    @GetMapping("/id")
    public ResponseEntity<AeropuertoDto> getAeropuertoById(@PathVariable Long id) {
        return aeropuertoService.buscarAeropuertoPorId(id)
                .map(aeropuerto -> ResponseEntity.ok().body(aeropuerto))
                .orElseThrow(AeropuertoNotFoundException::new);
    }

    @PostMapping()
    public ResponseEntity<AeropuertoDto> createAeropuerto(@RequestBody AeropuertoDto aeropuerto) throws URISyntaxException {
        return createNewAeropuerto(aeropuerto);
    }

    @PutMapping("/id")
    public ResponseEntity<AeropuertoDto> updateAeropuerto(@PathVariable Long id, @RequestBody AeropuertoDto newAeropuerto) {
        Optional<AeropuertoDto> aeropuertoUpadated = aeropuertoService.actualizarAeropuerto(id, newAeropuerto);
        return aeropuertoUpadated.map(aeropuerto -> ResponseEntity.ok(aeropuerto))
                .orElseGet(() -> {
                    return createNewAeropuerto(newAeropuerto);
                });
    }

    private ResponseEntity<AeropuertoDto> createNewAeropuerto(AeropuertoDto aeropuerto) {
        AeropuertoDto newAeropuerto = aeropuertoService.guardarAeropuerto(aeropuerto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAeropuerto.id())
                .toUri();
        return ResponseEntity.created(location).body(newAeropuerto);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteAeropuerto(@PathVariable Long id) {
        aeropuertoService.eliminarAeropuerto(id);
        return ResponseEntity.noContent().build();
    }

}
