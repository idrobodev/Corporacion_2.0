package org.todoporunalma.api.infrastructure.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Archivo;
import org.todoporunalma.api.infrastructure.entity.ArchivoEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-24T10:10:03-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ArchivoMapperImpl implements ArchivoMapper {

    @Override
    public Archivo toDomain(ArchivoEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Archivo.ArchivoBuilder archivo = Archivo.builder();

        archivo.carpeta( entity.getCarpeta() );
        archivo.createdAt( entity.getCreatedAt() );
        archivo.id( entity.getId() );
        archivo.mimeType( entity.getMimeType() );
        archivo.nombre( entity.getNombre() );
        archivo.participanteId( entity.getParticipanteId() );
        archivo.ruta( entity.getRuta() );
        archivo.tamaño( entity.getTamaño() );
        archivo.updatedAt( entity.getUpdatedAt() );
        archivo.url( entity.getUrl() );

        return archivo.build();
    }

    @Override
    public ArchivoEntity toEntity(Archivo domain) {
        if ( domain == null ) {
            return null;
        }

        ArchivoEntity.ArchivoEntityBuilder archivoEntity = ArchivoEntity.builder();

        archivoEntity.carpeta( domain.getCarpeta() );
        archivoEntity.createdAt( domain.getCreatedAt() );
        archivoEntity.id( domain.getId() );
        archivoEntity.mimeType( domain.getMimeType() );
        archivoEntity.nombre( domain.getNombre() );
        archivoEntity.participanteId( domain.getParticipanteId() );
        archivoEntity.ruta( domain.getRuta() );
        archivoEntity.tamaño( domain.getTamaño() );
        archivoEntity.updatedAt( domain.getUpdatedAt() );
        archivoEntity.url( domain.getUrl() );

        return archivoEntity.build();
    }
}
