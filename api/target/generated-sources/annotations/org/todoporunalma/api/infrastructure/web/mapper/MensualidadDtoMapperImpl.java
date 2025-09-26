package org.todoporunalma.api.infrastructure.web.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Mensualidad;
import org.todoporunalma.api.infrastructure.web.dto.MensualidadDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-26T15:49:44-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class MensualidadDtoMapperImpl implements MensualidadDtoMapper {

    @Override
    public MensualidadDto toDto(Mensualidad domain) {
        if ( domain == null ) {
            return null;
        }

        MensualidadDto.MensualidadDtoBuilder mensualidadDto = MensualidadDto.builder();

        mensualidadDto.anio( domain.getAnio() );
        mensualidadDto.createdAt( domain.getCreatedAt() );
        mensualidadDto.estado( domain.getEstado() );
        mensualidadDto.fechaPago( domain.getFechaPago() );
        mensualidadDto.fechaVencimiento( domain.getFechaVencimiento() );
        mensualidadDto.id( domain.getId() );
        mensualidadDto.mes( domain.getMes() );
        mensualidadDto.metodoPago( domain.getMetodoPago() );
        mensualidadDto.monto( domain.getMonto() );
        mensualidadDto.observaciones( domain.getObservaciones() );
        mensualidadDto.participanteId( domain.getParticipanteId() );
        mensualidadDto.updatedAt( domain.getUpdatedAt() );

        return mensualidadDto.build();
    }

    @Override
    public Mensualidad toDomain(MensualidadDto dto) {
        if ( dto == null ) {
            return null;
        }

        Mensualidad.MensualidadBuilder mensualidad = Mensualidad.builder();

        mensualidad.anio( dto.getAnio() );
        mensualidad.createdAt( dto.getCreatedAt() );
        mensualidad.estado( dto.getEstado() );
        mensualidad.fechaPago( dto.getFechaPago() );
        mensualidad.fechaVencimiento( dto.getFechaVencimiento() );
        mensualidad.id( dto.getId() );
        mensualidad.mes( dto.getMes() );
        mensualidad.metodoPago( dto.getMetodoPago() );
        mensualidad.monto( dto.getMonto() );
        mensualidad.observaciones( dto.getObservaciones() );
        mensualidad.participanteId( dto.getParticipanteId() );
        mensualidad.updatedAt( dto.getUpdatedAt() );

        return mensualidad.build();
    }
}
