package com.pw.gestorreservasvuelos.repositories;

import com.pw.gestorreservasvuelos.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente save (Cliente cliente);

    Optional<Cliente> findByCorreoElectronico (String correoElectronico);

    Optional<Cliente> findById (Long id);

    List<Cliente> findByIdIn (Collection<Long> ids);

    List<Cliente> findAllByNombre (String nombre);

    List<Cliente> findAllByApellido (String apellido);

    List<Cliente> findAllByNombreOrApellido (String nombre, String apellido);

    List<Cliente> findAllByNombreAndApellido (String nombre, String apellido);

}