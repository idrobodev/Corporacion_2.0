package org.todoporunalma.api.infrastructure.web.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Sede;
import org.todoporunalma.api.infrastructure.web.dto.SedeDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-24T10:10:03-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class SedeDtoMapperImpl implements SedeDtoMapper {

    @Override
    public SedeDto toDto(Sede domain) {
        if ( domain == null ) {
            return null;
        }

        SedeDto.SedeDtoBuilder sedeDto = SedeDto.builder();

        sedeDto.capacidadMaxima( domain.getCapacidadMaxima() );
        sedeDto.ciudad( domain.getCiudad() );
        sedeDto.createdAt( domain.getCreatedAt() );
        sedeDto.departamento( domain.getDepartamento() );
        sedeDto.direccion( domain.getDireccion() );
        sedeDto.directorNombre( domain.getDirectorNombre() );
        sedeDto.directorTelefono( domain.getDirectorTelefono() );
        sedeDto.email( domain.getEmail() );
        sedeDto.estado( domain.getEstado() );
        sedeDto.id( domain.getId() );
        sedeDto.nombre( domain.getNombre() );
        sedeDto.observaciones( domain.getObservaciones() );
        sedeDto.telefono( domain.getTelefono() );
        sedeDto.tipoSede( domain.getTipoSede() );
        sedeDto.updatedAt( domain.getUpdatedAt() );

        return sedeDto.build();
    }

    @Override
    public Sede toDomain(SedeDto dto) {
        if ( dto == null ) {
            return null;
        }

        Sede.SedeBuilder sede = Sede.builder();

        sede.capacidadMaxima( dto.getCapacidadMaxima() );
        sede.ciudad( dto.getCiudad() );
        sede.createdAt( dto.getCreatedAt() );
        sede.departamento( dto.getDepartamento() );
        sede.direccion( dto.getDireccion() );
        sede.directorNombre( dto.getDirectorNombre() );
        sede.directorTelefono( dto.getDirectorTelefono() );
        sede.email( dto.getEmail() );
        sede.estado( dto.getEstado() );
        sede.id( dto.getId() );
        sede.nombre( dto.getNombre() );
        sede.observaciones( dto.getObservaciones() );
        sede.telefono( dto.getTelefono() );
        sede.tipoSede( dto.getTipoSede() );
        sede.updatedAt( dto.getUpdatedAt() );

        return sede.build();
    }
}
