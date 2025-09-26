package org.todoporunalma.api.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.todoporunalma.api.domain.model.Usuario;
import org.todoporunalma.api.infrastructure.web.dto.UsuarioDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioDtoMapper {
    // Convert from domain to DTO - passwordHash is automatically ignored since it doesn't exist in DTO
    UsuarioDto toDto(Usuario domain);
    
    @Mapping(target = "passwordHash", ignore = true) // Don't map password from DTO to domain
    Usuario toDomain(UsuarioDto dto);
}
