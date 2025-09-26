package org.todoporunalma.api.infrastructure.web.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.todoporunalma.api.domain.model.Usuario;
import org.todoporunalma.api.infrastructure.web.dto.UsuarioDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-24T10:10:03-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UsuarioDtoMapperImpl implements UsuarioDtoMapper {

    @Override
    public UsuarioDto toDto(Usuario domain) {
        if ( domain == null ) {
            return null;
        }

        UsuarioDto.UsuarioDtoBuilder usuarioDto = UsuarioDto.builder();

        usuarioDto.activo( domain.getActivo() );
        usuarioDto.createdAt( domain.getCreatedAt() );
        usuarioDto.email( domain.getEmail() );
        usuarioDto.id( domain.getId() );
        usuarioDto.nombre( domain.getNombre() );
        usuarioDto.rol( domain.getRol() );
        usuarioDto.ultimoAcceso( domain.getUltimoAcceso() );
        usuarioDto.updatedAt( domain.getUpdatedAt() );

        return usuarioDto.build();
    }

    @Override
    public Usuario toDomain(UsuarioDto dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario.UsuarioBuilder usuario = Usuario.builder();

        usuario.activo( dto.getActivo() );
        usuario.createdAt( dto.getCreatedAt() );
        usuario.email( dto.getEmail() );
        usuario.id( dto.getId() );
        usuario.nombre( dto.getNombre() );
        usuario.rol( dto.getRol() );
        usuario.ultimoAcceso( dto.getUltimoAcceso() );
        usuario.updatedAt( dto.getUpdatedAt() );

        return usuario.build();
    }
}
