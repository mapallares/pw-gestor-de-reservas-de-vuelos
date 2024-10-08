package com.pw.gestorreservasvuelos.dto;

import com.pw.gestorreservasvuelos.entities.Pasajero;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PasajeroMapper {

    PasajeroMapper INSTANCE = Mappers.getMapper( PasajeroMapper.class );

    Pasajero pasajeroDtoToPasajero(PasajeroDto pasajeroDto);

    @Mapping(target = "id", ignore = true)
    Pasajero pasajeroDtoWithoutIdToPasajero(PasajeroDto pasajeroDto);

    PasajeroDto pasajeroToPasajeroDto(Pasajero pasajero);

    @Mapping(target = "id", ignore = true)
    PasajeroDto pasajeroToPasajeroDtoWithoutId(Pasajero pasajero);

}
