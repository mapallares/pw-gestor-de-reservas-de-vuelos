package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.dto.ClienteDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IClienteService {

    ClienteDto guardarCliente (ClienteDto cliente);

    Optional<ClienteDto> buscarClientePorId (Long id);

    List<ClienteDto> buscarClientes ();

    List<ClienteDto> buscarClientesPorIds (Collection<Long> ids);

    List<ClienteDto> buscarClientesPorNombre (String nombre);

    Optional<ClienteDto> actualizarCliente (Long id, ClienteDto cliente);

    void eliminarCliente (Long id);

}
