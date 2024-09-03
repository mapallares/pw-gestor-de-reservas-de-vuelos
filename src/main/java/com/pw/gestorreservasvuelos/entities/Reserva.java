package com.pw.gestorreservasvuelos.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Reservas") //Anotaciones o decoradores
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Reserva {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate fechaReserva;
    private int numeroPasajeros;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;


}
