package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Aerolineas") //Anotaciones o decoradores
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Aerolinea {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(unique = true, nullable = false)
    private String codigoArerolinea;
    private String paisOrginen;

    @OneToMany(mappedBy = "aerolinea")
    private Set<Vuelo> vuelos;


}
