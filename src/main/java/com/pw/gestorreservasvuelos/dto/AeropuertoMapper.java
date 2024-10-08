package com.pw.gestorreservasvuelos.dto;

import com.pw.gestorreservasvuelos.entities.Aeropuerto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AeropuertoMapper {

    AeropuertoMapper INSTANCE = Mappers.getMapper( AeropuertoMapper.class );

    Aeropuerto aeropuertoDtoToAeropuerto(AeropuertoDto aeropuertoDto);

    @Mapping(target = "id", ignore = true)
    Aeropuerto aeropuertoDtoWithoutIdToAeropuerto(AeropuertoDto aeropuertoDto);

    AeropuertoDto aeropuertoToAeropuertoDto(Aeropuerto aeropuerto);

    @Mapping(target = "id", ignore = true)
    AeropuertoDto aeropuertoToAeropuertoDtoWithoutId(Aeropuerto aeropuerto);

}
