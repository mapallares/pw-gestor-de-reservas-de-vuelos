package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Reserva {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(nullable = false) @Temporal(TemporalType.DATE)
    private LocalDate fechaReserva;

    @Column(nullable = false)
    private Integer numeroPasajeros;

    @OneToMany(mappedBy = "reserva", fetch = FetchType.EAGER)
    private List<Pasajero> pasajeros;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "rutas",
            joinColumns = @JoinColumn(name = "id_reserva", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_vuelo", referencedColumnName = "id")
    )
    private List<Vuelo> vuelos = new ArrayList<>();

    public void addVuelo(Vuelo vuelo) {
        this.vuelos.add(vuelo);
    }

}
