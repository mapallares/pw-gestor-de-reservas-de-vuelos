package com.pw.gestorreservasvuelos.dto;

import com.pw.gestorreservasvuelos.entities.Aerolinea;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AerolineaMapper {

    AerolineaMapper INSTANCE = Mappers.getMapper( AerolineaMapper.class );

    Aerolinea aerolineaDtoToAerolinea(AerolineaDto aerolineaDto);

    @Mapping(target = "id", ignore = true)
    Aerolinea aerolineaDtoWithoutIdToAerolinea(AerolineaDto aerolineaDto);

    AerolineaDto aerolineaToAerolineaDto(Aerolinea aerolinea);

    @Mapping(target = "id", ignore = true)
    AerolineaDto aerolineaToAerolineaDtoWithoutId(Aerolinea aerolinea);

}
