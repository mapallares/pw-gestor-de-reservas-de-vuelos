package com.pw.gestorreservasvuelos.dto;

import com.pw.gestorreservasvuelos.entities.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    ReservaMapper INSTANCE = Mappers.getMapper( ReservaMapper.class );

    Reserva reservaDtoToReserva(ReservaDto reservaDto);

    @Mapping(target = "id", ignore = true)
    Reserva reservaDtoWithoutIdToReserva(ReservaDto reservaDto);

    ReservaDto reservaToReservaDto(Reserva reserva);

    @Mapping(target = "id", ignore = true)
    ReservaDto reservaToReservaDtoWithoutId(Reserva reserva);

}
