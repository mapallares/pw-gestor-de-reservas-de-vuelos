package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "aerolineas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Aerolinea {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String codigoAerolinea;

    @Column(nullable = false)
    private String paisOrigen;

    @OneToMany(mappedBy = "aerolinea", fetch = FetchType.LAZY)
    private List<Vuelo> vuelos;

}
