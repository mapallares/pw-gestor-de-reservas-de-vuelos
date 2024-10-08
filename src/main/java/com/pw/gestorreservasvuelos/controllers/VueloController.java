package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.dto.VueloDto;
import com.pw.gestorreservasvuelos.exceptions.VueloNotFoundException;
import com.pw.gestorreservasvuelos.services.VueloService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vuelos")
public class VueloController {

    private final VueloService vueloService;

    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    @GetMapping()
    public ResponseEntity<List<VueloDto>> getAllVuelos() {
        return ResponseEntity.ok(vueloService.buscarVuelos());
    }

    @GetMapping("/id")
    public ResponseEntity<VueloDto> getVueloById(@PathVariable Long id) {
        return vueloService.buscarVueloPorId(id)
                .map(vuelo -> ResponseEntity.ok().body(vuelo))
                .orElseThrow(VueloNotFoundException::new);
    }

    @PostMapping()
    public ResponseEntity<VueloDto> createVuelo(@RequestBody VueloDto vuelo) throws URISyntaxException {
        return createNewVuelo(vuelo);
    }

    @PutMapping("/id")
    public ResponseEntity<VueloDto> updateVuelo(@PathVariable Long id, @RequestBody VueloDto newVuelo) {
        Optional<VueloDto> vueloUpadated = vueloService.actualizarVuelo(id, newVuelo);
        return vueloUpadated.map(vuelo -> ResponseEntity.ok(vuelo))
                .orElseGet(() -> {
                    return createNewVuelo(newVuelo);
                });
    }

    private ResponseEntity<VueloDto> createNewVuelo(VueloDto vuelo) {
        VueloDto newVuelo = vueloService.guardarVuelo(vuelo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newVuelo.id())
                .toUri();
        return ResponseEntity.created(location).body(newVuelo);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteVuelo(@PathVariable Long id) {
        vueloService.eliminarVuelo(id);
        return ResponseEntity.noContent().build();
    }

}
