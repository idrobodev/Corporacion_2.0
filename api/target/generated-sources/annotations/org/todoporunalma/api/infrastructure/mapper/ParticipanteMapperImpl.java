package org.todoporunalma.api.infrastructure.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Participante;
import org.todoporunalma.api.infrastructure.entity.ParticipanteEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-26T16:35:21-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ParticipanteMapperImpl implements ParticipanteMapper {

    @Override
    public Participante toDomain(ParticipanteEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Participante.ParticipanteBuilder participante = Participante.builder();

        participante.edad( entity.getEdad() );
        participante.apellidos( entity.getApellidos() );
        participante.createdAt( entity.getCreatedAt() );
        participante.direccion( entity.getDireccion() );
        participante.documento( entity.getDocumento() );
        participante.email( entity.getEmail() );
        participante.estado( entity.getEstado() );
        participante.fechaIngreso( entity.getFechaIngreso() );
        participante.fechaNacimiento( entity.getFechaNacimiento() );
        participante.genero( entity.getGenero() );
        participante.id( entity.getId() );
        participante.nombres( entity.getNombres() );
        participante.observaciones( entity.getObservaciones() );
        participante.sedeId( entity.getSedeId() );
        participante.telefono( entity.getTelefono() );
        participante.updatedAt( entity.getUpdatedAt() );

        return participante.build();
    }

    @Override
    public ParticipanteEntity toEntity(Participante domain) {
        if ( domain == null ) {
            return null;
        }

        ParticipanteEntity.ParticipanteEntityBuilder participanteEntity = ParticipanteEntity.builder();

        participanteEntity.apellidos( domain.getApellidos() );
        participanteEntity.createdAt( domain.getCreatedAt() );
        participanteEntity.direccion( domain.getDireccion() );
        participanteEntity.documento( domain.getDocumento() );
        participanteEntity.email( domain.getEmail() );
        participanteEntity.estado( domain.getEstado() );
        participanteEntity.fechaIngreso( domain.getFechaIngreso() );
        participanteEntity.fechaNacimiento( domain.getFechaNacimiento() );
        participanteEntity.genero( domain.getGenero() );
        participanteEntity.id( domain.getId() );
        participanteEntity.nombres( domain.getNombres() );
        participanteEntity.observaciones( domain.getObservaciones() );
        participanteEntity.sedeId( domain.getSedeId() );
        participanteEntity.telefono( domain.getTelefono() );
        participanteEntity.updatedAt( domain.getUpdatedAt() );

        return participanteEntity.build();
    }
}
