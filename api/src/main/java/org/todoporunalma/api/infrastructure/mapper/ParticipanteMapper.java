package org.todoporunalma.api.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.todoporunalma.api.domain.model.Participante;
import org.todoporunalma.api.infrastructure.entity.ParticipanteEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParticipanteMapper {
    @Mapping(source = "edad", target = "edad")
    Participante toDomain(ParticipanteEntity entity);
    
    ParticipanteEntity toEntity(Participante domain);
}
