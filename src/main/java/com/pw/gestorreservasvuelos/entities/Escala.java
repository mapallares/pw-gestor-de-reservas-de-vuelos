package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Escalas") //Anotaciones o decoradores
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Escala {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int tiempoEscala; //escala ----------------------------------------------------------------

    @ManyToMany(mappedBy = "escalas")
    private Set<Vuelo> vuelos;

    @ManyToOne
    @JoinColumn(name = "idAeropuerto")
    private Aeropuerto aeropuerto;

}
