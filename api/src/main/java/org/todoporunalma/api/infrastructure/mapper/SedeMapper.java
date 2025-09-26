package org.todoporunalma.api.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.todoporunalma.api.domain.model.Sede;
import org.todoporunalma.api.infrastructure.entity.SedeEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SedeMapper {
    Sede toDomain(SedeEntity entity);
    SedeEntity toEntity(Sede domain);
}
