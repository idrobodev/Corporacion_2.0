package org.todoporunalma.api.infrastructure.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Usuario;
import org.todoporunalma.api.infrastructure.entity.UsuarioEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-24T10:10:03-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toDomain(UsuarioEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Usuario.UsuarioBuilder usuario = Usuario.builder();

        usuario.activo( entity.getActivo() );
        usuario.createdAt( entity.getCreatedAt() );
        usuario.email( entity.getEmail() );
        usuario.id( entity.getId() );
        usuario.nombre( entity.getNombre() );
        usuario.passwordHash( entity.getPasswordHash() );
        usuario.rol( entity.getRol() );
        usuario.ultimoAcceso( entity.getUltimoAcceso() );
        usuario.updatedAt( entity.getUpdatedAt() );

        return usuario.build();
    }

    @Override
    public UsuarioEntity toEntity(Usuario domain) {
        if ( domain == null ) {
            return null;
        }

        UsuarioEntity.UsuarioEntityBuilder usuarioEntity = UsuarioEntity.builder();

        usuarioEntity.activo( domain.getActivo() );
        usuarioEntity.createdAt( domain.getCreatedAt() );
        usuarioEntity.email( domain.getEmail() );
        usuarioEntity.id( domain.getId() );
        usuarioEntity.nombre( domain.getNombre() );
        usuarioEntity.passwordHash( domain.getPasswordHash() );
        usuarioEntity.rol( domain.getRol() );
        usuarioEntity.ultimoAcceso( domain.getUltimoAcceso() );
        usuarioEntity.updatedAt( domain.getUpdatedAt() );

        return usuarioEntity.build();
    }
}
