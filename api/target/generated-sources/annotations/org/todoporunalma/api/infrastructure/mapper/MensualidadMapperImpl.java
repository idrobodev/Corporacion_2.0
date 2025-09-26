package org.todoporunalma.api.infrastructure.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Mensualidad;
import org.todoporunalma.api.infrastructure.entity.MensualidadEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-26T15:49:44-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class MensualidadMapperImpl implements MensualidadMapper {

    @Override
    public Mensualidad toDomain(MensualidadEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Mensualidad.MensualidadBuilder mensualidad = Mensualidad.builder();

        mensualidad.anio( entity.getAnio() );
        mensualidad.createdAt( entity.getCreatedAt() );
        mensualidad.estado( entity.getEstado() );
        mensualidad.fechaPago( entity.getFechaPago() );
        mensualidad.fechaVencimiento( entity.getFechaVencimiento() );
        mensualidad.id( entity.getId() );
        mensualidad.mes( entity.getMes() );
        mensualidad.metodoPago( entity.getMetodoPago() );
        mensualidad.monto( entity.getMonto() );
        mensualidad.observaciones( entity.getObservaciones() );
        mensualidad.participanteId( entity.getParticipanteId() );
        mensualidad.updatedAt( entity.getUpdatedAt() );

        return mensualidad.build();
    }

    @Override
    public MensualidadEntity toEntity(Mensualidad domain) {
        if ( domain == null ) {
            return null;
        }

        MensualidadEntity.MensualidadEntityBuilder mensualidadEntity = MensualidadEntity.builder();

        mensualidadEntity.anio( domain.getAnio() );
        mensualidadEntity.createdAt( domain.getCreatedAt() );
        mensualidadEntity.estado( domain.getEstado() );
        mensualidadEntity.fechaPago( domain.getFechaPago() );
        mensualidadEntity.fechaVencimiento( domain.getFechaVencimiento() );
        mensualidadEntity.id( domain.getId() );
        mensualidadEntity.mes( domain.getMes() );
        mensualidadEntity.metodoPago( domain.getMetodoPago() );
        mensualidadEntity.monto( domain.getMonto() );
        mensualidadEntity.observaciones( domain.getObservaciones() );
        mensualidadEntity.participanteId( domain.getParticipanteId() );
        mensualidadEntity.updatedAt( domain.getUpdatedAt() );

        return mensualidadEntity.build();
    }
}
