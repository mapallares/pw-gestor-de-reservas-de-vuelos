package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.dto.AerolineaDto;
import com.pw.gestorreservasvuelos.dto.AeropuertoDto;
import com.pw.gestorreservasvuelos.dto.VueloDto;
import com.pw.gestorreservasvuelos.dto.VueloRequestDto;
import com.pw.gestorreservasvuelos.exceptions.AerolineaNotFoundException;
import com.pw.gestorreservasvuelos.exceptions.AeropuertoNotFoundException;
import com.pw.gestorreservasvuelos.exceptions.VueloNotFoundException;
import com.pw.gestorreservasvuelos.services.AerolineaService;
import com.pw.gestorreservasvuelos.services.AeropuertoService;
import com.pw.gestorreservasvuelos.services.ClienteService;
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
    private final ClienteService clienteService;
    private final AeropuertoService aeropuertoService;
    private final AerolineaService aerolineaService;

    public VueloController(VueloService vueloService, ClienteService clienteService, AeropuertoService aeropuertoService, AerolineaService aerolineaService) {
        this.vueloService = vueloService;
        this.clienteService = clienteService;
        this.aeropuertoService = aeropuertoService;
        this.aerolineaService = aerolineaService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping()
    public ResponseEntity<List<VueloDto>> getAllVuelos() {
        return ResponseEntity.ok(vueloService.buscarVuelos());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<VueloDto> getVueloById(@PathVariable Long id) {
        return vueloService.buscarVueloPorId(id)
                .map(vuelo -> ResponseEntity.ok().body(vuelo))
                .orElseThrow(() -> new VueloNotFoundException("Vuelo con id <" + id + "> no encontrado"));
    }

    @CrossOrigin(origins = "*")
    @PostMapping()
    public ResponseEntity<VueloDto> createVuelo(@RequestBody VueloRequestDto request) throws URISyntaxException {
        AeropuertoDto origen = aeropuertoService.buscarAeropuertoPorId(request.origenId())
                .orElseThrow(() -> new AeropuertoNotFoundException("El aeropuerto de origen con id <" + request.origenId() + "> no existe"));
        AeropuertoDto destino = aeropuertoService.buscarAeropuertoPorId(request.destinoId())
                .orElseThrow(() -> new AeropuertoNotFoundException("El aeropuerto de destino con id <" + request.destinoId() + "> no existe"));
        AerolineaDto aerolinea = aerolineaService.buscarAerolineaPorId(request.aerolineaId())
                .orElseThrow(() -> new AerolineaNotFoundException("La aerolinea con id <" + request.aerolineaId() + "> no existe"));
        VueloDto vuelo = new VueloDto(null,
                origen,
                destino,
                aerolinea,
                request.fechaSalida(),
                request.horaSalida(),
                request.duracion(),
                request.capacidad());
        return createNewVuelo(vuelo);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<VueloDto> updateVuelo(@PathVariable Long id, @RequestBody VueloRequestDto request) {
        AeropuertoDto origen = aeropuertoService.buscarAeropuertoPorId(request.origenId())
                .orElseThrow(() -> new AeropuertoNotFoundException("El aeropuerto de origen con id <" + request.origenId() + "> no existe"));
        AeropuertoDto destino = aeropuertoService.buscarAeropuertoPorId(request.destinoId())
                .orElseThrow(() -> new AeropuertoNotFoundException("El aeropuerto de destino con id <" + request.destinoId() + "> no existe"));
        AerolineaDto aerolinea = aerolineaService.buscarAerolineaPorId(request.aerolineaId())
                .orElseThrow(() -> new AerolineaNotFoundException("La aerolinea con id <" + request.aerolineaId() + "> no existe"));
        VueloDto newVuelo = new VueloDto(id,
                origen,
                destino,
                aerolinea,
                request.fechaSalida(),
                request.horaSalida(),
                request.duracion(),
                request.capacidad());
        Optional<VueloDto> vueloUpadated = vueloService.actualizarVuelo(id, newVuelo);
        return vueloUpadated.map(vuelo -> ResponseEntity.ok(vuelo))
                .orElseThrow(() -> new VueloNotFoundException("Vuelo con id <" + id + "> no encontrado"));
    }

    private ResponseEntity<VueloDto> createNewVuelo(VueloDto vuelo) {
        VueloDto newVuelo = vueloService.guardarVuelo(vuelo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newVuelo.id())
                .toUri();
        return ResponseEntity.created(location).body(newVuelo);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVuelo(@PathVariable Long id) {
        if(!vueloService.buscarVueloPorId(id).isPresent()) {
            throw new VueloNotFoundException("Vuelo con id <" + id + "> no encontrado");
        }
        vueloService.eliminarVuelo(id);
        return ResponseEntity.noContent().build();
    }

}
