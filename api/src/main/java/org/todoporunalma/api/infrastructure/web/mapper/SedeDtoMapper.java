package org.todoporunalma.api.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.todoporunalma.api.domain.model.Sede;
import org.todoporunalma.api.infrastructure.web.dto.SedeDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SedeDtoMapper {
    SedeDto toDto(Sede domain);
    Sede toDomain(SedeDto dto);
}
