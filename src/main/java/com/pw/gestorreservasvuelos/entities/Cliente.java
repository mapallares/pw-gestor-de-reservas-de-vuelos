package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Clientes") //Anotaciones o decoradores
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

    @OneToMany(mappedBy = "cliente")
    private Set<Reserva> reservas; //Set es una coleccion que no repite los elementos

    @ManyToMany
    @JoinTable(
            name = "VuelosClientes",
            joinColumns = @JoinColumn(name = "IdCliente", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "Idvuelo", referencedColumnName = "id")
    )
    private List<Vuelo> vuelos = new ArrayList<>();

    public void addVuelo(Vuelo vuelo) {
        this.vuelos.add(vuelo);
    }

}
