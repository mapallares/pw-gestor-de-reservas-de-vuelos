package com.pw.gestorreservasvuelos.controllers;

import com.pw.gestorreservasvuelos.dto.ClienteDto;
import com.pw.gestorreservasvuelos.exceptions.ClienteAlreadyExistException;
import com.pw.gestorreservasvuelos.exceptions.ClienteNotFoundException;
import com.pw.gestorreservasvuelos.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @CrossOrigin(origins = "*")
    @GetMapping()
    public ResponseEntity<List<ClienteDto>> getAllClientes() {
        return ResponseEntity.ok(clienteService.buscarClientes());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{username}")
    public ResponseEntity<ClienteDto> getClienteByUsername(@PathVariable String username) {
        return clienteService.buscarClitentePorUsername(username)
                .map(cliente -> ResponseEntity.ok().body(cliente))
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con username <" + username + "> no encontrado"));
    }

    @CrossOrigin(origins = "*")
    @PostMapping()
    public ResponseEntity<ClienteDto> createCliente(@RequestBody ClienteDto cliente) throws URISyntaxException {
        return createNewCliente(cliente);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> updateCliente(@PathVariable Long id, @RequestBody ClienteDto newCliente) {
        Optional<ClienteDto> clienteUpadated = clienteService.actualizarCliente(id, newCliente);
        return clienteUpadated.map(cliente -> ResponseEntity.ok(cliente))
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con id <" + id + "> no encontrado"));
    }

    @CrossOrigin(origins = "*")
    private ResponseEntity<ClienteDto> createNewCliente(ClienteDto cliente) {
        if (clienteService.existeClientePorUsername(cliente.username())) {
            throw new ClienteAlreadyExistException("El nombre de usuario <" + cliente.username() + "> ya está en uso");
        }
        if (clienteService.existeClientePorEmail(cliente.email())) {
            throw new ClienteAlreadyExistException("El email <" + cliente.email() + "> ya está en uso");
        }
        ClienteDto newCliente = clienteService.guardarCliente(cliente);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCliente.id())
                .toUri();
        return ResponseEntity.created(location).body(newCliente);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        if(!clienteService.buscarClientePorId(id).isPresent()) {
            throw new ClienteNotFoundException("Cliente con id <" + id + "> encontrado");
        }
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

}
