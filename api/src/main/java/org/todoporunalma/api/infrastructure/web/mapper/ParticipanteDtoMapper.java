package org.todoporunalma.api.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.todoporunalma.api.domain.model.Participante;
import org.todoporunalma.api.infrastructure.web.dto.ParticipanteDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParticipanteDtoMapper {
    @Mapping(target = "sedeNombre", ignore = true) // Populated separately for joined queries
    ParticipanteDto toDto(Participante domain);
    
    Participante toDomain(ParticipanteDto dto);
}
