package com.pw.gestorreservasvuelos.dto;

import com.pw.gestorreservasvuelos.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper( ClienteMapper.class );

    Cliente clienteDtoToCliente(ClienteDto clienteDto);

    @Mapping(target = "id", ignore = true)
    Cliente clienteDtoWithoutIdToCliente(ClienteDto clienteDto);

    ClienteDto clienteToClienteDto(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    ClienteDto clienteToClienteDtoWithoutId(Cliente cliente);
    
}
