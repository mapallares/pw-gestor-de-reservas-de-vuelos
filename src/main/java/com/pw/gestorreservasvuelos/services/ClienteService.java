package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.dto.ClienteDto;
import com.pw.gestorreservasvuelos.dto.ClienteMapper;
import com.pw.gestorreservasvuelos.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    ClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public ClienteDto guardarCliente(ClienteDto cliente) {
        return clienteMapper.clienteToClienteDtoWithoutId(clienteRepository.save(clienteMapper.clienteDtoWithoutIdToCliente(cliente)));
    }

    @Override
    public Optional<ClienteDto> buscarClientePorId(Long id) {
        return clienteRepository.findById(id).map(cliente -> clienteMapper.clienteToClienteDto(cliente));
    }

    @Override
    public List<ClienteDto> buscarClientes() {
        List<ClienteDto> clientes = new ArrayList<>();
        clienteRepository.findAll().forEach(cliente -> clientes.add(clienteMapper.clienteToClienteDtoWithoutId(cliente)));
        return clientes;
    }

    @Override
    public List<ClienteDto> buscarClientesPorIds(Collection<Long> ids) {
        List<ClienteDto> clientes = new ArrayList<>();
        clienteRepository.findByIdIn(ids).forEach(cliente -> clientes.add(clienteMapper.clienteToClienteDto(cliente)));
        return clientes;
    }

    @Override
    public List<ClienteDto> buscarClientesPorNombre(String nombre) {
        List<ClienteDto> clientes = new ArrayList<>();
        clienteRepository.findAllByNombre(nombre).forEach(cliente -> clientes.add(clienteMapper.clienteToClienteDtoWithoutId(cliente)));
        return clientes;
    }

    @Override
    public Optional<ClienteDto> actualizarCliente(Long id, ClienteDto cliente) {
        return clienteRepository.findById(id).map(oldCliente -> {
            oldCliente.setNombre(cliente.nombre());
            oldCliente.setApellido(cliente.apellido());
            oldCliente.setFechaNacimiento(cliente.fechaNacimiento());
            oldCliente.setDireccion(cliente.direccion());
            oldCliente.setTelefono(cliente.telefono());
            oldCliente.setCorreoElectronico(cliente.correoElectronico());
            return clienteMapper.clienteToClienteDto(clienteRepository.save(oldCliente));
        });
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

}
