package org.todoporunalma.api.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.todoporunalma.api.domain.model.Mensualidad;
import org.todoporunalma.api.infrastructure.entity.MensualidadEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MensualidadMapper {
    Mensualidad toDomain(MensualidadEntity entity);
    MensualidadEntity toEntity(Mensualidad domain);
}
