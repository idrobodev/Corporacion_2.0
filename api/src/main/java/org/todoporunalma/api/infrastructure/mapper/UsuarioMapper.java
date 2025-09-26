package org.todoporunalma.api.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.todoporunalma.api.domain.model.Usuario;
import org.todoporunalma.api.infrastructure.entity.UsuarioEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {
    Usuario toDomain(UsuarioEntity entity);
    UsuarioEntity toEntity(Usuario domain);
}
