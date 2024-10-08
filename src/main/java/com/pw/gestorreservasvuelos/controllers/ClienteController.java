package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.dto.ClienteDto;
import com.pw.gestorreservasvuelos.exceptions.ClienteNotFoundException;
import com.pw.gestorreservasvuelos.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping()
    public ResponseEntity<List<ClienteDto>> getAllClientes() {
        return ResponseEntity.ok(clienteService.buscarClientes());
    }

    @GetMapping("/id")
    public ResponseEntity<ClienteDto> getClienteById(@PathVariable Long id) {
        return clienteService.buscarClientePorId(id)
                .map(cliente -> ResponseEntity.ok().body(cliente))
                .orElseThrow(ClienteNotFoundException::new);
    }

    @PostMapping()
    public ResponseEntity<ClienteDto> createCliente(@RequestBody ClienteDto cliente) throws URISyntaxException {
        return createNewCliente(cliente);
    }

    @PutMapping("/id")
    public ResponseEntity<ClienteDto> updateCliente(@PathVariable Long id, @RequestBody ClienteDto newCliente) {
        Optional<ClienteDto> clienteUpadated = clienteService.actualizarCliente(id, newCliente);
        return clienteUpadated.map(cliente -> ResponseEntity.ok(cliente))
                .orElseGet(() -> {
                    return createNewCliente(newCliente);
                });
    }

    private ResponseEntity<ClienteDto> createNewCliente(ClienteDto cliente) {
        ClienteDto newCliente = clienteService.guardarCliente(cliente);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCliente.id())
                .toUri();
        return ResponseEntity.created(location).body(newCliente);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

}
