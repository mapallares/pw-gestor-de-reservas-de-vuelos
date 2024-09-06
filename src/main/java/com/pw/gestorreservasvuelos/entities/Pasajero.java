package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pasajeros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Pasajero {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String identificacion;

    @Column(nullable = false)
    private String correoElectronico;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_reserva")
    private Reserva reserva;

}
