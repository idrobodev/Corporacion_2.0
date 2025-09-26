package org.todoporunalma.api.infrastructure.web.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Participante;
import org.todoporunalma.api.infrastructure.web.dto.ParticipanteDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-24T10:10:03-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ParticipanteDtoMapperImpl implements ParticipanteDtoMapper {

    @Override
    public ParticipanteDto toDto(Participante domain) {
        if ( domain == null ) {
            return null;
        }

        ParticipanteDto.ParticipanteDtoBuilder participanteDto = ParticipanteDto.builder();

        participanteDto.apellidos( domain.getApellidos() );
        participanteDto.createdAt( domain.getCreatedAt() );
        participanteDto.direccion( domain.getDireccion() );
        participanteDto.documento( domain.getDocumento() );
        participanteDto.edad( domain.getEdad() );
        participanteDto.email( domain.getEmail() );
        participanteDto.estado( domain.getEstado() );
        participanteDto.fechaIngreso( domain.getFechaIngreso() );
        participanteDto.fechaNacimiento( domain.getFechaNacimiento() );
        participanteDto.id( domain.getId() );
        participanteDto.nombres( domain.getNombres() );
        participanteDto.observaciones( domain.getObservaciones() );
        participanteDto.sedeId( domain.getSedeId() );
        participanteDto.telefono( domain.getTelefono() );
        participanteDto.updatedAt( domain.getUpdatedAt() );

        return participanteDto.build();
    }

    @Override
    public Participante toDomain(ParticipanteDto dto) {
        if ( dto == null ) {
            return null;
        }

        Participante.ParticipanteBuilder participante = Participante.builder();

        participante.apellidos( dto.getApellidos() );
        participante.createdAt( dto.getCreatedAt() );
        participante.direccion( dto.getDireccion() );
        participante.documento( dto.getDocumento() );
        participante.edad( dto.getEdad() );
        participante.email( dto.getEmail() );
        participante.estado( dto.getEstado() );
        participante.fechaIngreso( dto.getFechaIngreso() );
        participante.fechaNacimiento( dto.getFechaNacimiento() );
        participante.id( dto.getId() );
        participante.nombres( dto.getNombres() );
        participante.observaciones( dto.getObservaciones() );
        participante.sedeId( dto.getSedeId() );
        participante.telefono( dto.getTelefono() );
        participante.updatedAt( dto.getUpdatedAt() );

        return participante.build();
    }
}
