package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Aeropuertos") //Anotaciones o decoradores
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

    @OneToMany(mappedBy = "origen")
    private Set<Vuelo> vuelosOrigen;

    @OneToMany(mappedBy = "destino")
    private Set<Vuelo> vuelosDestino;

    @OneToOne(optional = false)
    @JoinColumn(name = "idVueloOrigen", referencedColumnName = "id")
    private Vuelo origen;

    @OneToOne(optional = false)
    @JoinColumn(name = "idVueloDestino", referencedColumnName = "id")
    private Vuelo destino;

    @OneToMany(mappedBy = "aeropuerto")
    private Set<Escala> escalas;

}
