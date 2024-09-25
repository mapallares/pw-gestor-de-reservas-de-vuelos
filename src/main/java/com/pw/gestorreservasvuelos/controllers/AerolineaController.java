package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.entities.Aerolinea;
import com.pw.gestorreservasvuelos.services.AerolineaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/aerolineas")
public class AerolineaController {

    private final AerolineaService aerolineaService;

    public AerolineaController(AerolineaService aerolineaService) {
        this.aerolineaService = aerolineaService;
    }

    @GetMapping()
    public ResponseEntity<List<Aerolinea>> getAllAerolineas() {
        return ResponseEntity.ok(aerolineaService.buscarAerolineas());
    }

    @GetMapping("/id")
    public ResponseEntity<Aerolinea> getAerolineaById(@PathVariable Long id) {
        return aerolineaService.buscarAerolineaPorId(id)
                .map(aerolinea -> ResponseEntity.ok().body(aerolinea))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Aerolinea> createAerolinea(@RequestBody Aerolinea aerolinea) throws URISyntaxException {
        return createNewAerolinea(aerolinea);
    }

    @PutMapping("/id")
    public ResponseEntity<Aerolinea> upatateAerolinea(@PathVariable Long id, @RequestBody Aerolinea newAerolinea) {
        Optional<Aerolinea> aerolineaUpadated = aerolineaService.actualizarAerolinea(id, newAerolinea);
        return aerolineaUpadated.map(aerolinea -> ResponseEntity.ok(aerolinea))
                .orElseGet(() -> {
                    return createNewAerolinea(newAerolinea);
                });
    }

    private ResponseEntity<Aerolinea> createNewAerolinea(Aerolinea aerolinea) {
        Aerolinea newAerolinea = aerolineaService.guardarAerolinea(aerolinea);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAerolinea.getId())
                .toUri();
        return ResponseEntity.created(location).body(newAerolinea);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteAerolinea(@PathVariable Long id) {
        aerolineaService.eliminarAerolinea(id);
        return ResponseEntity.noContent().build();
    }

}
