package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "vuelos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Vuelo {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_origen")
    private Aeropuerto origen;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_destino")
    private Aeropuerto destino;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_aerolinea")
    private Aerolinea aerolinea;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate fechaSalida;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private LocalTime horaSalida;

    @Column(nullable = false)
    private Integer duracion;

    @Column(nullable = false)
    private Integer capacidad;

    @ManyToMany(mappedBy = "vuelos", fetch = FetchType.EAGER)
    private List<Reserva> reservas;

}
