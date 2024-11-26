package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.dto.AerolineaDto;
import com.pw.gestorreservasvuelos.exceptions.AerolineaAlreadyExistException;
import com.pw.gestorreservasvuelos.exceptions.AerolineaNotFoundException;
import com.pw.gestorreservasvuelos.services.AerolineaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @CrossOrigin(origins = "*")
    @GetMapping()
    public ResponseEntity<List<AerolineaDto>> getAllAerolineas() {
        return ResponseEntity.ok(aerolineaService.buscarAerolineas());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<AerolineaDto> getAerolineaById(@PathVariable Long id) {
        return aerolineaService.buscarAerolineaPorId(id)
                .map(aerolinea -> ResponseEntity.ok().body(aerolinea))
                .orElseThrow(() -> new AerolineaNotFoundException("Aerolinea con id <" + id + "> no encontrada"));
    }

    @CrossOrigin(origins = "*")
    @PostMapping()
    public ResponseEntity<AerolineaDto> createAerolinea(@RequestBody AerolineaDto aerolinea) throws URISyntaxException {
        return createNewAerolinea(aerolinea);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<AerolineaDto> updateAerolinea(@PathVariable Long id, @RequestBody AerolineaDto newAerolinea) {
        Optional<AerolineaDto> aerolineaUpadated = aerolineaService.actualizarAerolinea(id, newAerolinea);
        return aerolineaUpadated.map(aerolinea -> ResponseEntity.ok(aerolinea))
                .orElseThrow(() -> new AerolineaNotFoundException("Aerolinea con id <" + id + "> no encontrada"));
    }

    private ResponseEntity<AerolineaDto> createNewAerolinea(AerolineaDto aerolinea) {
        if(aerolineaService.buscarAerolineaPorCodigo(aerolinea.codigoAerolinea()).isPresent()) {
            throw new AerolineaAlreadyExistException("El código de aerolinea <" + aerolinea.codigoAerolinea() + "> ya se ha utilizado");
        }
        AerolineaDto newAerolinea = aerolineaService.guardarAerolinea(aerolinea);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newAerolinea.id())
                .toUri();
        return ResponseEntity.created(location).body(newAerolinea);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAerolinea(@PathVariable Long id) {
        if (!aerolineaService.buscarAerolineaPorId(id).isPresent()) {
            throw new AerolineaNotFoundException("Aerolinea con id <" + id + "> no encontrada");
        }
        aerolineaService.eliminarAerolinea(id);
        return ResponseEntity.noContent().build();
    }

}
