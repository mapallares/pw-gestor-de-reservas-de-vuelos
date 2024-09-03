package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Vuelos") //Anotaciones o decoradores
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Vuelo {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private LocalDate fechaSalida;
    @Temporal(TemporalType.TIME)
    private LocalTime horaSalida;
    private int duracion; //ojito----------------------------------------------------------------------
    private int capacidad;

    @ManyToOne
    @JoinColumn(name = "idAerolinea")
    private Aerolinea aerolinea;

    @ManyToOne
    @JoinColumn(name = "idAeropuertoOrigen")
    private Aeropuerto origen;

    @ManyToOne
    @JoinColumn(name = "idAeropuertoDestino")
    private Aeropuerto destino;

    @OneToOne(mappedBy = "origen")
    private Aeropuerto vueloOrigen;

    @OneToOne(mappedBy = "destino")
    private Aeropuerto vueloDestino;

    @ManyToMany(mappedBy = "vuelos")
    private Set<Cliente> clientes;

    @ManyToMany
    @JoinTable(
            name = "VuelosEscalas",
            joinColumns = @JoinColumn(name = "idVuelo", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "idEscala", referencedColumnName = "id")
    )
    private List<Escala> escalas = new ArrayList<>();

    public void addEscala(Escala escala) {
        this.escalas.add(escala);
    }


}
