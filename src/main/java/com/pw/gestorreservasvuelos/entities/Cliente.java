package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    private String direccion;

    private String telefono;

    @Column(unique = true, nullable = false)
    private String correoElectronico;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Reserva> reservas;
}
