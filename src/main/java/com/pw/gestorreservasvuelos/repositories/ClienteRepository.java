package com.pw.gestorreservasvuelos.repositories;

import com.pw.gestorreservasvuelos.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente save(Cliente cliente);

    Cliente findByCorreoElectronico (String correoElectronico);

    Cliente findById (long id);

    List<Cliente> findAllByNombre (String nombre);

    List<Cliente> findAllByApellido (String apellido);

    List<Cliente> findAllByNombreOrApellido (String nombre, String apellido);

    List<Cliente> findAllByNombreAndApellido (String nombre, String apellido);

}