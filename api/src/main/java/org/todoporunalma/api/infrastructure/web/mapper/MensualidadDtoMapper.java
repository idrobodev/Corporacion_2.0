package org.todoporunalma.api.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.todoporunalma.api.domain.model.Mensualidad;
import org.todoporunalma.api.infrastructure.web.dto.MensualidadDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MensualidadDtoMapper {
    @Mapping(target = "participanteNombre", ignore = true) // Populated separately for joined queries
    @Mapping(target = "sedeNombre", ignore = true) // Populated separately for joined queries
    MensualidadDto toDto(Mensualidad domain);
    
    Mensualidad toDomain(MensualidadDto dto);
}
