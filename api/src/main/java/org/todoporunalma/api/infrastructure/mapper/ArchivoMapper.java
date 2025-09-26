package org.todoporunalma.api.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.todoporunalma.api.domain.model.Archivo;
import org.todoporunalma.api.infrastructure.entity.ArchivoEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArchivoMapper {
    Archivo toDomain(ArchivoEntity entity);
    ArchivoEntity toEntity(Archivo domain);
}
