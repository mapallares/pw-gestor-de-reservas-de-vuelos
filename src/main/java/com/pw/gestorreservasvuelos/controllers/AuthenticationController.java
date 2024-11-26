package com.pw.gestorreservasvuelos.controllers;
import com.pw.gestorreservasvuelos.dto.JwtResponse;
import com.pw.gestorreservasvuelos.dto.LoginRequest;
import com.pw.gestorreservasvuelos.dto.SignupRequest;
import com.pw.gestorreservasvuelos.entities.Cliente;
import com.pw.gestorreservasvuelos.entities.ERole;
import com.pw.gestorreservasvuelos.entities.Role;
import com.pw.gestorreservasvuelos.entities.User;
import com.pw.gestorreservasvuelos.exceptions.ClienteAlreadyExistException;
import com.pw.gestorreservasvuelos.exceptions.ClienteNotFoundException;
import com.pw.gestorreservasvuelos.exceptions.ResourceNotFoundException;
import com.pw.gestorreservasvuelos.repositories.ClienteRepository;
import com.pw.gestorreservasvuelos.repositories.RoleRepository;
import com.pw.gestorreservasvuelos.repositories.UserRepository;
import com.pw.gestorreservasvuelos.security.jwt.JwtUtil;
import com.pw.gestorreservasvuelos.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @CrossOrigin(origins = "*")
    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(),
                        loginRequest.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken= jwtUtil.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(role -> role.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwtToken, "Bearer", userDetails.getUsername(), roles));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest request){
        if (clienteRepository.existsByUsername(request.username())) {
            throw new ClienteAlreadyExistException("El nombre de usuario <" + request.username() + "> ya está en uso");
        }
        if (clienteRepository.existsByEmail(request.email())) {
            throw new ClienteAlreadyExistException("El email <" + request.email() + "> ya está en uso");
        }

        User u = new User(request.username(), passwordEncoder.encode(request.password()), request.email());
        Cliente user = new Cliente(null,
                request.nombre(),
                request.apellido(),
                request.fechaNacimiento(),
                request.direccion(),
                request.telefono(),
                request.email(),
                request.username(),
                passwordEncoder.encode(request.password()),
                new HashSet<>(), new ArrayList<>());

        Set<String> requestRoles = request.roles();
        Set<Role> roles = new HashSet<>();

        if (requestRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("El rol <" + ERole.ROLE_USER + "> no existe"));
            roles.add(userRole);
        } else {
            requestRoles.forEach(role -> {
                switch (role) {
                    case "Admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException("El rol <" + ERole.ROLE_ADMIN + "> no existe"));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new ResourceNotFoundException("El rol <" + ERole.ROLE_USER + "> no existe"));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        u.setRoles(roles);
        userRepository.save(u);
        Cliente newUser = clienteRepository.save(user);

        return ResponseEntity.ok(newUser);
    }
}
