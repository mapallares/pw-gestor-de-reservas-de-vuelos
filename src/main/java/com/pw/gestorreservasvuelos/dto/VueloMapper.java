package com.pw.gestorreservasvuelos.dto;

import com.pw.gestorreservasvuelos.entities.Vuelo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VueloMapper {

    VueloMapper INSTANCE = Mappers.getMapper( VueloMapper.class );

    Vuelo vueloDtoToVuelo(VueloDto vueloDto);

    @Mapping(target = "id", ignore = true)
    Vuelo vueloDtoWithoutIdToVuelo(VueloDto vueloDto);

    VueloDto vueloToVueloDto(Vuelo vuelo);

    @Mapping(target = "id", ignore = true)
    VueloDto vueloToVueloDtoWithoutId(Vuelo vuelo);

}
