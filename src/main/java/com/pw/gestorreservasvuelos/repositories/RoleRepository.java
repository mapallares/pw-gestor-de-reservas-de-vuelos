package com.pw.gestorreservasvuelos.repositories;

import com.pw.gestorreservasvuelos.entities.ERole;
import com.pw.gestorreservasvuelos.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
