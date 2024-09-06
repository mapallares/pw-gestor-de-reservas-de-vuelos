package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "aeropuertos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Aeropuerto {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String pais;

    @OneToMany(mappedBy = "origen", fetch = FetchType.LAZY)
    private List<Vuelo> origenes;

    @OneToMany(mappedBy = "destino", fetch = FetchType.LAZY)
    private List<Vuelo> destinos;

}
