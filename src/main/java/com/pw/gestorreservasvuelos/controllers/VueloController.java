package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.entities.Vuelo;
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
    public ResponseEntity<List<Vuelo>> getAllVuelos() {
        return ResponseEntity.ok(vueloService.buscarVuelos());
    }

    @GetMapping("/id")
    public ResponseEntity<Vuelo> getVueloById(@PathVariable Long id) {
        return vueloService.buscarVueloPorId(id)
                .map(vuelo -> ResponseEntity.ok().body(vuelo))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Vuelo> createVuelo(@RequestBody Vuelo vuelo) throws URISyntaxException {
        return createNewVuelo(vuelo);
    }

    @PutMapping("/id")
    public ResponseEntity<Vuelo> upatateVuelo(@PathVariable Long id, @RequestBody Vuelo newVuelo) {
        Optional<Vuelo> vueloUpadated = vueloService.actualizarVuelo(id, newVuelo);
        return vueloUpadated.map(vuelo -> ResponseEntity.ok(vuelo))
                .orElseGet(() -> {
                    return createNewVuelo(newVuelo);
                });
    }

    private ResponseEntity<Vuelo> createNewVuelo(Vuelo vuelo) {
        Vuelo newVuelo = vueloService.guardarVuelo(vuelo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newVuelo.getId())
                .toUri();
        return ResponseEntity.created(location).body(newVuelo);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteVuelo(@PathVariable Long id) {
        vueloService.eliminarVuelo(id);
        return ResponseEntity.noContent().build();
    }

}
