package org.todoporunalma.api.infrastructure.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Sede;
import org.todoporunalma.api.infrastructure.entity.SedeEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-26T15:49:44-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class SedeMapperImpl implements SedeMapper {

    @Override
    public Sede toDomain(SedeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Sede.SedeBuilder sede = Sede.builder();

        sede.capacidadMaxima( entity.getCapacidadMaxima() );
        sede.ciudad( entity.getCiudad() );
        sede.createdAt( entity.getCreatedAt() );
        sede.departamento( entity.getDepartamento() );
        sede.direccion( entity.getDireccion() );
        sede.directorNombre( entity.getDirectorNombre() );
        sede.directorTelefono( entity.getDirectorTelefono() );
        sede.email( entity.getEmail() );
        sede.estado( entity.getEstado() );
        sede.id( entity.getId() );
        sede.nombre( entity.getNombre() );
        sede.observaciones( entity.getObservaciones() );
        sede.telefono( entity.getTelefono() );
        sede.tipoSede( entity.getTipoSede() );
        sede.updatedAt( entity.getUpdatedAt() );

        return sede.build();
    }

    @Override
    public SedeEntity toEntity(Sede domain) {
        if ( domain == null ) {
            return null;
        }

        SedeEntity.SedeEntityBuilder sedeEntity = SedeEntity.builder();

        sedeEntity.capacidadMaxima( domain.getCapacidadMaxima() );
        sedeEntity.ciudad( domain.getCiudad() );
        sedeEntity.createdAt( domain.getCreatedAt() );
        sedeEntity.departamento( domain.getDepartamento() );
        sedeEntity.direccion( domain.getDireccion() );
        sedeEntity.directorNombre( domain.getDirectorNombre() );
        sedeEntity.directorTelefono( domain.getDirectorTelefono() );
        sedeEntity.email( domain.getEmail() );
        sedeEntity.estado( domain.getEstado() );
        sedeEntity.id( domain.getId() );
        sedeEntity.nombre( domain.getNombre() );
        sedeEntity.observaciones( domain.getObservaciones() );
        sedeEntity.telefono( domain.getTelefono() );
        sedeEntity.tipoSede( domain.getTipoSede() );
        sedeEntity.updatedAt( domain.getUpdatedAt() );

        return sedeEntity.build();
    }
}
