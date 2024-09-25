package com.pw.gestorreservasvuelos.services;

import com.pw.gestorreservasvuelos.entities.Cliente;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IClienteService {

    Cliente guardarCliente (Cliente cliente);

    Optional<Cliente> buscarClientePorId (Long id);

    List<Cliente> buscarClientes ();

    List<Cliente> buscarClientesPorIds (Collection<Long> ids);

    List<Cliente> buscarClientesPorNombre (String nombre);

    Optional<Cliente> actualizarCliente (Long id, Cliente cliente);

    void eliminarCliente (Long id);

}
