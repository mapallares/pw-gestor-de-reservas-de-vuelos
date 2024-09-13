package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Cliente;
import com.pw.gestorreservasvuelos.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> buscarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public List<Cliente> buscarClientesPorIds(Collection<Long> ids) {
        return clienteRepository.findByIdIn(ids);
    }

    @Override
    public List<Cliente> buscarClientesPorNombre(String nombre) {
        return clienteRepository.findAllByNombre(nombre);
    }

    @Override
    public Optional<Cliente> actualizarCliente(Long id, Cliente cliente) {
        return clienteRepository.findById(id).map(oldCliente -> {
            oldCliente.setNombre(cliente.getNombre());
            oldCliente.setApellido(cliente.getApellido());
            oldCliente.setDireccion(cliente.getDireccion());
            oldCliente.setTelefono(cliente.getTelefono());
            oldCliente.setCorreoElectronico(cliente.getCorreoElectronico());
            return clienteRepository.save(oldCliente);
        });
    }

}
